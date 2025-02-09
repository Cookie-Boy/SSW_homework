package ru.sibsutis.filefilter.cli;

import ru.sibsutis.filefilter.configuration.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class CommandLineParser {
    public AppConfig parse(String[] args) {
        String outputPath = "src/main/resources/output/";
        String prefix = "";
        boolean appendMode = false;
        boolean fullStats = false;
        List<String> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) outputPath = args[++i];
                    break;
                case "-p":
                    if (i + 1 < args.length) prefix = args[++i];
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
            }
        }

        if (inputFiles.isEmpty()) {
            System.err.println("[WARNING] Не указаны входные файлы.");;
        }

        return new AppConfig(inputFiles, outputPath, prefix, appendMode, fullStats);
    }
}
