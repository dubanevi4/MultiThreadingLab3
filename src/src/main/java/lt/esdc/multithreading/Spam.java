package lt.esdc.multithreading;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Spam {
    private Thread[] threads;
    private volatile boolean running = true;

    public Spam(String[] messages, int[] times) {
        threads = new Thread[messages.length];
        for (int i = 0; i < messages.length; i++) {
            threads[i] = new Worker(messages[i], times[i], this);
        }
    }

    public void start() {
        for (Thread t : threads) {
            t.start();
        }
    }

    public void stop() {
        running = false;
        for (Thread t : threads) {
            t.interrupt();
        }
        // Ждем завершения всех дочерних потоков
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    private static class Worker extends Thread {
        private final String message;
        private final int delay;
        private final Spam spam;

        public Worker(String message, int delay, Spam spam) {
            this.message = message;
            this.delay = delay;
            this.spam = spam;
        }

        @Override
        public void run() {
            try {
                while (spam.isRunning()) {
                    System.out.println(message);
                    Thread.sleep(delay);
                }
            } catch (InterruptedException e) {
                // Поток прерван, завершаем выполнение
            }
        }
    }

    public static void main(String[] args) {
        String[] messages = new String[] { "@@@", "bbbbbbb" };
        int[] times = new int[] { 333, 222 };

        Spam spam = new Spam(messages, times);
        spam.start();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in))) {
            br.readLine(); // ожидание нажатия Enter
        } catch (Exception e) {
            // Игнорируем исключения
        }

        spam.stop();
    }
}
