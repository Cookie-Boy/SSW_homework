package ru.sibsutis.filefilter.processor.impl;

import java.util.Collections;

public class IntegerProcessor extends AbstractProcessor<Integer> {
    @Override
    public boolean processLine(String line) {
        try {
            return data.add(Integer.parseInt(line));
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    protected String getFileSuffix() {
        return "integers.txt";
    }

    @Override
    public void printStatistics() {
        System.out.println("=== Integers Statistics ===");
        System.out.println("Size: " + data.size());
        if (!data.isEmpty()) {
            int min = Collections.min(data);
            int max = Collections.max(data);
            long sum = data.stream().mapToLong(Integer::longValue).sum();
            double avg = sum / (double) data.size();
            System.out.println("Minimum: " + min + "\nMaximum: " + max + "\nSum: " + sum + "\nAverage: " + avg);
            System.out.println();
        }
    }
}
