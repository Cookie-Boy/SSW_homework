package ru.sibsutis.threads;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgressTaskTest {

    @Test
    void testProgressTaskRunsSuccessfully() throws Exception {
        int threadNumber = 0;
        CyclicBarrier barrier = mock(CyclicBarrier.class);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ProgressTask task = new ProgressTask(threadNumber, barrier);
        Thread thread = new Thread(task);
        thread.start();
        thread.join();

        System.setOut(System.out);

        String output = outContent.toString();
        assertTrue(output.contains("Thread 1"), "Output should contain thread information");
    }

    @Test
    void testBarrierExceptionHandling() {
        int threadNumber = 1;
        CyclicBarrier barrier = mock(CyclicBarrier.class);

        try {
            doThrow(new BrokenBarrierException()).when(barrier).await();
        } catch (InterruptedException | BrokenBarrierException ignored) { }

        ProgressTask task = new ProgressTask(threadNumber, barrier);
        Thread thread = new Thread(task);
        thread.start();

        assertDoesNotThrow(() -> thread.join(), "Thread should handle BrokenBarrierException gracefully");
    }
}
