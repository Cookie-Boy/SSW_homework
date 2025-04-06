package ru.sibsutis.filefilter.cli;

import org.junit.jupiter.api.Test;
import ru.sibsutis.filefilter.configuration.AppConfig;
import ru.sibsutis.filefilter.configuration.StatsMode;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineParserTest {

    @Test
    void testParseWithAllOptions() {
        CommandLineParser parser = new CommandLineParser();
        String[] args = {"-o", "src/main/resources/output/path/", "-p", "prefix_", "-a", "-s", "input1.txt", "input2.txt"};
        AppConfig config = parser.parse(args);

        assertEquals("src/main/resources/output/path/", config.getOutputPath());
        assertEquals("prefix_", config.getPrefix());
        assertEquals(StatsMode.SHORT, config.getStatsMode());
        assertTrue(config.isAppendMode());
        assertEquals(Arrays.asList("input1.txt", "input2.txt"), config.getInputFiles());
    }

    @Test
    void testParseWithNoOptions() {
        CommandLineParser parser = new CommandLineParser();
        String[] args = {"input1.txt", "input2.txt"};
        AppConfig config = parser.parse(args);

        assertEquals("src/main/resources/output/", config.getOutputPath());
        assertEquals("", config.getPrefix());
        assertEquals(StatsMode.NONE, config.getStatsMode());
        assertFalse(config.isAppendMode());
        assertEquals(Arrays.asList("input1.txt", "input2.txt"), config.getInputFiles());
    }

    @Test
    void testParseWithAppendMode() {
        CommandLineParser parser = new CommandLineParser();
        String[] args = {"-a", "input1.txt"};
        AppConfig config = parser.parse(args);

        assertTrue(config.isAppendMode());
        assertEquals(List.of("input1.txt"), config.getInputFiles());
    }

    @Test
    void testParseWithFullStats() {
        CommandLineParser parser = new CommandLineParser();
        String[] args = {"-f", "input1.txt"};
        AppConfig config = parser.parse(args);

        assertEquals(StatsMode.FULL, config.getStatsMode());
        assertEquals(List.of("input1.txt"), config.getInputFiles());
    }

    @Test
    void testParseWithPrefix() {
        CommandLineParser parser = new CommandLineParser();
        String[] args = {"-p", "pre_", "input1.txt"};
        AppConfig config = parser.parse(args);

        assertEquals("pre_", config.getPrefix());
        assertEquals(List.of("input1.txt"), config.getInputFiles());
    }

    @Test
    void testParseWithOutputPath() {
        CommandLineParser parser = new CommandLineParser();
        String[] args = {"-o", "src/main/resources/output/", "input1.txt"};
        AppConfig config = parser.parse(args);

        assertEquals("src/main/resources/output/", config.getOutputPath());
        assertEquals(List.of("input1.txt"), config.getInputFiles());
    }

    @Test
    void testParseWithNoInputFiles() {
        CommandLineParser parser = new CommandLineParser();
        String[] args = {"-o", "src/main/resources/output/", "-p", "pre_", "-a", "-f"};
        AppConfig config = parser.parse(args);

        assertEquals("src/main/resources/output/", config.getOutputPath());
        assertEquals("pre_", config.getPrefix());
        assertEquals(StatsMode.FULL, config.getStatsMode());
        assertTrue(config.isAppendMode());
        assertTrue(config.getInputFiles().isEmpty());
    }

    @Test
    void testParseWithMissingOutputPathValue() {
        CommandLineParser parser = new CommandLineParser();
        String[] args = {"-o", "-p", "pre_", "input1.txt"};
        AppConfig config = parser.parse(args);

        assertEquals("src/main/resources/output/", config.getOutputPath());
        assertEquals("pre_", config.getPrefix());
        assertEquals(List.of("input1.txt"), config.getInputFiles());
    }

    @Test
    void testParseWithMissingPrefixValue() {
        CommandLineParser parser = new CommandLineParser();
        String[] args = {"-p", "-o", "src/main/resources/output/", "input1.txt"};
        AppConfig config = parser.parse(args);

        assertEquals("src/main/resources/output/", config.getOutputPath());
        assertEquals("", config.getPrefix());
        assertEquals(List.of("input1.txt"), config.getInputFiles());
    }
}