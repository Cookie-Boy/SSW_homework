package ru.sibsutis.filefilter;

import ru.sibsutis.filefilter.cli.CommandLineParser;
import ru.sibsutis.filefilter.configuration.AppConfig;
import ru.sibsutis.filefilter.service.FileService;

public class Main {
    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        AppConfig config = parser.parse(args);
        FileService fileService = new FileService(config);
        fileService.processFiles();
    }
}