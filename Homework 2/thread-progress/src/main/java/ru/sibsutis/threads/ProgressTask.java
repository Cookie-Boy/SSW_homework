package ru.sibsutis.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ProgressTask implements Runnable {
    private static final int PROGRESS_LENGTH = 20;
    private static final int STEP_DELAY_MS = 200;

    private final int threadNumber;
    private final CyclicBarrier barrier;

    public ProgressTask(int threadNumber, CyclicBarrier barrier) {
        this.threadNumber = threadNumber;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long threadId = Thread.currentThread().threadId();
        StringBuilder progress = new StringBuilder("[                    ]");

        for (int i = 0; i < PROGRESS_LENGTH; i++) {
            progress.setCharAt(i + 1, '#');

            synchronized (System.out) {
                ConsoleUtils.moveCursorToLine(threadNumber + 1);
                System.out.printf("Thread %d (ID: %d): %s%n", threadNumber + 1, threadId, progress);
            }

            try {
                Thread.sleep(STEP_DELAY_MS);
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        synchronized (System.out) {
            ConsoleUtils.moveCursorToLine(threadNumber + 1);
            System.out.printf("Thread %d (ID: %d) finished in %d ms\033[K%n", threadNumber + 1, threadId, elapsedTime);
        }
    }
}
