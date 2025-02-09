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

    public FileService(AppConfig config) {
        this.config = config;
        this.registry = new ProcessorRegistry();
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
            System.err.println("Файл не найден или недоступен: " + filePath);
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
            System.err.println("Ошибка чтения файла " + filePath + ": " + e.getMessage());
        }
    }

    private void saveResults() {
        for (Processor processor : registry.getProcessors()) {
            processor.writeResults(config.getOutputPath(), config.getPrefix(), config.isAppendMode());
            processor.printStatistics(config.isFullStats());
        }
    }
}
