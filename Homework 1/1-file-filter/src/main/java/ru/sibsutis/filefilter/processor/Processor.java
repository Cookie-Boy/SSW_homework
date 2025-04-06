package ru.sibsutis.filefilter.processor;

import ru.sibsutis.filefilter.configuration.StatsMode;

public interface Processor {
    boolean processLine(String line);
    void writeResults(String outputPath, String prefix, boolean appendMode);
    void printStatistics(StatsMode statsMode);
    Class<?> getSupportedType();
}
