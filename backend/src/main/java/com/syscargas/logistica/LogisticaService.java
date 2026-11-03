package com.syscargas.logistica;

import static com.syscargas.common.Estado.ACTIVO;
import static com.syscargas.common.Estado.ANULADO;
import static com.syscargas.common.Estado.EN_PROCESO;
import static com.syscargas.common.Estado.FINALIZADO;
import static com.syscargas.common.Estado.PENDIENTE;

import com.syscargas.common.ApiException;
import com.syscargas.common.DocSupport;
import com.syscargas.common.Rows;
import com.syscargas.logistica.LogisticaDtos.AsignacionReq;
import com.syscargas.logistica.LogisticaDtos.FleteroReq;
import com.syscargas.logistica.LogisticaDtos.ItemAcc;
import com.syscargas.logistica.LogisticaDtos.LlegadaReq;
import com.syscargas.logistica.LogisticaDtos.SalidaReq;
import com.syscargas.logistica.LogisticaDtos.ViajeReq;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogisticaService {

    private final JdbcClient jdbc;
    private final DocSupport docs;

    public LogisticaService(JdbcClient jdbc, DocSupport docs) {
        this.jdbc = jdbc;
        this.docs = docs;
    }

    // =====================================================================
    // Asignacion de equipos
    // =====================================================================
    private static final String ASIG_SQL = """
            SELECT a.id_asig, a.fecha_carg, a.descrip, a.id_ped, a.id_viaj,
                   a.id_cond, c.nombres || ' ' || c.apellidos AS conductor,
                   a.id_tc, t.chapa_nr AS tracto, a.id_semi, s.chapa_nro AS semirremolque,
                   a.id_est, e.descrip AS estado, p.nombres || ' ' || p.apellidos AS personal,
                   sa.id_salid, sa.fecha_sal, sa.kilom_sal, ll.id_lleg, ll.fecha_lleg, ll.kilom_lleg,
                   ll.kilom_lleg - sa.kilom_sal AS km_recorridos
            FROM asignacion a
            JOIN personal c ON c.id_per = a.id_cond
            JOIN tracto t ON t.id_tc = a.id_tc
            JOIN semirremolque s ON s.id_semi = a.id_semi
            JOIN personal p ON p.id_per = a.id_per
            JOIN estado e ON e.id_est = a.id_est
            LEFT JOIN r_salida sa ON sa.id_asig = a.id_asig AND sa.id_est <> 0
            LEFT JOIN r_llegada ll ON ll.id_salid = sa.id_salid AND ll.id_est <> 0
            """;

    public List<Map<String, Object>> asignaciones() {
        return jdbc.sql(ASIG_SQL + " ORDER BY a.id_asig DESC LIMIT 1000").query(Rows.MAP).list();
    }

    public Map<String, Object> asignacion(int id) {
        Map<String, Object> m = docs.one(ASIG_SQL + " WHERE a.id_asig = :id", id, "Asignación");
        m.put("accesorios", docs.list("""
                SELECT d.item, d.id_acc, ac.descrip AS accesorio, d.obs
                FROM d_acc d JOIN accesorios ac ON ac.id_acc = d.id_acc WHERE d.id_asig = :id ORDER BY d.item
                """, id));
        return m;
    }

    @Transactional
    public Map<String, Object> crearAsignacion(AsignacionReq req, int idPer) {
        int estPed = docs.lockEstado("ped_flete", "id_ped", req.idPed(), "El pedido de flete");
        docs.requireEstado(estPed, "El pedido de flete", PENDIENTE, EN_PROCESO);
        // bloquea los recursos para que dos asignaciones simultaneas no tomen el mismo equipo
        docs.requireEstado(docs.lockEstado("tracto", "id_tc", req.idTc(), "El tracto"), "El tracto", ACTIVO);
        docs.requireEstado(docs.lockEstado("semirremolque", "id_semi", req.idSemi(), "El semirremolque"),
                "El semirremolque", ACTIVO);
        docs.requireEstado(docs.lockEstado("personal", "id_per", req.idCond(), "El conductor"), "El conductor", ACTIVO);
        List<String> ocupados = jdbc.sql("""
                SELECT CASE WHEN a.id_tc = :tc THEN 'el tracto'
                            WHEN a.id_semi = :semi THEN 'el semirremolque'
                            ELSE 'el conductor' END || ' (asignación Nº ' || a.id_asig || ')'
                FROM asignacion a
                WHERE a.id_est IN (2, 3) AND (a.id_tc = :tc OR a.id_semi = :semi OR a.id_cond = :cond)
                """)
                .param("tc", req.idTc()).param("semi", req.idSemi()).param("cond", req.idCond())
                .query(String.class).list();
        if (!ocupados.isEmpty()) {
            throw ApiException.conflict("Recursos ocupados en otra asignación vigente: " + String.join(", ", ocupados));
        }
        int id = jdbc.sql("""
                INSERT INTO asignacion (fecha_carg, descrip, id_ped, id_cond, id_tc, id_semi, id_per)
                VALUES (:f, :d, :ped, :cond, :tc, :semi, :per) RETURNING id_asig
                """)
                .param("f", req.fechaCarg()).param("d", req.descrip().trim().toUpperCase(Locale.ROOT))
                .param("ped", req.idPed()).param("cond", req.idCond()).param("tc", req.idTc())
                .param("semi", req.idSemi()).param("per", idPer)
                .query(Integer.class).single();
        int item = 1;
        for (ItemAcc acc : req.accesorios()) {
            docs.requireActivo("accesorios", "id_acc", acc.idAcc(), "El accesorio");
            jdbc.sql("INSERT INTO d_acc (id_asig, item, id_acc, obs) VALUES (:id, :item, :acc, :obs)")
                    .param("id", id).param("item", item++).param("acc", acc.idAcc())
                    .param("obs", acc.obs() == null || acc.obs().isBlank() ? null : acc.obs().trim().toUpperCase(Locale.ROOT))
                    .update();
        }
        if (estPed == PENDIENTE) {
            docs.setEstado("ped_flete", "id_ped", req.idPed(), EN_PROCESO);
        }
        return asignacion(id);
    }

    @Transactional
    public void anularAsignacion(int id) {
        docs.requireEstado(docs.lockEstado("asignacion", "id_asig", id, "La asignación"), "La asignación", PENDIENTE);
        docs.setEstado("asignacion", "id_asig", id, ANULADO);
        jdbc.sql("""
                UPDATE ped_flete p SET id_est = 2
                WHERE p.id_ped = (SELECT id_ped FROM asignacion WHERE id_asig = :id) AND p.id_est = 3
                  AND NOT EXISTS (SELECT 1 FROM asignacion a WHERE a.id_ped = p.id_ped AND a.id_est <> 0)
                """).param("id", id).update();
    }

    // =====================================================================
    // Registro de salida
    // =====================================================================
    private static final String SALIDA_SQL = """
            SELECT s.id_salid, s.fecha_sal, s.kilom_sal, s.comb_sal, s.id_asig,
                   t.chapa_nr AS tracto, c.nombres || ' ' || c.apellidos AS conductor,
                   s.id_est, e.descrip AS estado
            FROM r_salida s
            JOIN asignacion a ON a.id_asig = s.id_asig
            JOIN tracto t ON t.id_tc = a.id_tc
            JOIN personal c ON c.id_per = a.id_cond
            JOIN estado e ON e.id_est = s.id_est
            """;

    public List<Map<String, Object>> salidas() {
        return jdbc.sql(SALIDA_SQL + " ORDER BY s.id_salid DESC LIMIT 1000").query(Rows.MAP).list();
    }

    @Transactional
    public Map<String, Object> crearSalida(SalidaReq req) {
        docs.requireEstado(docs.lockEstado("asignacion", "id_asig", req.idAsig(), "La asignación"),
                "La asignación", PENDIENTE);
        LocalDate fechaCarg = jdbc.sql("SELECT fecha_carg FROM asignacion WHERE id_asig = :id")
                .param("id", req.idAsig()).query(LocalDate.class).single();
        if (req.fechaSal().isBefore(fechaCarg)) {
            throw ApiException.badRequest("La fecha de salida no puede ser anterior a la fecha de carga");
        }
        int id = jdbc.sql("""
                INSERT INTO r_salida (fecha_sal, kilom_sal, comb_sal, id_asig) VALUES (:f, :k, :c, :a) RETURNING id_salid
                """)
                .param("f", req.fechaSal()).param("k", req.kilomSal()).param("c", req.combSal()).param("a", req.idAsig())
                .query(Integer.class).single();
        docs.setEstado("asignacion", "id_asig", req.idAsig(), EN_PROCESO);
        jdbc.sql("""
                UPDATE r_viaje SET id_est = 3
                WHERE id_viaj = (SELECT id_viaj FROM asignacion WHERE id_asig = :a) AND id_est = 2
                """).param("a", req.idAsig()).update();
        return docs.one(SALIDA_SQL + " WHERE s.id_salid = :id", id, "Salida");
    }

    @Transactional
    public void anularSalida(int id) {
        docs.requireEstado(docs.lockEstado("r_salida", "id_salid", id, "La salida"), "La salida", ACTIVO);
        long llegadas = jdbc.sql("SELECT count(*) FROM r_llegada WHERE id_salid = :id AND id_est <> 0")
                .param("id", id).query(Long.class).single();
        if (llegadas > 0) {
            throw ApiException.conflict("La salida ya tiene una llegada registrada; anule primero la llegada");
        }
        docs.setEstado("r_salida", "id_salid", id, ANULADO);
        jdbc.sql("UPDATE asignacion SET id_est = 2 WHERE id_asig = (SELECT id_asig FROM r_salida WHERE id_salid = :id)")
                .param("id", id).update();
    }

    // =====================================================================
    // Registro de llegada (cierra asignacion y propaga estados)
    // =====================================================================
    private static final String LLEGADA_SQL = """
            SELECT l.id_lleg, l.fecha_lleg, l.kilom_lleg, l.comb_lleg, l.id_salid, s.id_asig,
                   s.kilom_sal, l.kilom_lleg - s.kilom_sal AS km_recorridos,
                   s.comb_sal - l.comb_lleg AS comb_consumido, l.id_est, e.descrip AS estado
            FROM r_llegada l
            JOIN r_salida s ON s.id_salid = l.id_salid
            JOIN estado e ON e.id_est = l.id_est
            """;

    public List<Map<String, Object>> llegadas() {
        return jdbc.sql(LLEGADA_SQL + " ORDER BY l.id_lleg DESC LIMIT 1000").query(Rows.MAP).list();
    }

    record SalidaInfo(int idAsig, LocalDate fechaSal, BigDecimal kilomSal) {}

    @Transactional
    public Map<String, Object> crearLlegada(LlegadaReq req) {
        docs.requireEstado(docs.lockEstado("r_salida", "id_salid", req.idSalid(), "La salida"), "La salida", ACTIVO);
        SalidaInfo s = jdbc.sql("SELECT id_asig, fecha_sal, kilom_sal FROM r_salida WHERE id_salid = :id")
                .param("id", req.idSalid()).query(SalidaInfo.class).single();
        if (req.fechaLleg().isBefore(s.fechaSal())) {
            throw ApiException.badRequest("La fecha de llegada no puede ser anterior a la de salida");
        }
        if (req.kilomLleg().compareTo(s.kilomSal()) < 0) {
            throw ApiException.badRequest("El kilometraje de llegada (" + req.kilomLleg()
                    + ") no puede ser menor al de salida (" + s.kilomSal() + ")");
        }
        int id = jdbc.sql("""
                INSERT INTO r_llegada (fecha_lleg, kilom_lleg, comb_lleg, id_salid) VALUES (:f, :k, :c, :s) RETURNING id_lleg
                """)
                .param("f", req.fechaLleg()).param("k", req.kilomLleg()).param("c", req.combLleg())
                .param("s", req.idSalid())
                .query(Integer.class).single();
        docs.setEstado("asignacion", "id_asig", s.idAsig(), FINALIZADO);
        propagarFinalizacion(s.idAsig());
        return docs.one(LLEGADA_SQL + " WHERE l.id_lleg = :id", id, "Llegada");
    }

    /** Finaliza pedido de flete, pedido del cliente y viaje cuando ya no les quedan asignaciones abiertas. */
    private void propagarFinalizacion(int idAsig) {
        jdbc.sql("""
                UPDATE ped_flete p SET id_est = 4
                WHERE p.id_ped = (SELECT id_ped FROM asignacion WHERE id_asig = :a)
                  AND NOT EXISTS (SELECT 1 FROM asignacion x WHERE x.id_ped = p.id_ped AND x.id_est IN (2, 3))
                """).param("a", idAsig).update();
        jdbc.sql("""
                UPDATE r_pedido r SET id_est = 4
                WHERE r.id_rped = (SELECT f.id_rped FROM asignacion a JOIN ped_flete f ON f.id_ped = a.id_ped WHERE a.id_asig = :a)
                  AND NOT EXISTS (SELECT 1 FROM ped_flete x WHERE x.id_rped = r.id_rped AND x.id_est IN (2, 3))
                """).param("a", idAsig).update();
        jdbc.sql("""
                UPDATE r_viaje v SET id_est = 4
                WHERE v.id_viaj = (SELECT id_viaj FROM asignacion WHERE id_asig = :a)
                  AND NOT EXISTS (SELECT 1 FROM asignacion x WHERE x.id_viaj = v.id_viaj AND x.id_est IN (2, 3))
                """).param("a", idAsig).update();
    }

    @Transactional
    public void anularLlegada(int id) {
        docs.requireEstado(docs.lockEstado("r_llegada", "id_lleg", id, "La llegada"), "La llegada", ACTIVO);
        int idAsig = jdbc.sql("SELECT s.id_asig FROM r_llegada l JOIN r_salida s ON s.id_salid = l.id_salid WHERE l.id_lleg = :id")
                .param("id", id).query(Integer.class).single();
        docs.setEstado("r_llegada", "id_lleg", id, ANULADO);
        docs.setEstado("asignacion", "id_asig", idAsig, EN_PROCESO);
        // revierte la finalizacion propagada
        jdbc.sql("""
                UPDATE ped_flete SET id_est = 3 WHERE id_ped = (SELECT id_ped FROM asignacion WHERE id_asig = :a) AND id_est = 4
                """).param("a", idAsig).update();
        jdbc.sql("""
                UPDATE r_pedido SET id_est = 3
                WHERE id_rped = (SELECT f.id_rped FROM asignacion a JOIN ped_flete f ON f.id_ped = a.id_ped WHERE a.id_asig = :a)
                  AND id_est = 4
                """).param("a", idAsig).update();
        jdbc.sql("""
                UPDATE r_viaje SET id_est = 3 WHERE id_viaj = (SELECT id_viaj FROM asignacion WHERE id_asig = :a) AND id_est = 4
                """).param("a", idAsig).update();
    }

    // =====================================================================
    // Registro de viaje y asignacion de fleteros
    // =====================================================================
    private static final String VIAJE_SQL = """
            SELECT v.id_viaj, v.fecha_sal, v.id_iti, i.descrip AS itinerario, v.id_adu, a.descrip AS aduana,
                   v.id_ruta, r.descrip AS ruta, v.id_est, e.descrip AS estado,
                   p.nombres || ' ' || p.apellidos AS personal,
                   (SELECT count(*) FROM asignacion x WHERE x.id_viaj = v.id_viaj AND x.id_est <> 0) AS asignaciones
            FROM r_viaje v
            JOIN itinerario i ON i.id_iti = v.id_iti
            JOIN aduana a ON a.id_adu = v.id_adu
            JOIN ruta r ON r.id_ruta = v.id_ruta
            JOIN personal p ON p.id_per = v.id_per
            JOIN estado e ON e.id_est = v.id_est
            """;

    public List<Map<String, Object>> viajes() {
        return jdbc.sql(VIAJE_SQL + " ORDER BY v.id_viaj DESC LIMIT 1000").query(Rows.MAP).list();
    }

    public Map<String, Object> viaje(int id) {
        Map<String, Object> m = docs.one(VIAJE_SQL + " WHERE v.id_viaj = :id", id, "Viaje");
        m.put("ciudades", docs.list("""
                SELECT vc.item, vc.id_ciud, c.descrip AS ciudad
                FROM viaje_ciudad vc JOIN ciudad c ON c.id_ciud = vc.id_ciud WHERE vc.id_viaj = :id ORDER BY vc.item
                """, id));
        m.put("lista_asignaciones", docs.list("""
                SELECT a.id_asig, t.chapa_nr AS tracto, s.chapa_nro AS semirremolque,
                       c.nombres || ' ' || c.apellidos AS conductor, e.descrip AS estado
                FROM asignacion a JOIN tracto t ON t.id_tc = a.id_tc JOIN semirremolque s ON s.id_semi = a.id_semi
                JOIN personal c ON c.id_per = a.id_cond JOIN estado e ON e.id_est = a.id_est
                WHERE a.id_viaj = :id AND a.id_est <> 0 ORDER BY a.id_asig
                """, id));
        m.put("fleteros", docs.list("""
                SELECT af.id_aflet, af.fecha, af.id_flet, f.nombres || ' ' || f.apellidos AS fletero, f.camion,
                       f.telef, e.descrip AS estado, af.id_est
                FROM asig_flet af JOIN fletero f ON f.id_flet = af.id_flet JOIN estado e ON e.id_est = af.id_est
                WHERE af.id_viaj = :id ORDER BY af.id_aflet
                """, id));
        return m;
    }

    @Transactional
    public Map<String, Object> crearViaje(ViajeReq req, int idPer) {
        docs.requireActivo("itinerario", "id_iti", req.idIti(), "El itinerario");
        docs.requireActivo("aduana", "id_adu", req.idAdu(), "La aduana");
        docs.requireActivo("ruta", "id_ruta", req.idRuta(), "La ruta");
        int id = jdbc.sql("""
                INSERT INTO r_viaje (fecha_sal, id_per, id_iti, id_adu, id_ruta) VALUES (:f, :p, :i, :a, :r) RETURNING id_viaj
                """)
                .param("f", req.fechaSal()).param("p", idPer).param("i", req.idIti())
                .param("a", req.idAdu()).param("r", req.idRuta())
                .query(Integer.class).single();
        int item = 1;
        for (Integer ciudad : req.ciudades()) {
            jdbc.sql("INSERT INTO viaje_ciudad (id_viaj, item, id_ciud) VALUES (:v, :i, :c)")
                    .param("v", id).param("i", item++).param("c", ciudad).update();
        }
        boolean enCurso = false;
        for (Integer asig : req.asignaciones().stream().distinct().toList()) {
            int est = docs.lockEstado("asignacion", "id_asig", asig, "La asignación " + asig);
            docs.requireEstado(est, "La asignación " + asig, PENDIENTE, EN_PROCESO);
            int n = jdbc.sql("UPDATE asignacion SET id_viaj = :v WHERE id_asig = :a AND id_viaj IS NULL")
                    .param("v", id).param("a", asig).update();
            if (n == 0) {
                throw ApiException.conflict("La asignación " + asig + " ya pertenece a otro viaje");
            }
            enCurso |= est == EN_PROCESO;
        }
        if (enCurso) {
            docs.setEstado("r_viaje", "id_viaj", id, EN_PROCESO);
        }
        return viaje(id);
    }

    @Transactional
    public void anularViaje(int id) {
        docs.requireEstado(docs.lockEstado("r_viaje", "id_viaj", id, "El viaje"), "El viaje", PENDIENTE);
        jdbc.sql("UPDATE asignacion SET id_viaj = NULL WHERE id_viaj = :id").param("id", id).update();
        jdbc.sql("UPDATE asig_flet SET id_est = 0 WHERE id_viaj = :id").param("id", id).update();
        docs.setEstado("r_viaje", "id_viaj", id, ANULADO);
    }

    @Transactional
    public Map<String, Object> asignarFletero(int idViaj, FleteroReq req) {
        docs.requireEstado(docs.lockEstado("r_viaje", "id_viaj", idViaj, "El viaje"), "El viaje", PENDIENTE, EN_PROCESO);
        docs.requireActivo("fletero", "id_flet", req.idFlet(), "El fletero");
        long dup = jdbc.sql("SELECT count(*) FROM asig_flet WHERE id_viaj = :v AND id_flet = :f AND id_est <> 0")
                .param("v", idViaj).param("f", req.idFlet()).query(Long.class).single();
        if (dup > 0) {
            throw ApiException.conflict("El fletero ya está asignado a este viaje");
        }
        jdbc.sql("INSERT INTO asig_flet (fecha, id_flet, id_viaj) VALUES (:f, :fl, :v)")
                .param("f", req.fecha()).param("fl", req.idFlet()).param("v", idViaj).update();
        return viaje(idViaj);
    }

    @Transactional
    public void quitarFletero(int idAflet) {
        docs.requireEstado(docs.lockEstado("asig_flet", "id_aflet", idAflet, "La asignación de fletero"),
                "La asignación de fletero", ACTIVO);
        docs.setEstado("asig_flet", "id_aflet", idAflet, ANULADO);
    }
}
