package ru.sibsutis.filefilter.service;

import ru.sibsutis.filefilter.configuration.AppConfig;
import ru.sibsutis.filefilter.processor.Processor;
import ru.sibsutis.filefilter.processor.ProcessorRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    private final AppConfig config;
    private final ProcessorRegistry registry;

    public FileService(AppConfig config, ProcessorRegistry processorRegistry) {
        this.config = config;
        this.registry = processorRegistry;
    }

    public void processFiles() {
        for (String filePath : config.getInputFiles()) {
            processFile(filePath);
        }
        saveResults();
    }

    private void processFile(String filePath) {
        Path path = Paths.get(filePath);

        if (!Files.exists(path) || !Files.isReadable(path)) {
            System.err.println("Error: file not found or unavailable: " + filePath);
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (Processor processor : registry.getProcessors()) {
                    if (processor.processLine(line))
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: can not read the file " + filePath + ": " + e.getMessage());
        }
    }

    private void saveResults() {
        for (Processor processor : registry.getProcessors()) {
            processor.writeResults(config.getOutputPath(), config.getPrefix(), config.isAppendMode());
            processor.printStatistics(config.isFullStats());
        }
    }
}
