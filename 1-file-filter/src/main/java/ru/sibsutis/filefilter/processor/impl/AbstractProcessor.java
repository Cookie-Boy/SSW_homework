package ru.sibsutis.filefilter.processor.impl;

import ru.sibsutis.filefilter.processor.Processor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProcessor<T> implements Processor {
    protected final List<T> data = new ArrayList<>();

    protected abstract String getFileSuffix();

    @Override
    public void writeResults(String outputPath, String prefix, boolean appendMode) {
        if (data.isEmpty()) return;

        String fileName = outputPath + prefix + getFileSuffix();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, appendMode))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл " + outputPath + ": " + e.getMessage());
        }
    }
}
