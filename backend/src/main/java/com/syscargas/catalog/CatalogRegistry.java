package com.syscargas.catalog;

import static com.syscargas.catalog.CatalogDef.Col.date;
import static com.syscargas.catalog.CatalogDef.Col.decimal;
import static com.syscargas.catalog.CatalogDef.Col.email;
import static com.syscargas.catalog.CatalogDef.Col.integer;
import static com.syscargas.catalog.CatalogDef.Col.optSelect;
import static com.syscargas.catalog.CatalogDef.Col.optText;
import static com.syscargas.catalog.CatalogDef.Col.select;
import static com.syscargas.catalog.CatalogDef.Col.text;

import com.syscargas.common.ApiException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/** Referenciales del sistema (equivalen a las pantallas Ref_* del legacy). */
@Component
public class CatalogRegistry {

    private static final String DESCRIP = "%1$s.descrip";
    private static final String PERSONA = "%1$s.nombres || ' ' || %1$s.apellidos";

    private final Map<String, CatalogDef> defs = new LinkedHashMap<>();

    public CatalogRegistry() {
        // --- Empresa
        add("sucursal", "Sucursales", "Empresa", "sucursal", "id_suc", DESCRIP, true, List.of(
                text("descrip", "Descripción", 80), text("direc", "Dirección", 150),
                text("telef", "Teléfono", 30), email("correo", "Correo")));
        add("cargo", "Cargos", "Empresa", "cargo", "id_car", DESCRIP, true, List.of(
                text("descrip", "Descripción", 80)));
        add("personal", "Personal", "Empresa", "personal", "id_per", PERSONA, true, List.of(
                text("nro_doc", "Nro. documento", 20), text("nombres", "Nombres", 80),
                text("apellidos", "Apellidos", 80), date("fecha_nac", "Fecha nac.", false).hidden(),
                text("direcc", "Dirección", 150).hidden(), text("telef", "Teléfono", 30),
                select("id_car", "Cargo", "cargo"), select("id_suc", "Sucursal", "sucursal")));
        add("caja", "Cajas", "Empresa", "caja", "id_caj", DESCRIP, true, List.of(
                text("descrip", "Descripción", 60), select("id_suc", "Sucursal", "sucursal")));
        defs.put("rol", new CatalogDef("rol", "Roles", "Empresa", "rol", "id_rol", DESCRIP,
                List.of(text("codigo", "Código", 30), text("descrip", "Descripción", 80)), false, true, null));
        defs.put("estado", new CatalogDef("estado", "Estados", "Empresa", "estado", "id_est", DESCRIP,
                List.of(text("descrip", "Descripción", 40)), false, true, null));

        // --- Geografia
        add("pais", "Países", "Geografía", "pais", "id_pais", DESCRIP, true, List.of(
                text("descrip", "Descripción", 80), decimal("tarifa", "Tarifa flete (Gs.)", 0)));
        add("estad_prov", "Departamentos / Provincias", "Geografía", "estad_prov", "id_prov", DESCRIP, true, List.of(
                select("id_pais", "País", "pais"), text("descrip", "Descripción", 80)),
                """
                SELECT e.id_prov AS value, e.descrip || ' (' || p.descrip || ')' AS label
                FROM estad_prov e JOIN pais p ON p.id_pais = e.id_pais
                WHERE e.id_est = 1 ORDER BY label
                """);
        add("ciudad", "Ciudades", "Geografía", "ciudad", "id_ciud", DESCRIP, true, List.of(
                select("id_prov", "Dpto. / Provincia", "estad_prov"), text("descrip", "Descripción", 80)),
                """
                SELECT c.id_ciud AS value, c.descrip || ' (' || e.descrip || ', ' || p.descrip || ')' AS label
                FROM ciudad c JOIN estad_prov e ON e.id_prov = c.id_prov JOIN pais p ON p.id_pais = e.id_pais
                WHERE c.id_est = 1 ORDER BY label
                """);
        add("nacionalidad", "Nacionalidades", "Geografía", "nacionalidad", "id_nac", DESCRIP, true, List.of(
                text("descrip", "Descripción", 60)));

        // --- Pedidos
        add("cliente", "Clientes", "Pedidos", "cliente", "id_cliente",
                "%1$s.nombres || coalesce(' ' || %1$s.apellidos, '') || ' - ' || %1$s.nro_doc", true, List.of(
                select("id_tdoc", "Tipo doc.", "tipo_doc").hidden(), text("nro_doc", "Nro. documento", 30),
                text("nombres", "Nombres / Razón social", 120), optText("apellidos", "Apellidos", 80),
                date("fecha_nac", "Fecha nac.", false).hidden(), text("direcc", "Dirección", 150).hidden(),
                text("telef", "Teléfono", 30), email("correo", "Correo").hidden(),
                select("id_tcli", "Tipo cliente", "tipo_cli"), select("id_nac", "Nacionalidad", "nacionalidad")));
        add("tipo_cli", "Tipos de cliente", "Pedidos", "tipo_cli", "id_tcli", DESCRIP, false, List.of(
                text("descrip", "Descripción", 40)));
        add("tipo_doc", "Tipos de documento", "Pedidos", "tipo_doc", "id_tdoc", DESCRIP, false, List.of(
                text("descrip", "Descripción", 40)));
        add("despachante", "Despachantes", "Pedidos", "despachante", "id_desp", PERSONA, true, List.of(
                text("nro_doc", "Nro. documento", 20), text("nombres", "Nombres", 80),
                text("apellidos", "Apellidos", 80), text("telef", "Teléfono", 30)));
        add("mercaderia", "Mercaderías", "Pedidos", "mercaderia", "id_merc", DESCRIP, true, List.of(
                text("descrip", "Descripción", 120), select("id_tm", "Tipo mercadería", "tipo_merc")));
        add("tipo_merc", "Tipos de mercadería", "Pedidos", "tipo_merc", "id_tm", DESCRIP, false, List.of(
                text("descrip", "Descripción", 60)));
        add("tipo_carg", "Tipos de carga", "Pedidos", "tipo_carg", "id_tic", DESCRIP, false, List.of(
                text("descrip", "Descripción", 60)));
        add("moneda", "Monedas", "Pedidos", "moneda", "id_mon", "%1$s.abrev || ' - ' || %1$s.descrip", true, List.of(
                text("abrev", "Abreviatura", 10), text("descrip", "Descripción", 60)));
        add("aduana", "Aduanas", "Pedidos", "aduana", "id_adu", DESCRIP, true, List.of(
                text("descrip", "Descripción", 80), optSelect("id_ciud", "Ciudad", "ciudad")));

        // --- Logistica
        add("marca_t", "Marcas de tracto", "Logística", "marca_t", "id_mt", DESCRIP, false, List.of(
                text("descrip", "Descripción", 60)));
        add("modelo_t", "Modelos de tracto", "Logística", "modelo_t", "id_modt",
                "%1$s.descrip || ' ' || %1$s.anho", false, List.of(
                select("id_mt", "Marca", "marca_t"), text("descrip", "Modelo", 60), integer("anho", "Año", 1950)),
                """
                SELECT m.id_modt AS value, t.descrip || ' ' || m.descrip || ' (' || m.anho || ')' AS label
                FROM modelo_t m JOIN marca_t t ON t.id_mt = m.id_mt ORDER BY label
                """);
        add("tracto", "Tractos", "Logística", "tracto", "id_tc", "%1$s.chapa_nr || ' - ' || %1$s.chasis_nr", true, List.of(
                text("chapa_nr", "Chapa", 15), text("chasis_nr", "Chasis", 40),
                decimal("tara", "Tara (kg)", 1), select("id_modt", "Modelo", "modelo_t")));
        add("marca_s", "Marcas de semirremolque", "Logística", "marca_s", "id_ms", DESCRIP, false, List.of(
                text("descrip", "Descripción", 60)));
        add("tipo_semi", "Tipos de semirremolque", "Logística", "tipo_semi", "id_tsem", DESCRIP, false, List.of(
                text("descrip", "Descripción", 60)));
        add("semirremolque", "Semirremolques", "Logística", "semirremolque", "id_semi",
                "%1$s.chapa_nro || ' - ' || %1$s.cap_carg || ' kg'", true, List.of(
                text("chapa_nro", "Chapa", 15), text("chasis_nro", "Chasis", 40),
                decimal("cap_carg", "Capacidad (kg)", 1), decimal("largo", "Largo (m)", 0.01).hidden(),
                decimal("ancho", "Ancho (m)", 0.01).hidden(), decimal("alto", "Alto (m)", 0.01).hidden(),
                select("id_tsem", "Tipo", "tipo_semi"), select("id_ms", "Marca", "marca_s")));
        add("accesorios", "Accesorios", "Logística", "accesorios", "id_acc", DESCRIP, true, List.of(
                text("descrip", "Descripción", 80)));
        add("fletero", "Fleteros", "Logística", "fletero", "id_flet", PERSONA, true, List.of(
                text("nro_doc", "Nro. documento", 20), text("nombres", "Nombres", 80),
                text("apellidos", "Apellidos", 80), text("camion", "Camión", 80),
                text("telef", "Teléfono", 30), text("direc", "Dirección", 150).hidden(),
                text("nro_reg", "Nro. registro", 30)));
        add("itinerario", "Itinerarios", "Logística", "itinerario", "id_iti", DESCRIP, true, List.of(
                text("descrip", "Descripción", 80)));
        add("ruta", "Rutas", "Logística", "ruta", "id_ruta", DESCRIP, true, List.of(
                text("descrip", "Descripción", 80), decimal("extension_km", "Extensión (km)", 0.01),
                select("id_ciud", "Ciudad", "ciudad")));
        add("peaje", "Peajes", "Logística", "peaje", "id_peaj", DESCRIP, true, List.of(
                text("descrip", "Descripción", 80), decimal("tarifa", "Tarifa", 0),
                optSelect("id_ruta", "Ruta", "ruta"), select("id_ciud", "Ciudad", "ciudad")));
        add("tip_event", "Tipos de evento", "Logística", "tip_event", "id_tip", DESCRIP, false, List.of(
                text("descrip", "Descripción", 60)));
    }

    private void add(String name, String title, String group, String table, String pk, String label,
                     boolean hasEstado, List<CatalogDef.Col> cols) {
        add(name, title, group, table, pk, label, hasEstado, cols, null);
    }

    private void add(String name, String title, String group, String table, String pk, String label,
                     boolean hasEstado, List<CatalogDef.Col> cols, String optionsSql) {
        defs.put(name, new CatalogDef(name, title, group, table, pk, label, cols, hasEstado, false, optionsSql));
    }

    public CatalogDef get(String name) {
        CatalogDef def = defs.get(name);
        if (def == null) {
            throw ApiException.notFound("Catálogo");
        }
        return def;
    }

    public boolean exists(String name) {
        return defs.containsKey(name);
    }

    public Collection<CatalogDef> all() {
        return defs.values();
    }
}
