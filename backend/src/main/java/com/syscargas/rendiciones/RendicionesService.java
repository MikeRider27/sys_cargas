package com.syscargas.rendiciones;

import static com.syscargas.common.Estado.ACTIVO;
import static com.syscargas.common.Estado.ANULADO;
import static com.syscargas.common.Estado.FINALIZADO;
import static com.syscargas.common.Estado.PENDIENTE;

import com.syscargas.common.ApiException;
import com.syscargas.common.DocSupport;
import com.syscargas.common.Rows;
import com.syscargas.rendiciones.RendicionesDtos.AperturaReq;
import com.syscargas.rendiciones.RendicionesDtos.CierreReq;
import com.syscargas.rendiciones.RendicionesDtos.ComprobanteReq;
import com.syscargas.rendiciones.RendicionesDtos.RendicionReq;
import com.syscargas.rendiciones.RendicionesDtos.ViaticoReq;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RendicionesService {

    private final JdbcClient jdbc;
    private final DocSupport docs;

    public RendicionesService(JdbcClient jdbc, DocSupport docs) {
        this.jdbc = jdbc;
        this.docs = docs;
    }

    private static String up(String s) {
        return s == null || s.isBlank() ? null : s.trim().toUpperCase(Locale.ROOT);
    }

    // =====================================================================
    // Apertura y cierre de caja
    // =====================================================================
    private static final String CAJA_SQL = """
            SELECT a.id_ap_cie, a.id_caj, c.descrip AS caja, a.id_mon, m.abrev AS moneda,
                   p.nombres || ' ' || p.apellidos AS personal, a.fecha_hora_ap, a.monto_inic,
                   coalesce(v.total, 0) AS viaticos, a.monto_inic - coalesce(v.total, 0) AS saldo_esperado,
                   a.fecha_hora_cierr, a.monto_cierr,
                   a.monto_cierr - (a.monto_inic - coalesce(v.total, 0)) AS diferencia_cierre,
                   a.id_est, CASE WHEN a.id_est = 0 THEN 'ANULADO'
                                  WHEN a.fecha_hora_cierr IS NULL THEN 'ABIERTA' ELSE 'CERRADA' END AS estado
            FROM aper_cierr_caja a
            JOIN caja c ON c.id_caj = a.id_caj
            JOIN moneda m ON m.id_mon = a.id_mon
            JOIN personal p ON p.id_per = a.id_per
            LEFT JOIN (SELECT id_ap_cie, sum(monto) AS total FROM viatico WHERE id_est <> 0 GROUP BY id_ap_cie) v
                   ON v.id_ap_cie = a.id_ap_cie
            """;

    public List<Map<String, Object>> aperturas() {
        return jdbc.sql(CAJA_SQL + " ORDER BY a.id_ap_cie DESC LIMIT 1000").query(Rows.MAP).list();
    }

    @Transactional
    public Map<String, Object> abrir(AperturaReq req, int idPer) {
        docs.requireEstado(docs.lockEstado("caja", "id_caj", req.idCaj(), "La caja"), "La caja", ACTIVO);
        docs.requireActivo("moneda", "id_mon", req.idMon(), "La moneda");
        long abiertas = jdbc.sql("SELECT count(*) FROM aper_cierr_caja WHERE id_caj = :c AND fecha_hora_cierr IS NULL AND id_est <> 0")
                .param("c", req.idCaj()).query(Long.class).single();
        if (abiertas > 0) {
            throw ApiException.conflict("La caja ya tiene una apertura sin cerrar");
        }
        int id = jdbc.sql("""
                INSERT INTO aper_cierr_caja (id_caj, id_per, id_mon, monto_inic) VALUES (:c, :p, :m, :monto) RETURNING id_ap_cie
                """)
                .param("c", req.idCaj()).param("p", idPer).param("m", req.idMon()).param("monto", req.montoInic())
                .query(Integer.class).single();
        return docs.one(CAJA_SQL + " WHERE a.id_ap_cie = :id", id, "Apertura");
    }

    @Transactional
    public Map<String, Object> cerrar(int id, CierreReq req) {
        docs.requireEstado(docs.lockEstado("aper_cierr_caja", "id_ap_cie", id, "La apertura"), "La apertura", ACTIVO);
        int n = jdbc.sql("""
                UPDATE aper_cierr_caja SET fecha_hora_cierr = LOCALTIMESTAMP(0), monto_cierr = :m, id_est = :est
                WHERE id_ap_cie = :id AND fecha_hora_cierr IS NULL
                """)
                .param("m", req.montoCierr()).param("est", FINALIZADO).param("id", id).update();
        if (n == 0) {
            throw ApiException.conflict("La caja ya está cerrada");
        }
        return docs.one(CAJA_SQL + " WHERE a.id_ap_cie = :id", id, "Apertura");
    }

    // =====================================================================
    // Viaticos
    // =====================================================================
    private static final String VIATICO_SQL = """
            SELECT v.id_viat, v.fecha, v.id_per, p.nombres || ' ' || p.apellidos AS beneficiario, v.id_viaj,
                   v.id_ap_cie, c.descrip AS caja, m.abrev AS moneda, v.monto, v.obs,
                   coalesce(k.total, 0) AS comprobado, v.monto - coalesce(k.total, 0) AS saldo,
                   v.id_est, e.descrip AS estado
            FROM viatico v
            JOIN personal p ON p.id_per = v.id_per
            JOIN aper_cierr_caja a ON a.id_ap_cie = v.id_ap_cie
            JOIN caja c ON c.id_caj = a.id_caj
            JOIN moneda m ON m.id_mon = a.id_mon
            JOIN estado e ON e.id_est = v.id_est
            LEFT JOIN (SELECT id_viat, sum(monto) AS total FROM comprobante WHERE id_est <> 0 GROUP BY id_viat) k
                   ON k.id_viat = v.id_viat
            """;

    public List<Map<String, Object>> viaticos() {
        return jdbc.sql(VIATICO_SQL + " ORDER BY v.id_viat DESC LIMIT 1000").query(Rows.MAP).list();
    }

    public Map<String, Object> viatico(int id) {
        Map<String, Object> m = docs.one(VIATICO_SQL + " WHERE v.id_viat = :id", id, "Viático");
        m.put("comprobantes", docs.list("""
                SELECT id_comp, fecha, nro_comp, descrip, monto, id_est FROM comprobante
                WHERE id_viat = :id AND id_est <> 0 ORDER BY fecha, id_comp
                """, id));
        return m;
    }

    @Transactional
    public Map<String, Object> crearViatico(ViaticoReq req) {
        docs.requireEstado(docs.lockEstado("aper_cierr_caja", "id_ap_cie", req.idApCie(), "La caja"),
                "La caja", ACTIVO);
        docs.requireActivo("personal", "id_per", req.idPer(), "El beneficiario");
        if (req.idViaj() != null) {
            int est = jdbc.sql("SELECT id_est FROM r_viaje WHERE id_viaj = :id").param("id", req.idViaj())
                    .query(Integer.class).optional().orElseThrow(() -> ApiException.badRequest("El viaje no existe"));
            if (est == ANULADO) {
                throw ApiException.badRequest("El viaje está anulado");
            }
        }
        BigDecimal saldo = jdbc.sql("""
                SELECT a.monto_inic - coalesce((SELECT sum(monto) FROM viatico WHERE id_ap_cie = a.id_ap_cie AND id_est <> 0), 0)
                FROM aper_cierr_caja a WHERE a.id_ap_cie = :id
                """).param("id", req.idApCie()).query(BigDecimal.class).single();
        if (req.monto().compareTo(saldo) > 0) {
            throw ApiException.conflict("Saldo insuficiente en caja: disponible " + saldo.toPlainString());
        }
        int id = jdbc.sql("""
                INSERT INTO viatico (fecha, id_per, id_viaj, id_ap_cie, monto, obs)
                VALUES (:f, :p, :v, :a, :m, :o) RETURNING id_viat
                """)
                .param("f", req.fecha()).param("p", req.idPer()).param("v", req.idViaj())
                .param("a", req.idApCie()).param("m", req.monto()).param("o", up(req.obs()))
                .query(Integer.class).single();
        return viatico(id);
    }

    @Transactional
    public void anularViatico(int id) {
        docs.requireEstado(docs.lockEstado("viatico", "id_viat", id, "El viático"), "El viático", PENDIENTE);
        long comps = jdbc.sql("SELECT count(*) FROM comprobante WHERE id_viat = :id AND id_est <> 0")
                .param("id", id).query(Long.class).single();
        if (comps > 0) {
            throw ApiException.conflict("El viático tiene comprobantes cargados; anúlelos primero");
        }
        Integer abierta = jdbc.sql("""
                SELECT count(*) FROM viatico v JOIN aper_cierr_caja a ON a.id_ap_cie = v.id_ap_cie
                WHERE v.id_viat = :id AND a.fecha_hora_cierr IS NULL
                """).param("id", id).query(Integer.class).single();
        if (abierta == 0) {
            throw ApiException.conflict("La caja que entregó el viático ya está cerrada");
        }
        docs.setEstado("viatico", "id_viat", id, ANULADO);
    }

    // =====================================================================
    // Comprobantes
    // =====================================================================
    private static final String COMP_SQL = """
            SELECT k.id_comp, k.id_viat, p.nombres || ' ' || p.apellidos AS beneficiario, k.fecha, k.nro_comp,
                   k.descrip, k.monto, k.id_est, e.descrip AS estado
            FROM comprobante k
            JOIN viatico v ON v.id_viat = k.id_viat
            JOIN personal p ON p.id_per = v.id_per
            JOIN estado e ON e.id_est = k.id_est
            """;

    public List<Map<String, Object>> comprobantes() {
        return jdbc.sql(COMP_SQL + " ORDER BY k.id_comp DESC LIMIT 1000").query(Rows.MAP).list();
    }

    @Transactional
    public Map<String, Object> crearComprobante(ComprobanteReq req) {
        docs.requireEstado(docs.lockEstado("viatico", "id_viat", req.idViat(), "El viático"), "El viático", PENDIENTE);
        LocalDate fechaViat = jdbc.sql("SELECT fecha FROM viatico WHERE id_viat = :id").param("id", req.idViat())
                .query(LocalDate.class).single();
        if (req.fecha().isBefore(fechaViat)) {
            throw ApiException.badRequest("La fecha del comprobante no puede ser anterior a la del viático");
        }
        int id = jdbc.sql("""
                INSERT INTO comprobante (id_viat, fecha, nro_comp, descrip, monto) VALUES (:v, :f, :n, :d, :m) RETURNING id_comp
                """)
                .param("v", req.idViat()).param("f", req.fecha()).param("n", req.nroComp().trim().toUpperCase(Locale.ROOT))
                .param("d", up(req.descrip())).param("m", req.monto())
                .query(Integer.class).single();
        return docs.one(COMP_SQL + " WHERE k.id_comp = :id", id, "Comprobante");
    }

    @Transactional
    public void anularComprobante(int id) {
        docs.requireEstado(docs.lockEstado("comprobante", "id_comp", id, "El comprobante"), "El comprobante", ACTIVO);
        int viat = jdbc.sql("SELECT id_viat FROM comprobante WHERE id_comp = :id").param("id", id).query(Integer.class).single();
        docs.requireEstado(docs.lockEstado("viatico", "id_viat", viat, "El viático"), "El viático", PENDIENTE);
        docs.setEstado("comprobante", "id_comp", id, ANULADO);
    }

    // =====================================================================
    // Rendiciones
    // =====================================================================
    private static final String REND_SQL = """
            SELECT r.id_rend, r.id_viat, r.fecha, p.nombres || ' ' || p.apellidos AS beneficiario, m.abrev AS moneda,
                   r.total_asignado, r.total_rendido, r.diferencia, r.obs, r.id_est, e.descrip AS estado
            FROM rendiciones r
            JOIN viatico v ON v.id_viat = r.id_viat
            JOIN personal p ON p.id_per = v.id_per
            JOIN aper_cierr_caja a ON a.id_ap_cie = v.id_ap_cie
            JOIN moneda m ON m.id_mon = a.id_mon
            JOIN estado e ON e.id_est = r.id_est
            """;

    public List<Map<String, Object>> rendiciones() {
        return jdbc.sql(REND_SQL + " ORDER BY r.id_rend DESC LIMIT 1000").query(Rows.MAP).list();
    }

    @Transactional
    public Map<String, Object> crearRendicion(RendicionReq req, int idPer) {
        docs.requireEstado(docs.lockEstado("viatico", "id_viat", req.idViat(), "El viático"), "El viático", PENDIENTE);
        int id = jdbc.sql("""
                INSERT INTO rendiciones (id_viat, total_asignado, total_rendido, diferencia, obs, id_per)
                SELECT v.id_viat, v.monto, t.total, v.monto - t.total, :obs, :per
                FROM viatico v,
                     LATERAL (SELECT coalesce(sum(monto), 0) AS total FROM comprobante
                              WHERE id_viat = v.id_viat AND id_est <> 0) t
                WHERE v.id_viat = :v
                RETURNING id_rend
                """)
                .param("v", req.idViat()).param("obs", up(req.obs())).param("per", idPer)
                .query(Integer.class).single();
        docs.setEstado("viatico", "id_viat", req.idViat(), FINALIZADO);
        return docs.one(REND_SQL + " WHERE r.id_rend = :id", id, "Rendición");
    }

    @Transactional
    public void anularRendicion(int id) {
        docs.requireEstado(docs.lockEstado("rendiciones", "id_rend", id, "La rendición"), "La rendición", ACTIVO);
        docs.setEstado("rendiciones", "id_rend", id, ANULADO);
        jdbc.sql("UPDATE viatico SET id_est = 2 WHERE id_viat = (SELECT id_viat FROM rendiciones WHERE id_rend = :id)")
                .param("id", id).update();
    }
}
