package ru.sibsutis.threads;

import java.util.concurrent.*;

public class Main {
    private static final int NUM_THREADS = 8;
    private static final int PROGRESS_LENGTH = 20;
    private static final int STEP_DELAY_MS = 200;

    public static void main(String[] args) {
        clearConsole();

        CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadNumber = i;
            executor.execute(() -> {
                long threadId = Thread.currentThread().threadId();
                StringBuilder progress = new StringBuilder("[                    ]");

                for (int j = 0; j < PROGRESS_LENGTH; j++) {
                    progress.setCharAt(j + 1, '#');

                    synchronized (System.out) {
                        moveCursorToLine(threadNumber + 1);
                        System.out.printf("Thread %d (ID: %d): %s%n", threadNumber + 1, threadId, progress);
                    }

                    try {
                        Thread.sleep(STEP_DELAY_MS);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                synchronized (System.out) {
                    moveCursorToLine(threadNumber + NUM_THREADS + 1);
                    System.out.printf("Thread %d (ID: %d) finished%n", threadNumber + 1, threadId);
                }
            });
        }

        executor.shutdown();
    }

    public static void moveCursorToLine(int line) {
        System.out.printf("\033[%d;0H", line);
    }

    public static void clearConsole() {
        System.out.print("\033[2J"); // Очистка
        System.out.print("\033[H"); // Перемещение курсора в начало
    }

//    public static boolean isAnsiSupported() {
//        String os = System.getProperty("os.name").toLowerCase();
//        // На Windows ANSI не поддерживается стандартно в терминале
//        return !(os.contains("win"));
//    }
}
