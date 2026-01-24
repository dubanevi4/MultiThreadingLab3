package lt.esdc.multithreading;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class Part4 {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final int K;
    private final int N;
    private final String filename;

    public Part4(int K, int N, String filename) {
        this.K = K;
        this.N = N;
        this.filename = filename;
    }

    public void execute() throws IOException, InterruptedException {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }

        RandomAccessFile raf = new RandomAccessFile(filename, "rw");

        Thread[] threads = new Thread[K];
        
        for (int i = 0; i < K; i++) {
            final int digit = i;
            final int lineNumber = i;
            
            threads[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        long lineStart = lineNumber * (N + LINE_SEPARATOR.length());
                        
                        synchronized (raf) {
                            raf.seek(lineStart);
                            
                            for (int j = 0; j < N; j++) {
                                raf.write((digit + "").getBytes(StandardCharsets.UTF_8));
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                            
                            raf.write(LINE_SEPARATOR.getBytes(StandardCharsets.UTF_8));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        raf.close();

        printFileContents();
    }

    private void printFileContents() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            String line;
            while ((line = raf.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Part4 part4 = new Part4(10, 20, "part4.txt");
            part4.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
