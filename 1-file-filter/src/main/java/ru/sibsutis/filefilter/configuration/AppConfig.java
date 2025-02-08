package ru.sibsutis.filefilter.configuration;

import lombok.Data;

import java.util.List;

@Data
public class AppConfig {
    private List<String> inputFiles;
    private String outputPath;
    private String prefix;
    private boolean appendMode;
    private boolean fullStats;
}
