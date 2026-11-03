package com.syscargas.dashboard;

import com.syscargas.common.Rows;
import java.util.Map;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final JdbcClient jdbc;

    public DashboardController(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/api/dashboard")
    public Map<String, Object> resumen() {
        return jdbc.sql("""
                SELECT (SELECT count(*) FROM r_pedido WHERE id_est = 2) AS pedidos_pendientes,
                       (SELECT count(*) FROM r_pedido WHERE id_est = 3) AS pedidos_en_proceso,
                       (SELECT count(*) FROM ped_flete WHERE id_est IN (2, 3)) AS fletes_abiertos,
                       (SELECT count(*) FROM asignacion WHERE id_est = 3) AS unidades_en_ruta,
                       (SELECT count(*) FROM asignacion WHERE id_est = 2) AS asignaciones_sin_salida,
                       (SELECT count(*) FROM reclamos WHERE id_est = 2) AS reclamos_pendientes,
                       (SELECT count(*) FROM viatico WHERE id_est = 2) AS viaticos_sin_rendir,
                       (SELECT count(*) FROM aper_cierr_caja WHERE fecha_hora_cierr IS NULL AND id_est <> 0) AS cajas_abiertas,
                       (SELECT count(*) FROM emi_crt WHERE id_est = 1 AND anho = extract(year FROM CURRENT_DATE)) AS crt_anho
                """).query(Rows.MAP).single();
    }
}
