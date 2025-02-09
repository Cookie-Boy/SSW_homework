package ru.sibsutis.filefilter.processor;

public interface Processor {
    boolean processLine(String line);
    void writeResults(String outputPath, String prefix, boolean appendMode);
    void printStatistics(boolean fullStats);
}
