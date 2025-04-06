package ru.sibsutis.filefilter.processor.impl;

import ru.sibsutis.filefilter.configuration.StatsMode;

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
    public void printStatistics(StatsMode statsMode) {
        if (statsMode == StatsMode.SHORT || statsMode == StatsMode.FULL) {
            System.out.println("=== Integers Statistics ===");
            System.out.println("Size: " + data.size());
            if (!data.isEmpty() && statsMode == StatsMode.FULL) {
                int min = Collections.min(data);
                int max = Collections.max(data);
                long sum = data.stream().mapToLong(Integer::longValue).sum();
                double avg = sum / (double) data.size();
                System.out.println("Minimum: " + min + "\nMaximum: " + max + "\nSum: " + sum + "\nAverage: " + avg);
                System.out.println();
            }
        }
    }

    @Override
    public Class<?> getSupportedType() {
        return Integer.class;
    }
}
