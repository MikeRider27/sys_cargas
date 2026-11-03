package com.syscargas.reports;

import com.syscargas.common.ApiException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    public enum Tipo { DOCUMENTO, RANGO }

    public record ReportDef(String name, String title, Tipo tipo, String group) {}

    /** Lista blanca: solo estos nombres pueden cargarse desde classpath:reports/. */
    private static final Map<String, ReportDef> REPORTES = new LinkedHashMap<>();
    /** Formato numerico paraguayo: 25.000,00 */
    private static final Locale LOCALE = Locale.of("es", "PY");

    static {
        add("regped", "Pedido del cliente", Tipo.DOCUMENTO, "Pedidos");
        add("coti", "Cotización de flete", Tipo.DOCUMENTO, "Pedidos");
        add("ped_flete", "Pedido de flete", Tipo.DOCUMENTO, "Pedidos");
        add("emicrt", "CRT - Conocimiento de transporte", Tipo.DOCUMENTO, "Pedidos");
        add("recla", "Reclamo", Tipo.DOCUMENTO, "Pedidos");
        add("asignacion", "Orden de carga (asignación)", Tipo.DOCUMENTO, "Logística");
        add("rendicion", "Rendición de viático", Tipo.DOCUMENTO, "Rendiciones");
        add("listado_pedidos", "Listado de pedidos", Tipo.RANGO, "Listados");
        add("listado_reclamos", "Listado de reclamos", Tipo.RANGO, "Listados");
        add("recorridos", "Recorridos y consumo por tracto", Tipo.RANGO, "Listados");
    }

    private static void add(String name, String title, Tipo tipo, String group) {
        REPORTES.put(name, new ReportDef(name, title, tipo, group));
    }

    private final DataSource dataSource;
    private final Map<String, JasperReport> cache = new ConcurrentHashMap<>();

    public ReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<ReportDef> disponibles() {
        return List.copyOf(REPORTES.values());
    }

    public ReportDef def(String name) {
        ReportDef def = REPORTES.get(name);
        if (def == null) {
            throw ApiException.notFound("Reporte");
        }
        return def;
    }

    public byte[] pdf(String name, Integer id, LocalDate desde, LocalDate hasta) {
        ReportDef def = def(name);
        Map<String, Object> params = new HashMap<>();
        params.put(JRParameter.REPORT_LOCALE, LOCALE);
        if (def.tipo() == Tipo.DOCUMENTO) {
            if (id == null) {
                throw ApiException.badRequest("Debe indicar el número de documento");
            }
            params.put("ID", id);
        } else {
            LocalDate d = desde != null ? desde : LocalDate.now().withDayOfMonth(1);
            LocalDate h = hasta != null ? hasta : LocalDate.now();
            if (h.isBefore(d)) {
                throw ApiException.badRequest("La fecha 'hasta' no puede ser anterior a 'desde'");
            }
            params.put("DESDE", java.sql.Date.valueOf(d));
            params.put("HASTA", java.sql.Date.valueOf(h));
        }
        Connection con = DataSourceUtils.getConnection(dataSource);
        try {
            con.setReadOnly(true);
            JasperPrint print = JasperFillManager.fillReport(load(name), params, con);
            if (def.tipo() == Tipo.DOCUMENTO && print.getPages().isEmpty()) {
                throw ApiException.notFound("Documento");
            }
            return JasperExportManager.exportReportToPdf(print);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Error generando el reporte " + name, e);
        } finally {
            try {
                con.setReadOnly(false);
            } catch (Exception ignored) {
                // la conexion vuelve al pool igualmente
            }
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    private JasperReport load(String name) {
        return cache.computeIfAbsent(name, n -> {
            try (InputStream in = new ClassPathResource("reports/" + n + ".jasper").getInputStream()) {
                return (JasperReport) JRLoader.loadObject(in);
            } catch (Exception e) {
                throw new IllegalStateException("No se pudo cargar el reporte " + n, e);
            }
        });
    }
}
