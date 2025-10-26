package runner;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagmentTest {
    @Test
    void testParallel() throws Exception {
        // Ejecuta todas las features bajo classpath:features (ajusta si quieres otra carpeta)
        Results results = Runner.path("classpath:features")
                .tags("~@ignore")
                .outputCucumberJson(true) // genera JSON Cucumber
                .parallel(1);             // súbelo si deseas paralelismo

        // Genera reporte HTML Masterthought
        generateReport(results.getReportDir());

        // Falla el build solo si hubo fallos en Karate
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    public static void generateReport(String karateOutputPath) throws Exception {
        // Recolecta todos los .json producidos por Karate
        Collection<File> jsonFiles = Files.walk(new File(karateOutputPath).toPath())
                .filter(p -> p.toString().endsWith(".json"))
                .map(java.nio.file.Path::toFile)
                .toList();

        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(f -> jsonPaths.add(f.getAbsolutePath()));

        // Configuración del reporte
        File reportOutput = new File("target/cucumber-html-reports"); // salida HTML
        Configuration config = new Configuration(reportOutput, "PetStore - Karate");

        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports(); // genera el HTML
    }
}
