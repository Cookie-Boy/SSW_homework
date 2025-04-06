package ru.sibsutis.threads;

import java.util.concurrent.*;

public class Main {
    private static final int NUM_THREADS = 4;

    public static void main(String[] args) {
        ConsoleUtils.clearConsole();
        CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS);

        try (ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS)) {
            for (int i = 0; i < NUM_THREADS; i++) {
                executor.execute(new ProgressTask(i, barrier));
            }
        }

        System.out.println();
    }
}
