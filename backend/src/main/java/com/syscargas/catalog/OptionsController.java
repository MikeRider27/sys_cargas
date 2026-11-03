package com.syscargas.catalog;

import com.syscargas.common.Rows;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Opciones (value, label) para combos: catalogos y documentos en estado utilizable. */
@RestController
@RequestMapping("/api/opciones")
public class OptionsController {

    private static final Map<String, String> DOCUMENTOS = Map.ofEntries(
            Map.entry("pedidos_abiertos", """
                    SELECT r.id_rped AS value,
                           'Nº ' || r.id_rped || ' - ' || c.nombres || coalesce(' ' || c.apellidos, '') || ' - ' || r.descrip AS label
                    FROM r_pedido r JOIN cliente c ON c.id_cliente = r.id_cliente
                    WHERE r.id_est IN (2, 3) ORDER BY r.id_rped DESC
                    """),
            Map.entry("pedidos_todos", """
                    SELECT r.id_rped AS value,
                           'Nº ' || r.id_rped || ' - ' || c.nombres || coalesce(' ' || c.apellidos, '') || ' - ' || r.descrip AS label
                    FROM r_pedido r JOIN cliente c ON c.id_cliente = r.id_cliente
                    WHERE r.id_est <> 0 ORDER BY r.id_rped DESC
                    """),
            Map.entry("pedidos_flete_abiertos", """
                    SELECT p.id_ped AS value, 'Nº ' || p.id_ped || ' (pedido ' || p.id_rped || ') - ' || p.descrip AS label
                    FROM ped_flete p WHERE p.id_est IN (2, 3) ORDER BY p.id_ped DESC
                    """),
            Map.entry("pedidos_flete_todos", """
                    SELECT p.id_ped AS value, 'Nº ' || p.id_ped || ' (pedido ' || p.id_rped || ') - ' || p.descrip AS label
                    FROM ped_flete p WHERE p.id_est <> 0 ORDER BY p.id_ped DESC
                    """),
            Map.entry("crt", """
                    SELECT c.id_crt AS value, 'CRT ' || c.nro || '/' || c.anho || ' - ' || po.descrip || ' → ' || pd.descrip AS label
                    FROM emi_crt c JOIN pais po ON po.id_pais = c.id_pais_o JOIN pais pd ON pd.id_pais = c.id_pais_d
                    WHERE c.id_est <> 0 ORDER BY c.anho DESC, c.nro DESC
                    """),
            Map.entry("asignaciones_sin_salida", """
                    SELECT a.id_asig AS value, 'Nº ' || a.id_asig || ' - ' || t.chapa_nr || ' / ' || s.chapa_nro
                           || ' - ' || p.nombres || ' ' || p.apellidos AS label
                    FROM asignacion a JOIN tracto t ON t.id_tc = a.id_tc JOIN semirremolque s ON s.id_semi = a.id_semi
                    JOIN personal p ON p.id_per = a.id_cond
                    WHERE a.id_est = 2 ORDER BY a.id_asig DESC
                    """),
            Map.entry("asignaciones_sin_viaje", """
                    SELECT a.id_asig AS value, 'Nº ' || a.id_asig || ' - ' || t.chapa_nr || ' / ' || s.chapa_nro || ' - ' || a.descrip AS label
                    FROM asignacion a JOIN tracto t ON t.id_tc = a.id_tc JOIN semirremolque s ON s.id_semi = a.id_semi
                    WHERE a.id_est IN (2, 3) AND a.id_viaj IS NULL ORDER BY a.id_asig DESC
                    """),
            Map.entry("salidas_sin_llegada", """
                    SELECT s.id_salid AS value, 'Salida Nº ' || s.id_salid || ' - asignación ' || s.id_asig
                           || ' - ' || to_char(s.fecha_sal, 'DD/MM/YYYY') || ' - km ' || s.kilom_sal AS label
                    FROM r_salida s
                    WHERE s.id_est <> 0
                      AND NOT EXISTS (SELECT 1 FROM r_llegada l WHERE l.id_salid = s.id_salid AND l.id_est <> 0)
                    ORDER BY s.id_salid DESC
                    """),
            Map.entry("viajes_abiertos", """
                    SELECT v.id_viaj AS value, 'Viaje Nº ' || v.id_viaj || ' - ' || i.descrip || ' - ' || to_char(v.fecha_sal, 'DD/MM/YYYY') AS label
                    FROM r_viaje v JOIN itinerario i ON i.id_iti = v.id_iti
                    WHERE v.id_est IN (2, 3) ORDER BY v.id_viaj DESC
                    """),
            Map.entry("cajas_abiertas", """
                    SELECT a.id_ap_cie AS value, c.descrip || ' - apertura Nº ' || a.id_ap_cie || ' (' || m.abrev || ')' AS label
                    FROM aper_cierr_caja a JOIN caja c ON c.id_caj = a.id_caj JOIN moneda m ON m.id_mon = a.id_mon
                    WHERE a.fecha_hora_cierr IS NULL AND a.id_est <> 0 ORDER BY a.id_ap_cie DESC
                    """),
            Map.entry("viaticos_pendientes", """
                    SELECT v.id_viat AS value, 'Viático Nº ' || v.id_viat || ' - ' || p.nombres || ' ' || p.apellidos
                           || ' - ' || m.abrev || ' ' || to_char(v.monto, 'FM999G999G999G990D00') AS label
                    FROM viatico v JOIN personal p ON p.id_per = v.id_per
                    JOIN aper_cierr_caja a ON a.id_ap_cie = v.id_ap_cie JOIN moneda m ON m.id_mon = a.id_mon
                    WHERE v.id_est = 2 ORDER BY v.id_viat DESC
                    """),
            Map.entry("roles", "SELECT id_rol AS value, codigo || ' - ' || descrip AS label FROM rol ORDER BY codigo"));

    private final JdbcClient jdbc;
    private final CatalogService catalogs;
    private final CatalogRegistry registry;

    public OptionsController(JdbcClient jdbc, CatalogService catalogs, CatalogRegistry registry) {
        this.jdbc = jdbc;
        this.catalogs = catalogs;
        this.registry = registry;
    }

    @GetMapping("/{name}")
    public List<Map<String, Object>> options(@PathVariable String name) {
        String sql = DOCUMENTOS.get(name);
        if (sql != null) {
            return jdbc.sql(sql).query(Rows.MAP).list();
        }
        if (!registry.exists(name)) {
            throw com.syscargas.common.ApiException.notFound("Lista de opciones");
        }
        return catalogs.options(name);
    }
}
