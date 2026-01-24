package lt.esdc.multithreading;

import java.io.InputStream;

public class EnterSimulatorInputStream extends InputStream {
    private boolean firstRead = true;
    private int position = 0;
    private final byte[] enterBytes = "\n".getBytes();

    @Override
    public int read() {
        if (firstRead) {
            firstRead = false;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (position < enterBytes.length) {
            return enterBytes[position++];
        }

        return -1;
    }
}
