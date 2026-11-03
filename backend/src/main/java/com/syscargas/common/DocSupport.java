package com.syscargas.common;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

/**
 * Operaciones comunes sobre documentos. Los nombres de tabla/columna los pasa
 * siempre el codigo (constantes), nunca el usuario.
 */
@Component
public class DocSupport {

    private final JdbcClient jdbc;

    public DocSupport(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    /** Bloquea la fila (FOR UPDATE) y devuelve su estado. */
    public int lockEstado(String table, String pk, int id, String what) {
        return jdbc.sql("SELECT id_est FROM " + table + " WHERE " + pk + " = :id FOR UPDATE")
                .param("id", id).query(Integer.class).optional()
                .orElseThrow(() -> ApiException.notFound(what));
    }

    public void requireEstado(int actual, String what, int... permitidos) {
        for (int p : permitidos) {
            if (p == actual) {
                return;
            }
        }
        String est = jdbc.sql("SELECT descrip FROM estado WHERE id_est = :e").param("e", actual)
                .query(String.class).single();
        throw ApiException.conflict(what + " está en estado " + est + " y no admite esta operación");
    }

    public void setEstado(String table, String pk, int id, int estado) {
        jdbc.sql("UPDATE " + table + " SET id_est = :e WHERE " + pk + " = :id")
                .param("e", estado).param("id", id).update();
    }

    public Map<String, Object> one(String sql, int id, String what) {
        return jdbc.sql(sql).param("id", id).query(Rows.MAP).optional()
                .orElseThrow(() -> ApiException.notFound(what));
    }

    public List<Map<String, Object>> list(String sql, int id) {
        return jdbc.sql(sql).param("id", id).query(Rows.MAP).list();
    }

    /** Verifica que el registro referenciado exista y este activo (id_est = 1). */
    public void requireActivo(String table, String pk, Integer id, String what) {
        if (id == null) {
            return;
        }
        Integer est = jdbc.sql("SELECT id_est FROM " + table + " WHERE " + pk + " = :id")
                .param("id", id).query(Integer.class).optional()
                .orElseThrow(() -> ApiException.badRequest(what + " no existe"));
        if (est != Estado.ACTIVO) {
            throw ApiException.badRequest(what + " no está activo");
        }
    }
}
