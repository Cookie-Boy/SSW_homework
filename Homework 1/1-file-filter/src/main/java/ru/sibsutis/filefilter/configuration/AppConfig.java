package ru.sibsutis.filefilter.configuration;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppConfig {
    private List<String> inputFiles;
    private String outputPath;
    private String prefix;
    private boolean appendMode;
    private StatsMode statsMode;
}
