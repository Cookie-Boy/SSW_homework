package ru.sibsutis.filefilter.configuration;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AppConfig {
    private List<String> inputFiles;
    private String outputPath;
    private String prefix;
    private boolean appendMode;
    private boolean fullStats;
}
