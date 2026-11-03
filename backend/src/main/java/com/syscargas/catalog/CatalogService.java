package com.syscargas.catalog;

import com.syscargas.catalog.CatalogDef.Col;
import com.syscargas.common.ApiException;
import com.syscargas.common.Rows;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogService {

    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final int MAX_FILAS = 1000;

    private final JdbcClient jdbc;
    private final CatalogRegistry registry;

    public CatalogService(JdbcClient jdbc, CatalogRegistry registry) {
        this.jdbc = jdbc;
        this.registry = registry;
    }

    /** SELECT con las columnas de la tabla mas la descripcion de cada FK ({col}_label). */
    private String selectSql(CatalogDef def) {
        StringBuilder cols = new StringBuilder("t.*");
        StringBuilder joins = new StringBuilder();
        int i = 0;
        for (Col c : def.cols()) {
            if (c.type() == CatalogDef.Type.SELECT) {
                CatalogDef ref = registry.get(c.ref());
                String alias = "r" + i++;
                cols.append(", ").append(ref.label(alias)).append(" AS ").append(c.name()).append("_label");
                joins.append(" LEFT JOIN ").append(ref.table()).append(' ').append(alias)
                        .append(" ON ").append(alias).append('.').append(ref.pk()).append(" = t.").append(c.name());
            }
        }
        if (def.hasEstado()) {
            cols.append(", e.descrip AS estado");
            joins.append(" JOIN estado e ON e.id_est = t.id_est");
        }
        return "SELECT " + cols + " FROM " + def.table() + " t" + joins;
    }

    public List<Map<String, Object>> list(String name, String q, boolean incluirAnulados) {
        CatalogDef def = registry.get(name);
        List<String> where = new ArrayList<>();
        if (def.hasEstado() && !incluirAnulados) {
            where.add("t.id_est <> 0");
        }
        boolean buscar = q != null && !q.isBlank();
        if (buscar) {
            String textos = def.cols().stream()
                    .filter(c -> c.type() == CatalogDef.Type.TEXT || c.type() == CatalogDef.Type.EMAIL)
                    .map(c -> "t." + c.name() + " ILIKE :q")
                    .collect(Collectors.joining(" OR "));
            where.add("(" + (textos.isEmpty() ? "false" : textos) + " OR CAST(t." + def.pk() + " AS TEXT) = :exacto)");
        }
        String sql = selectSql(def)
                + (where.isEmpty() ? "" : " WHERE " + String.join(" AND ", where))
                + " ORDER BY t." + def.pk() + " DESC LIMIT " + MAX_FILAS;
        var spec = jdbc.sql(sql);
        if (buscar) {
            String term = q.trim();
            spec = spec.param("q", "%" + term.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_") + "%")
                    .param("exacto", term);
        }
        return spec.query(Rows.MAP).list();
    }

    public Map<String, Object> get(String name, int id) {
        CatalogDef def = registry.get(name);
        return jdbc.sql(selectSql(def) + " WHERE t." + def.pk() + " = :id")
                .param("id", id).query(Rows.MAP).optional()
                .orElseThrow(() -> ApiException.notFound("Registro"));
    }

    public List<Map<String, Object>> options(String name) {
        CatalogDef def = registry.get(name);
        String sql = def.optionsSql() != null ? def.optionsSql()
                : "SELECT t." + def.pk() + " AS value, " + def.label("t") + " AS label FROM " + def.table() + " t"
                + (def.hasEstado() ? " WHERE t.id_est = 1" : "") + " ORDER BY label";
        return jdbc.sql(sql).query(Rows.MAP).list();
    }

    @Transactional
    public Map<String, Object> create(String name, Map<String, Object> body) {
        CatalogDef def = writable(name);
        Map<String, Object> values = validate(def, body);
        String cols = String.join(", ", values.keySet());
        String params = values.keySet().stream().map(k -> ":" + k).collect(Collectors.joining(", "));
        Integer id = jdbc.sql("INSERT INTO " + def.table() + " (" + cols + ") VALUES (" + params + ") RETURNING " + def.pk())
                .params(values).query(Integer.class).single();
        return get(name, id);
    }

    @Transactional
    public Map<String, Object> update(String name, int id, Map<String, Object> body) {
        CatalogDef def = writable(name);
        Map<String, Object> values = validate(def, body);
        String sets = values.keySet().stream().map(k -> k + " = :" + k).collect(Collectors.joining(", "));
        int n = jdbc.sql("UPDATE " + def.table() + " SET " + sets + " WHERE " + def.pk() + " = :__id")
                .params(values).param("__id", id).update();
        if (n == 0) {
            throw ApiException.notFound("Registro");
        }
        return get(name, id);
    }

    /** Baja logica si la tabla tiene estado; si no, borrado fisico (falla con 409 si esta en uso). */
    @Transactional
    public void delete(String name, int id) {
        CatalogDef def = writable(name);
        String sql = def.hasEstado()
                ? "UPDATE " + def.table() + " SET id_est = 0 WHERE " + def.pk() + " = :id"
                : "DELETE FROM " + def.table() + " WHERE " + def.pk() + " = :id";
        if (jdbc.sql(sql).param("id", id).update() == 0) {
            throw ApiException.notFound("Registro");
        }
    }

    @Transactional
    public void activar(String name, int id) {
        CatalogDef def = writable(name);
        if (!def.hasEstado()) {
            throw ApiException.badRequest("Este catálogo no maneja estado");
        }
        if (jdbc.sql("UPDATE " + def.table() + " SET id_est = 1 WHERE " + def.pk() + " = :id").param("id", id).update() == 0) {
            throw ApiException.notFound("Registro");
        }
    }

    private CatalogDef writable(String name) {
        CatalogDef def = registry.get(name);
        if (def.readOnly()) {
            throw ApiException.badRequest("El catálogo " + def.title() + " es de solo lectura");
        }
        return def;
    }

    /** Valida y convierte el cuerpo segun las columnas definidas; ignora cualquier otra clave. */
    Map<String, Object> validate(CatalogDef def, Map<String, Object> body) {
        Map<String, Object> out = new java.util.LinkedHashMap<>();
        for (Col c : def.cols()) {
            Object raw = body.get(c.name());
            if (raw instanceof String s && s.isBlank()) {
                raw = null;
            }
            if (raw == null) {
                if (c.required()) {
                    throw ApiException.badRequest("El campo '" + c.label() + "' es obligatorio");
                }
                out.put(c.name(), null);
                continue;
            }
            out.put(c.name(), convert(c, raw));
        }
        return out;
    }

    private Object convert(Col c, Object raw) {
        try {
            return switch (c.type()) {
                case TEXT -> {
                    String s = raw.toString().trim();
                    if (s.length() > c.max()) {
                        throw ApiException.badRequest("'" + c.label() + "' admite hasta " + c.max() + " caracteres");
                    }
                    yield c.upper() ? s.toUpperCase(Locale.ROOT) : s;
                }
                case EMAIL -> {
                    String s = raw.toString().trim().toLowerCase(Locale.ROOT);
                    if (s.length() > c.max() || !EMAIL.matcher(s).matches()) {
                        throw ApiException.badRequest("'" + c.label() + "' no es un correo válido");
                    }
                    yield s;
                }
                case INT, SELECT -> {
                    int v = new BigDecimal(raw.toString().trim()).intValueExact();
                    checkMin(c, v);
                    yield v;
                }
                case DECIMAL -> {
                    BigDecimal v = new BigDecimal(raw.toString().trim());
                    checkMin(c, v.doubleValue());
                    yield v;
                }
                case DATE -> {
                    LocalDate d = LocalDate.parse(raw.toString().trim());
                    if (d.isAfter(LocalDate.now())) {
                        throw ApiException.badRequest("'" + c.label() + "' no puede ser una fecha futura");
                    }
                    yield d;
                }
            };
        } catch (NumberFormatException | ArithmeticException e) {
            throw ApiException.badRequest("'" + c.label() + "' debe ser un número válido");
        } catch (DateTimeParseException e) {
            throw ApiException.badRequest("'" + c.label() + "' debe ser una fecha válida (AAAA-MM-DD)");
        }
    }

    private static void checkMin(Col c, double v) {
        if (c.min() != null && v < c.min()) {
            throw ApiException.badRequest("'" + c.label() + "' debe ser mayor o igual a " + c.min());
        }
    }
}
