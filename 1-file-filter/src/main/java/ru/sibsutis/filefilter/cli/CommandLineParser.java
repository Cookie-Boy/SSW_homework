package ru.sibsutis.filefilter.cli;

import ru.sibsutis.filefilter.configuration.AppConfig;
import ru.sibsutis.filefilter.configuration.StatsMode;

import java.util.ArrayList;
import java.util.List;

public class CommandLineParser {
    public AppConfig parse(String[] args) {
        String outputPath = "src/main/resources/output/";
        String prefix = "";
        boolean appendMode = false;
        StatsMode statsMode = StatsMode.NONE;
        List<String> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o" -> {
                    if (i + 1 < args.length && !args[i + 1].startsWith("-")) outputPath = args[++i];
                }
                case "-p" -> {
                    if (i + 1 < args.length && !args[i + 1].startsWith("-")) prefix = args[++i];
                }
                case "-a" -> appendMode = true;
                case "-f" -> statsMode = StatsMode.FULL;
                case "-s" -> statsMode = StatsMode.SHORT;
                default -> inputFiles.add(args[i]);
            }
        }

        if (inputFiles.isEmpty()) {
            System.err.println("[WARNING] There are no input files.");;
        }

        return new AppConfig(inputFiles, outputPath, prefix, appendMode, statsMode);
    }
}
