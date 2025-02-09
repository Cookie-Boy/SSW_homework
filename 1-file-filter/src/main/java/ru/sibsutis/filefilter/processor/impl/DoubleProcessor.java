package ru.sibsutis.filefilter.processor.impl;

import java.util.Collections;

public class DoubleProcessor extends AbstractProcessor<Double> {
    @Override
    public boolean processLine(String line) {
        try {
            return data.add(Double.parseDouble(line));
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    protected String getFileSuffix() {
        return "doubles.txt";
    }

    @Override
    public void printStatistics(boolean fullStats) {
        System.out.println("=== Doubles Statistics ===");
        System.out.println("Size: " + data.size());
        if (!data.isEmpty() && fullStats) {
            double min = Collections.min(data);
            double max = Collections.max(data);
            double sum = data.stream().mapToDouble(Double::doubleValue).sum();
            double avg = sum / data.size();
            System.out.println("Minimum: " + min + "\nMaximum: " + max + "\nSum: " + sum + "\nAverage: " + avg);
            System.out.println();
        }
    }
}
