package lt.esdc.multithreading;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Part3FileGenerator {
    public static void generateFile(String filename, int M, int N) throws IOException {
        Random random = new Random();
        
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    int value = random.nextInt(1000);
                    writer.write(String.valueOf(value));
                    if (j < N - 1) {
                        writer.write(" ");
                    }
                }
                if (i < M - 1) {
                    writer.write("\n");
                }
            }
        }
    }
}
