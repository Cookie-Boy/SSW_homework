package ru.sibsutis.filefilter.service;

import ru.sibsutis.filefilter.configuration.AppConfig;
import ru.sibsutis.filefilter.processor.Processor;
import ru.sibsutis.filefilter.processor.ProcessorRegistry;
import ru.sibsutis.filefilter.processor.impl.DoubleProcessor;
import ru.sibsutis.filefilter.processor.impl.IntegerProcessor;
import ru.sibsutis.filefilter.processor.impl.StringProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Processor processor = determineProcessor(line);
                processor.processLine(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла " + filePath + ": " + e.getMessage());
        }
    }

    private Processor determineProcessor(String line) {
        if (isInteger(line)) {
            return registry.getProcessor(IntegerProcessor.class);
        } else if (isDouble(line)) {
            return registry.getProcessor(DoubleProcessor.class);
        } else {
            return registry.getProcessor(StringProcessor.class);
        }
    }

    private boolean isInteger(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String line) {
        try {
            Double.parseDouble(line);
            return line.contains(".") || line.contains(",");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void saveResults() {
        for (Processor processor : registry.getProcessors()) {
            processor.writeResults(config.getOutputPath(), config.getPrefix(), config.isAppendMode());
        }
    }
}
