package com.syscargas.catalog;

import java.util.List;

/**
 * Definicion de una tabla referencial. Todos los identificadores SQL salen de
 * estas definiciones (lista blanca); los valores siempre van como parametros.
 *
 * @param labelSql   expresion SQL para mostrar el registro; {@code %1$s} es el alias de la tabla
 * @param optionsSql consulta opcional (value, label) para combos, cuando el label necesita joins
 */
public record CatalogDef(String name, String title, String group, String table, String pk,
                         String labelSql, List<Col> cols, boolean hasEstado, boolean readOnly,
                         String optionsSql) {

    public enum Type { TEXT, EMAIL, INT, DECIMAL, DATE, SELECT }

    /**
     * @param ref    catalogo del que salen las opciones (solo para SELECT)
     * @param min    valor minimo para INT/DECIMAL (null = sin minimo)
     * @param upper  si el texto se guarda en mayusculas (comportamiento del legacy)
     * @param inList si se muestra como columna en el listado
     */
    public record Col(String name, String label, Type type, boolean required, int max, String ref,
                      Double min, boolean upper, boolean inList) {

        public static Col text(String name, String label, int max) {
            return new Col(name, label, Type.TEXT, true, max, null, null, true, true);
        }

        public static Col optText(String name, String label, int max) {
            return new Col(name, label, Type.TEXT, false, max, null, null, true, true);
        }

        public static Col email(String name, String label) {
            return new Col(name, label, Type.EMAIL, false, 120, null, null, false, true);
        }

        public static Col integer(String name, String label, double min) {
            return new Col(name, label, Type.INT, true, 0, null, min, false, true);
        }

        public static Col decimal(String name, String label, double min) {
            return new Col(name, label, Type.DECIMAL, true, 0, null, min, false, true);
        }

        public static Col date(String name, String label, boolean required) {
            return new Col(name, label, Type.DATE, required, 0, null, null, false, true);
        }

        public static Col select(String name, String label, String ref) {
            return new Col(name, label, Type.SELECT, true, 0, ref, null, false, true);
        }

        public static Col optSelect(String name, String label, String ref) {
            return new Col(name, label, Type.SELECT, false, 0, ref, null, false, true);
        }

        public Col hidden() {
            return new Col(name, label, type, required, max, ref, min, upper, false);
        }
    }

    public String label(String alias) {
        return labelSql.formatted(alias);
    }
}
