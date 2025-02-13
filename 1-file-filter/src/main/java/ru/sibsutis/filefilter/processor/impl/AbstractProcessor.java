package ru.sibsutis.filefilter.processor.impl;

import ru.sibsutis.filefilter.configuration.StatsMode;
import ru.sibsutis.filefilter.processor.Processor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProcessor<T> implements Processor {
    public final List<T> data = new ArrayList<>();

    protected abstract String getFileSuffix();

    @Override
    public void writeResults(String outputPath, String prefix, boolean appendMode) {
        if (data.isEmpty()) return;

        Path filePath = Paths.get(outputPath, prefix + getFileSuffix());

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, appendMode
                ? java.nio.file.StandardOpenOption.APPEND
                : java.nio.file.StandardOpenOption.CREATE)) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error: can not write to the file " + outputPath + ": " + e.getMessage());
        }
    }

    @Override
    public void printStatistics(StatsMode statsMode) {
        if (statsMode != StatsMode.NONE) {
            System.out.println("=== Processor Statistics ===");
            System.out.println("Data size: " + data.size());
        }
    }
}
