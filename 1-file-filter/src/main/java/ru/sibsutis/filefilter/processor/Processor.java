package ru.sibsutis.filefilter.processor;

public interface Processor {
    void processLine(String line);
    void writeResults(String outputPath, boolean appendMode);
}
