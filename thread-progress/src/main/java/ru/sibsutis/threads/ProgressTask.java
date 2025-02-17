package ru.sibsutis.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ProgressTask implements Runnable {
    private static final int PROGRESS_LENGTH = 20;
    private static final int STEP_DELAY_MS = 200;

    private final int threadNumber;
    private final CyclicBarrier barrier;
    private final int totalThreads;

    public ProgressTask(int threadNumber, CyclicBarrier barrier, int totalThreads) {
        this.threadNumber = threadNumber;
        this.barrier = barrier;
        this.totalThreads = totalThreads;
    }

    @Override
    public void run() {
        long threadId = Thread.currentThread().threadId();
        StringBuilder progress = new StringBuilder("[                    ]");

        for (int j = 0; j < PROGRESS_LENGTH; j++) {
            progress.setCharAt(j + 1, '#');

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

        synchronized (System.out) {
            ConsoleUtils.moveCursorToLine(threadNumber + totalThreads + 2);
            System.out.printf("Thread %d (ID: %d) finished%n", threadNumber + 1, threadId);
        }
    }
}
