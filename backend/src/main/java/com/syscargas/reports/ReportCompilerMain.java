package com.syscargas.reports;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 * Compila los .jrxml a .jasper durante el build de Maven (fase process-classes).
 * Un error en un reporte rompe el build en lugar de fallar en produccion.
 */
public final class ReportCompilerMain {

    private ReportCompilerMain() {}

    public static void main(String[] args) throws IOException, JRException {
        Path src = Path.of(args[0]);
        Path out = Path.of(args[1]);
        Files.createDirectories(out);
        List<Path> fuentes;
        try (Stream<Path> files = Files.list(src)) {
            fuentes = files.filter(p -> p.toString().endsWith(".jrxml")).sorted().toList();
        }
        for (Path jrxml : fuentes) {
            String name = jrxml.getFileName().toString().replace(".jrxml", ".jasper");
            JasperCompileManager.compileReportToFile(jrxml.toString(), out.resolve(name).toString());
            System.out.println("[reportes] compilado " + name);
        }
    }
}
