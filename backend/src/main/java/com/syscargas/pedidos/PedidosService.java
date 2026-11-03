package com.syscargas.pedidos;

import static com.syscargas.common.Estado.ACTIVO;
import static com.syscargas.common.Estado.ANULADO;
import static com.syscargas.common.Estado.EN_PROCESO;
import static com.syscargas.common.Estado.FINALIZADO;
import static com.syscargas.common.Estado.PENDIENTE;

import com.syscargas.common.ApiException;
import com.syscargas.common.DocSupport;
import com.syscargas.common.Rows;
import com.syscargas.pedidos.PedidosDtos.CalculoCotizacion;
import com.syscargas.pedidos.PedidosDtos.CotizacionReq;
import com.syscargas.pedidos.PedidosDtos.CrtReq;
import com.syscargas.pedidos.PedidosDtos.EventoReq;
import com.syscargas.pedidos.PedidosDtos.ItemCrt;
import com.syscargas.pedidos.PedidosDtos.ItemMerc;
import com.syscargas.pedidos.PedidosDtos.PedidoFleteReq;
import com.syscargas.pedidos.PedidosDtos.PedidoReq;
import com.syscargas.pedidos.PedidosDtos.ReclamoReq;
import com.syscargas.pedidos.PedidosDtos.ResolucionReclamo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidosService {

    private final JdbcClient jdbc;
    private final DocSupport docs;

    public PedidosService(JdbcClient jdbc, DocSupport docs) {
        this.jdbc = jdbc;
        this.docs = docs;
    }

    private static String up(String s) {
        return s == null ? null : s.trim().toUpperCase(Locale.ROOT);
    }

    // =====================================================================
    // Registro de pedido del cliente (r_pedido)
    // =====================================================================
    private static final String PEDIDO_SQL = """
            SELECT r.id_rped, r.fecha, r.descrip, r.id_cliente,
                   c.nombres || coalesce(' ' || c.apellidos, '') AS cliente, c.nro_doc,
                   r.id_per, p.nombres || ' ' || p.apellidos AS personal, r.id_est, e.descrip AS estado
            FROM r_pedido r
            JOIN cliente c ON c.id_cliente = r.id_cliente
            JOIN personal p ON p.id_per = r.id_per
            JOIN estado e ON e.id_est = r.id_est
            """;

    public List<Map<String, Object>> pedidos() {
        return jdbc.sql(PEDIDO_SQL + " ORDER BY r.id_rped DESC LIMIT 1000").query(Rows.MAP).list();
    }

    public Map<String, Object> pedido(int id) {
        Map<String, Object> m = docs.one(PEDIDO_SQL + " WHERE r.id_rped = :id", id, "Pedido");
        m.put("pedidos_flete", docs.list(
                "SELECT p.id_ped, p.fecha, p.descrip, e.descrip AS estado FROM ped_flete p JOIN estado e ON e.id_est = p.id_est WHERE p.id_rped = :id ORDER BY p.id_ped",
                id));
        return m;
    }

    @Transactional
    public Map<String, Object> crearPedido(PedidoReq req, int idPer) {
        docs.requireActivo("cliente", "id_cliente", req.idCliente(), "El cliente");
        int id = jdbc.sql("""
                INSERT INTO r_pedido (fecha, descrip, id_cliente, id_per) VALUES (:f, :d, :c, :p) RETURNING id_rped
                """)
                .param("f", req.fecha()).param("d", up(req.descrip()))
                .param("c", req.idCliente()).param("p", idPer)
                .query(Integer.class).single();
        return pedido(id);
    }

    @Transactional
    public void anularPedido(int id) {
        docs.requireEstado(docs.lockEstado("r_pedido", "id_rped", id, "El pedido"), "El pedido", PENDIENTE, EN_PROCESO);
        long activos = jdbc.sql("SELECT count(*) FROM ped_flete WHERE id_rped = :id AND id_est <> 0")
                .param("id", id).query(Long.class).single();
        if (activos > 0) {
            throw ApiException.conflict("El pedido tiene pedidos de flete vigentes; anúlelos primero");
        }
        docs.setEstado("r_pedido", "id_rped", id, ANULADO);
    }

    // =====================================================================
    // Cotizacion de flete
    // =====================================================================
    /**
     * Regla de negocio del legacy (Cotizar_F): (tarifa origen + tarifa destino) x volumen x peso.
     * Ahora se calcula en el servidor con BigDecimal (el legacy guardaba el texto del formulario
     * y desbordaba INTEGER).
     */
    public Map<String, Object> calcular(CalculoCotizacion req) {
        BigDecimal tarifaO = tarifa(req.idPaisO());
        BigDecimal tarifaD = tarifa(req.idPaisD());
        BigDecimal costo = tarifaO.add(tarifaD).multiply(req.volumen()).multiply(req.peso())
                .setScale(0, RoundingMode.HALF_UP);
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("tarifa_origen", tarifaO);
        out.put("tarifa_destino", tarifaD);
        out.put("costo", costo);
        out.put("moneda", "PYG");
        return out;
    }

    private BigDecimal tarifa(int idPais) {
        docs.requireActivo("pais", "id_pais", idPais, "El país");
        return jdbc.sql("SELECT tarifa FROM pais WHERE id_pais = :id").param("id", idPais)
                .query(BigDecimal.class).single();
    }

    private static final String COTI_SQL = """
            SELECT k.id_cot, k.fecha, k.id_cliente, c.nombres || coalesce(' ' || c.apellidos, '') AS cliente,
                   k.id_pais_o, po.descrip AS pais_origen, k.id_pais_d, pd.descrip AS pais_destino,
                   k.peso, k.volumen, k.costo, m.abrev AS moneda, k.id_est, e.descrip AS estado,
                   p.nombres || ' ' || p.apellidos AS personal
            FROM coti_flete k
            JOIN cliente c ON c.id_cliente = k.id_cliente
            JOIN pais po ON po.id_pais = k.id_pais_o
            JOIN pais pd ON pd.id_pais = k.id_pais_d
            JOIN moneda m ON m.id_mon = k.id_mon
            JOIN personal p ON p.id_per = k.id_per
            JOIN estado e ON e.id_est = k.id_est
            """;

    public List<Map<String, Object>> cotizaciones() {
        return jdbc.sql(COTI_SQL + " ORDER BY k.id_cot DESC LIMIT 1000").query(Rows.MAP).list();
    }

    public Map<String, Object> cotizacion(int id) {
        Map<String, Object> m = docs.one(COTI_SQL + " WHERE k.id_cot = :id", id, "Cotización");
        m.put("items", docs.list("""
                SELECT d.item, d.id_merc, me.descrip AS mercaderia
                FROM d_co_mer d JOIN mercaderia me ON me.id_merc = d.id_merc
                WHERE d.id_cot = :id ORDER BY d.item
                """, id));
        return m;
    }

    @Transactional
    public Map<String, Object> crearCotizacion(CotizacionReq req, int idPer) {
        docs.requireActivo("cliente", "id_cliente", req.idCliente(), "El cliente");
        BigDecimal costo = (BigDecimal) calcular(new CalculoCotizacion(
                req.idPaisO(), req.idPaisD(), req.peso(), req.volumen())).get("costo");
        int id = jdbc.sql("""
                INSERT INTO coti_flete (id_cliente, id_pais_o, id_pais_d, peso, volumen, costo, id_mon, id_per)
                VALUES (:c, :o, :d, :peso, :vol, :costo, (SELECT id_mon FROM moneda WHERE abrev = 'PYG'), :p)
                RETURNING id_cot
                """)
                .param("c", req.idCliente()).param("o", req.idPaisO()).param("d", req.idPaisD())
                .param("peso", req.peso()).param("vol", req.volumen()).param("costo", costo).param("p", idPer)
                .query(Integer.class).single();
        int item = 1;
        for (Integer merc : req.mercaderias().stream().distinct().toList()) {
            docs.requireActivo("mercaderia", "id_merc", merc, "La mercadería");
            jdbc.sql("INSERT INTO d_co_mer (id_cot, item, id_merc) VALUES (:id, :item, :m)")
                    .param("id", id).param("item", item++).param("m", merc).update();
        }
        return cotizacion(id);
    }

    @Transactional
    public void anularCotizacion(int id) {
        docs.requireEstado(docs.lockEstado("coti_flete", "id_cot", id, "La cotización"), "La cotización", ACTIVO);
        docs.setEstado("coti_flete", "id_cot", id, ANULADO);
    }

    // =====================================================================
    // Pedido de flete (ped_flete + d_merc)
    // =====================================================================
    private static final String PED_SQL = """
            SELECT p.id_ped, p.fecha, p.descrip, p.id_rped, c.nombres || coalesce(' ' || c.apellidos, '') AS cliente,
                   p.id_desp, d.nombres || ' ' || d.apellidos AS despachante, p.id_tic, t.descrip AS tipo_carga,
                   p.id_est, e.descrip AS estado
            FROM ped_flete p
            JOIN r_pedido r ON r.id_rped = p.id_rped
            JOIN cliente c ON c.id_cliente = r.id_cliente
            JOIN despachante d ON d.id_desp = p.id_desp
            JOIN tipo_carg t ON t.id_tic = p.id_tic
            JOIN estado e ON e.id_est = p.id_est
            """;

    public List<Map<String, Object>> pedidosFlete() {
        return jdbc.sql(PED_SQL + " ORDER BY p.id_ped DESC LIMIT 1000").query(Rows.MAP).list();
    }

    public Map<String, Object> pedidoFlete(int id) {
        Map<String, Object> m = docs.one(PED_SQL + " WHERE p.id_ped = :id", id, "Pedido de flete");
        m.put("items", docs.list("""
                SELECT d.item, d.id_merc, me.descrip AS mercaderia, d.cantidad
                FROM d_merc d JOIN mercaderia me ON me.id_merc = d.id_merc
                WHERE d.id_ped = :id ORDER BY d.item
                """, id));
        return m;
    }

    @Transactional
    public Map<String, Object> crearPedidoFlete(PedidoFleteReq req, int idPer) {
        int estPedido = docs.lockEstado("r_pedido", "id_rped", req.idRped(), "El pedido del cliente");
        docs.requireEstado(estPedido, "El pedido del cliente", PENDIENTE, EN_PROCESO);
        docs.requireActivo("despachante", "id_desp", req.idDesp(), "El despachante");
        int id = jdbc.sql("""
                INSERT INTO ped_flete (fecha, descrip, id_rped, id_desp, id_tic, id_per)
                VALUES (:f, :d, :r, :desp, :tic, :p) RETURNING id_ped
                """)
                .param("f", req.fecha()).param("d", up(req.descrip())).param("r", req.idRped())
                .param("desp", req.idDesp()).param("tic", req.idTic()).param("p", idPer)
                .query(Integer.class).single();
        int item = 1;
        for (ItemMerc it : req.items()) {
            docs.requireActivo("mercaderia", "id_merc", it.idMerc(), "La mercadería");
            jdbc.sql("INSERT INTO d_merc (id_ped, item, id_merc, cantidad) VALUES (:id, :item, :m, :c)")
                    .param("id", id).param("item", item++).param("m", it.idMerc()).param("c", it.cantidad())
                    .update();
        }
        if (estPedido == PENDIENTE) {
            docs.setEstado("r_pedido", "id_rped", req.idRped(), EN_PROCESO);
        }
        return pedidoFlete(id);
    }

    @Transactional
    public void anularPedidoFlete(int id) {
        docs.requireEstado(docs.lockEstado("ped_flete", "id_ped", id, "El pedido de flete"),
                "El pedido de flete", PENDIENTE, EN_PROCESO);
        long asig = jdbc.sql("SELECT count(*) FROM asignacion WHERE id_ped = :id AND id_est <> 0")
                .param("id", id).query(Long.class).single();
        if (asig > 0) {
            throw ApiException.conflict("El pedido de flete tiene asignaciones de equipo vigentes");
        }
        docs.setEstado("ped_flete", "id_ped", id, ANULADO);
        // si el pedido del cliente queda sin pedidos de flete vigentes, vuelve a pendiente
        jdbc.sql("""
                UPDATE r_pedido r SET id_est = 2
                WHERE r.id_rped = (SELECT id_rped FROM ped_flete WHERE id_ped = :id) AND r.id_est = 3
                  AND NOT EXISTS (SELECT 1 FROM ped_flete p WHERE p.id_rped = r.id_rped AND p.id_est <> 0)
                """).param("id", id).update();
    }

    // =====================================================================
    // Emision de CRT (emi_crt + d_m_emi)
    // =====================================================================
    private static final String CRT_SQL = """
            SELECT k.id_crt, k.nro, k.anho, k.fecha_emi, k.id_ped, k.id_mon, m.abrev AS moneda,
                   k.id_expo, ex.nombres || coalesce(' ' || ex.apellidos, '') AS exportador,
                   k.id_impo, im.nombres || coalesce(' ' || im.apellidos, '') AS importador,
                   k.id_adu, a.descrip AS aduana, k.id_pais_o, po.descrip AS pais_origen,
                   k.id_pais_d, pd.descrip AS pais_destino, k.id_est, e.descrip AS estado,
                   p.nombres || ' ' || p.apellidos AS personal,
                   (SELECT coalesce(sum(d.valor), 0) FROM d_m_emi d WHERE d.id_crt = k.id_crt) AS valor_total
            FROM emi_crt k
            JOIN moneda m ON m.id_mon = k.id_mon
            JOIN cliente ex ON ex.id_cliente = k.id_expo
            JOIN cliente im ON im.id_cliente = k.id_impo
            JOIN aduana a ON a.id_adu = k.id_adu
            JOIN pais po ON po.id_pais = k.id_pais_o
            JOIN pais pd ON pd.id_pais = k.id_pais_d
            JOIN personal p ON p.id_per = k.id_per
            JOIN estado e ON e.id_est = k.id_est
            """;

    public List<Map<String, Object>> crts() {
        return jdbc.sql(CRT_SQL + " ORDER BY k.anho DESC, k.nro DESC LIMIT 1000").query(Rows.MAP).list();
    }

    public Map<String, Object> crt(int id) {
        Map<String, Object> m = docs.one(CRT_SQL + " WHERE k.id_crt = :id", id, "CRT");
        m.put("items", docs.list("""
                SELECT d.item, d.id_merc, me.descrip AS mercaderia, d.cantidad, d.peso, d.volumen, d.valor,
                       d.id_mon, mo.abrev AS moneda
                FROM d_m_emi d JOIN mercaderia me ON me.id_merc = d.id_merc JOIN moneda mo ON mo.id_mon = d.id_mon
                WHERE d.id_crt = :id ORDER BY d.item
                """, id));
        return m;
    }

    @Transactional
    public Map<String, Object> crearCrt(CrtReq req, int idPer) {
        if (req.idPaisO().equals(req.idPaisD())) {
            throw ApiException.badRequest("El país de origen y el de destino deben ser distintos");
        }
        if (req.idPed() != null) {
            docs.requireEstado(docs.lockEstado("ped_flete", "id_ped", req.idPed(), "El pedido de flete"),
                    "El pedido de flete", PENDIENTE, EN_PROCESO);
        }
        docs.requireActivo("cliente", "id_cliente", req.idExpo(), "El exportador");
        docs.requireActivo("cliente", "id_cliente", req.idImpo(), "El importador");
        docs.requireActivo("aduana", "id_adu", req.idAdu(), "La aduana");
        int anho = req.fechaEmi().getYear();
        // numeracion correlativa por anho sin carreras entre transacciones concurrentes
        jdbc.sql("SELECT pg_advisory_xact_lock(4242, :anho)").param("anho", anho).query(Rows.MAP).list();
        int nro = jdbc.sql("SELECT coalesce(max(nro), 0) + 1 FROM emi_crt WHERE anho = :anho")
                .param("anho", anho).query(Integer.class).single();
        int id = jdbc.sql("""
                INSERT INTO emi_crt (nro, anho, fecha_emi, id_ped, id_mon, id_per, id_expo, id_impo, id_adu, id_pais_o, id_pais_d)
                VALUES (:nro, :anho, :f, :ped, :mon, :per, :ex, :im, :adu, :o, :d) RETURNING id_crt
                """)
                .param("nro", nro).param("anho", anho).param("f", req.fechaEmi()).param("ped", req.idPed())
                .param("mon", req.idMon()).param("per", idPer).param("ex", req.idExpo()).param("im", req.idImpo())
                .param("adu", req.idAdu()).param("o", req.idPaisO()).param("d", req.idPaisD())
                .query(Integer.class).single();
        int item = 1;
        for (ItemCrt it : req.items()) {
            docs.requireActivo("mercaderia", "id_merc", it.idMerc(), "La mercadería");
            jdbc.sql("""
                    INSERT INTO d_m_emi (id_crt, item, id_merc, cantidad, peso, volumen, valor, id_mon)
                    VALUES (:id, :item, :m, :c, :p, :v, :val, :mon)
                    """)
                    .param("id", id).param("item", item++).param("m", it.idMerc()).param("c", it.cantidad())
                    .param("p", it.peso()).param("v", it.volumen()).param("val", it.valor()).param("mon", it.idMon())
                    .update();
        }
        return crt(id);
    }

    @Transactional
    public void anularCrt(int id) {
        docs.requireEstado(docs.lockEstado("emi_crt", "id_crt", id, "El CRT"), "El CRT", ACTIVO);
        docs.setEstado("emi_crt", "id_crt", id, ANULADO);
    }

    // =====================================================================
    // Eventos de carga
    // =====================================================================
    public List<Map<String, Object>> eventos(Integer idCrt) {
        return jdbc.sql("""
                SELECT ev.id_evento, ev.id_crt, 'CRT ' || k.nro || '/' || k.anho AS crt, ev.id_tip, t.descrip AS tipo,
                       ev.fecha_hora, ev.coment, p.nombres || ' ' || p.apellidos AS personal,
                       ev.id_est, e.descrip AS estado
                FROM evento_carga ev
                JOIN emi_crt k ON k.id_crt = ev.id_crt
                JOIN tip_event t ON t.id_tip = ev.id_tip
                JOIN personal p ON p.id_per = ev.id_per
                JOIN estado e ON e.id_est = ev.id_est
                WHERE (CAST(:crt AS INTEGER) IS NULL OR ev.id_crt = :crt)
                ORDER BY ev.fecha_hora DESC, ev.id_evento DESC LIMIT 1000
                """).param("crt", idCrt).query(Rows.MAP).list();
    }

    @Transactional
    public Map<String, Object> crearEvento(EventoReq req, int idPer) {
        docs.requireEstado(docs.lockEstado("emi_crt", "id_crt", req.idCrt(), "El CRT"), "El CRT", ACTIVO);
        int id = jdbc.sql("""
                INSERT INTO evento_carga (id_crt, id_tip, fecha_hora, coment, id_per)
                VALUES (:crt, :tip, :f, :c, :p) RETURNING id_evento
                """)
                .param("crt", req.idCrt()).param("tip", req.idTip()).param("f", req.fechaHora())
                .param("c", up(req.coment())).param("p", idPer)
                .query(Integer.class).single();
        return Map.of("id_evento", id);
    }

    @Transactional
    public void anularEvento(int id) {
        docs.requireEstado(docs.lockEstado("evento_carga", "id_evento", id, "El evento"), "El evento", ACTIVO);
        docs.setEstado("evento_carga", "id_evento", id, ANULADO);
    }

    // =====================================================================
    // Reclamos
    // =====================================================================
    private static final String RECLAMO_SQL = """
            SELECT r.id_recla, r.fecha, r.descrip, r.id_rped, r.id_ped,
                   c.nombres || coalesce(' ' || c.apellidos, '') AS cliente, c.telef,
                   r.id_est, e.descrip AS estado, r.fecha_c, r.solucion, r.avisado,
                   p.nombres || ' ' || p.apellidos AS personal
            FROM reclamos r
            JOIN r_pedido rp ON rp.id_rped = r.id_rped
            JOIN cliente c ON c.id_cliente = rp.id_cliente
            JOIN personal p ON p.id_per = r.id_per
            JOIN estado e ON e.id_est = r.id_est
            """;

    public List<Map<String, Object>> reclamos(Integer estado) {
        return jdbc.sql(RECLAMO_SQL + " WHERE (CAST(:est AS INTEGER) IS NULL OR r.id_est = :est) ORDER BY r.id_recla DESC LIMIT 1000")
                .param("est", estado).query(Rows.MAP).list();
    }

    public Map<String, Object> reclamo(int id) {
        return docs.one(RECLAMO_SQL + " WHERE r.id_recla = :id", id, "Reclamo");
    }

    @Transactional
    public Map<String, Object> crearReclamo(ReclamoReq req, int idPer) {
        int estPedido = docs.lockEstado("r_pedido", "id_rped", req.idRped(), "El pedido");
        if (estPedido == ANULADO) {
            throw ApiException.conflict("No se puede reclamar sobre un pedido anulado");
        }
        if (req.idPed() != null) {
            Integer rped = jdbc.sql("SELECT id_rped FROM ped_flete WHERE id_ped = :id").param("id", req.idPed())
                    .query(Integer.class).optional()
                    .orElseThrow(() -> ApiException.badRequest("El pedido de flete no existe"));
            if (!rped.equals(req.idRped())) {
                throw ApiException.badRequest("El pedido de flete no pertenece al pedido seleccionado");
            }
        }
        int id = jdbc.sql("""
                INSERT INTO reclamos (fecha, descrip, id_rped, id_ped, id_per) VALUES (:f, :d, :r, :p, :per)
                RETURNING id_recla
                """)
                .param("f", req.fecha()).param("d", up(req.descrip())).param("r", req.idRped())
                .param("p", req.idPed()).param("per", idPer)
                .query(Integer.class).single();
        return reclamo(id);
    }

    /** Cierre del reclamo. El legacy lo marcaba como ANULADO (id_est=0); ahora queda FINALIZADO. */
    @Transactional
    public Map<String, Object> resolverReclamo(int id, ResolucionReclamo req) {
        docs.requireEstado(docs.lockEstado("reclamos", "id_recla", id, "El reclamo"), "El reclamo", PENDIENTE);
        jdbc.sql("""
                UPDATE reclamos SET solucion = :s, avisado = :a, fecha_c = CURRENT_DATE, id_est = :est
                WHERE id_recla = :id
                """)
                .param("s", up(req.solucion())).param("a", req.avisado()).param("est", FINALIZADO).param("id", id)
                .update();
        return reclamo(id);
    }

    @Transactional
    public void anularReclamo(int id) {
        docs.requireEstado(docs.lockEstado("reclamos", "id_recla", id, "El reclamo"), "El reclamo", PENDIENTE);
        docs.setEstado("reclamos", "id_recla", id, ANULADO);
    }
}
