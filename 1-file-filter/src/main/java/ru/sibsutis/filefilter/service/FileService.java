package ru.sibsutis.filefilter.service;

import ru.sibsutis.filefilter.configuration.AppConfig;
import ru.sibsutis.filefilter.processor.Processor;
import ru.sibsutis.filefilter.processor.ProcessorRegistry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileService {
    private final AppConfig config;
    private final ProcessorRegistry registry;
    private static final Logger logger = Logger.getLogger(FileService.class.getName());

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
            logger.severe("Файл не найден или недоступен: " + filePath);
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
            logger.severe("Ошибка чтения файла " + filePath + ": " + e.getMessage());
        }
    }

    private void saveResults() {
        for (Processor processor : registry.getProcessors()) {
            processor.writeResults(config.getOutputPath(), config.getPrefix(), config.isAppendMode());
            processor.printStatistics();
        }
    }
}
