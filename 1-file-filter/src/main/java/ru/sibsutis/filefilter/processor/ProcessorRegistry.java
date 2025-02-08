package ru.sibsutis.filefilter.processor;

import lombok.Getter;
import ru.sibsutis.filefilter.processor.impl.DoubleProcessor;
import ru.sibsutis.filefilter.processor.impl.IntegerProcessor;
import ru.sibsutis.filefilter.processor.impl.StringProcessor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProcessorRegistry {
    private final List<Processor> processors = new ArrayList<>();

    public ProcessorRegistry() {
        processors.add(new IntegerProcessor());
        processors.add(new DoubleProcessor());
        processors.add(new StringProcessor());
    }

    public <T extends Processor> T getProcessor(Class<T> processorClass) {
        return processors.stream()
                .filter(processorClass::isInstance)
                .map(processorClass::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Processor not found: " + processorClass.getSimpleName()));
    }
}
