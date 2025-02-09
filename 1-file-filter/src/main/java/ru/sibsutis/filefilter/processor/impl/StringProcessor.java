package ru.sibsutis.filefilter.processor.impl;

public class StringProcessor extends AbstractProcessor<String> {
    @Override
    public boolean processLine(String line) {
        return data.add(line);
    }

    @Override
    protected String getFileSuffix() {
        return "strings.txt";
    }

    @Override
    public void printStatistics(boolean fullStats) {
        System.out.println("=== Strings Statistics ===");
        System.out.println("Size: " + data.size());
        if (!data.isEmpty() && fullStats) {
            int minLength = data.stream().mapToInt(String::length).min().orElse(0);
            int maxLength = data.stream().mapToInt(String::length).max().orElse(0);
            System.out.println("Min length: " + minLength + "\nMax length: " + maxLength);
            System.out.println();
        }
    }
}
