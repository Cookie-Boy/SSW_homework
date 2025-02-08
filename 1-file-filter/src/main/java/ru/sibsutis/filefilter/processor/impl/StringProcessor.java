package ru.sibsutis.filefilter.processor.impl;

public class StringProcessor extends AbstractProcessor<String> {
    @Override
    public void processLine(String line) {
        data.add(line);
    }

    @Override
    protected String getFileSuffix() {
        return "strings.txt";
    }
}
