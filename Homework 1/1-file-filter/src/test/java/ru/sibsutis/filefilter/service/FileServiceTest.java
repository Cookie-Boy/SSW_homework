package ru.sibsutis.filefilter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.sibsutis.filefilter.configuration.AppConfig;
import ru.sibsutis.filefilter.configuration.StatsMode;
import ru.sibsutis.filefilter.processor.impl.DoubleProcessor;
import ru.sibsutis.filefilter.processor.impl.IntegerProcessor;
import ru.sibsutis.filefilter.processor.ProcessorRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {
    private FileService fileService;
    private ProcessorRegistry registry;

    private List<String> inputFiles;
    private String outputPath;
    private String prefix;
    private boolean appendMode;
    private StatsMode statsMode;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        inputFiles = List.of();
        outputPath = tempDir.toString();
        prefix = "result_";
        appendMode = false;
        statsMode = StatsMode.NONE;

        AppConfig config = new AppConfig() {
            @Override
            public List<String> getInputFiles() {
                return inputFiles;
            }

            @Override
            public String getOutputPath() {
                return outputPath;
            }

            @Override
            public String getPrefix() {
                return prefix;
            }

            @Override
            public boolean isAppendMode() {
                return appendMode;
            }

            @Override
            public StatsMode getStatsMode() {
                return statsMode;
            }
        };

        registry = new ProcessorRegistry();
        fileService = new FileService(config, registry);
    }

    @Test
    void testProcessDoubleFile() throws IOException {
        Path testFile = tempDir.resolve("doubles.txt");
        Files.write(testFile, List.of("1.1", "2.2", "3.3"));

        inputFiles = List.of(testFile.toString());

        fileService.processFiles();

        DoubleProcessor processor = (DoubleProcessor) registry.getProcessors().stream()
                .filter(p -> p instanceof DoubleProcessor)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("DoubleProcessor not found"));

        assertEquals(3, processor.data.size());
        assertTrue(processor.data.contains(1.1));
        assertTrue(processor.data.contains(2.2));
        assertTrue(processor.data.contains(3.3));
    }

    @Test
    void testProcessIntegerFile() throws IOException {
        Path testFile = tempDir.resolve("integers.txt");
        Files.write(testFile, List.of("10", "20", "30"));

        inputFiles = List.of(testFile.toString());

        fileService.processFiles();

        IntegerProcessor processor = (IntegerProcessor) registry.getProcessors().stream()
                .filter(p -> p instanceof IntegerProcessor)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("IntegerProcessor not found"));

        assertEquals(3, processor.data.size());
        assertTrue(processor.data.contains(10));
        assertTrue(processor.data.contains(20));
        assertTrue(processor.data.contains(30));
    }

    @Test
    void testSaveDoubleResults() throws IOException {
        DoubleProcessor processor = (DoubleProcessor) registry.getProcessors().stream()
                .filter(p -> p instanceof DoubleProcessor)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("DoubleProcessor not found"));

        processor.data.add(1.1);
        processor.data.add(2.2);

        fileService.processFiles();

        Path resultFile = tempDir.resolve("result_doubles.txt");
        assertTrue(Files.exists(resultFile));

        List<String> lines = Files.readAllLines(resultFile);
        assertEquals(2, lines.size());
        assertEquals("1.1", lines.get(0));
        assertEquals("2.2", lines.get(1));
    }

    @Test
    void testSaveIntegerResults() throws IOException {
        IntegerProcessor processor = (IntegerProcessor) registry.getProcessors().stream()
                .filter(p -> p instanceof IntegerProcessor)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("IntegerProcessor not found"));

        processor.data.add(10);
        processor.data.add(20);

        fileService.processFiles();

        Path resultFile = tempDir.resolve("result_integers.txt");
        assertTrue(Files.exists(resultFile));

        List<String> lines = Files.readAllLines(resultFile);
        assertEquals(2, lines.size());
        assertEquals("10", lines.get(0));
        assertEquals("20", lines.get(1));
    }

    @Test
    void testProcessNonExistingFile() {
        String nonExistingFile = tempDir.resolve("not_found.txt").toString();
        inputFiles = List.of(nonExistingFile);

        assertDoesNotThrow(() -> fileService.processFiles());
    }

    @Test
    void testPrintStatistics() {
        DoubleProcessor processor = (DoubleProcessor) registry.getProcessors().stream()
                .filter(p -> p instanceof DoubleProcessor)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("DoubleProcessor not found"));

        processor.data.add(1.1);
        processor.data.add(2.2);

        processor.printStatistics(StatsMode.FULL);
    }
}
