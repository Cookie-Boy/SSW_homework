package ru.sibsutis.filefilter.processor.impl;

public class DoubleProcessor extends AbstractProcessor<Double> {
    @Override
    public void processLine(String line) {
        data.add(Double.parseDouble(line));
    }

    @Override
    protected String getFileSuffix() {
        return "doubles.txt";
    }
}
