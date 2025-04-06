package ru.sibsutis.filefilter;

import ru.sibsutis.filefilter.cli.CommandLineParser;
import ru.sibsutis.filefilter.configuration.AppConfig;
import ru.sibsutis.filefilter.processor.ProcessorRegistry;
import ru.sibsutis.filefilter.service.FileService;

public class Main {
    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        AppConfig config = parser.parse(args);
        ProcessorRegistry registry = new ProcessorRegistry();
        FileService fileService = new FileService(config, registry);
        fileService.processFiles();
    }
}