package lt.esdc.multithreading;

import java.io.InputStream;

public class Part1 {
    public static void main(String[] args) {
        InputStream originalIn = System.in;

        try {
            System.setIn(new EnterSimulatorInputStream());

            Thread t = new Thread(() ->
                    Spam.main(null));

            t.start();

            t.join();
        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
        } finally {
            System.setIn(originalIn);
        }
    }
}
