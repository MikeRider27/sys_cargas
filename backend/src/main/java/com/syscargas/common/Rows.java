package com.syscargas.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;

/** Convierte filas a mapas con tipos java.time para serializarlas a JSON. */
public final class Rows {

    public static final RowMapper<Map<String, Object>> MAP = new ColumnMapRowMapper() {
        @Override
        protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
            Object v = rs.getObject(index);
            if (v instanceof java.sql.Date d) {
                return d.toLocalDate();
            }
            if (v instanceof java.sql.Timestamp t) {
                return t.toLocalDateTime();
            }
            if (v instanceof java.sql.Time t) {
                return t.toLocalTime();
            }
            return v;
        }
    };

    private Rows() {}
}
