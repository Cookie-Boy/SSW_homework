package ru.sibsutis.filefilter.processor;

import java.util.regex.Pattern;

public class TypeDetector {
    private static final Pattern INTEGER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("\\d+\\.\\d+");

    public static Class<?> detectType(String line) {
        if (INTEGER_PATTERN.matcher(line).matches()) {
            return Integer.class;
        } else if (DOUBLE_PATTERN.matcher(line).matches()) {
            return Double.class;
        } else {
            return String.class;
        }
    }
}
