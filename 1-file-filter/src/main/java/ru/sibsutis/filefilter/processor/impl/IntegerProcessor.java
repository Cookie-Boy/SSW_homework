package ru.sibsutis.filefilter.processor.impl;

public class IntegerProcessor extends AbstractProcessor<Integer> {
    @Override
    public void processLine(String line) {
        data.add(Integer.parseInt(line));
    }

    @Override
    protected String getFileSuffix() {
        return "integers.txt";
    }
}
