package ru.sibsutis.filefilter.service;

import ru.sibsutis.filefilter.configuration.AppConfig;
import ru.sibsutis.filefilter.processor.Processor;
import ru.sibsutis.filefilter.processor.ProcessorRegistry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileService {
    private AppConfig config;
    private ProcessorRegistry registry;

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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (Processor processor : registry.getProcessors()) {
                    processor.processLine(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла " + filePath + ": " + e.getMessage());
        }
    }

    private void saveResults() {
        for (Processor processor : registry.getProcessors()) {
            processor.writeResults(config.getOutputPath(), config.isAppendMode());
        }
    }
}
