package lt.esdc.multithreading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part3 {
    private int[][] matrix;
    private int M;
    private int N;

    public Part3(String filename) throws IOException {
        loadMatrix(filename);
    }

    private void loadMatrix(String filename) throws IOException {
        List<int[]> rows = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\s+");
                int[] row = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    row[i] = Integer.parseInt(parts[i]);
                }
                rows.add(row);
            }
        }
        
        M = rows.size();
        if (M == 0) {
            throw new IOException("Matrix is empty");
        }
        N = rows.get(0).length;
        
        matrix = new int[M][N];
        for (int i = 0; i < M; i++) {
            matrix[i] = rows.get(i);
        }
    }

    public int findMaxParallel() throws InterruptedException {
        int[] rowMaxs = new int[M];
        Thread[] threads = new Thread[M];
        
        for (int i = 0; i < M; i++) {
            final int rowIndex = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    int max = matrix[rowIndex][0];
                    for (int j = 1; j < N; j++) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        if (matrix[rowIndex][j] > max) {
                            max = matrix[rowIndex][j];
                        }
                    }
                    rowMaxs[rowIndex] = max;
                }
            };
            threads[i].start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        int globalMax = rowMaxs[0];
        for (int i = 1; i < M; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (rowMaxs[i] > globalMax) {
                globalMax = rowMaxs[i];
            }
        }
        
        return globalMax;
    }

    public int findMaxSequential() throws InterruptedException {
        int max = matrix[0][0];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (i == 0 && j == 0) continue;
                
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        
        return max;
    }

    public static void main(String[] args) {
        try {
            Part3FileGenerator.generateFile("part3.txt", 4, 100);
            
            Part3 part3 = new Part3("part3.txt");
            
            long startTime1 = System.currentTimeMillis();
            int max1 = part3.findMaxParallel();
            long endTime1 = System.currentTimeMillis();
            long time1 = endTime1 - startTime1;
            
            long startTime2 = System.currentTimeMillis();
            int max2 = part3.findMaxSequential();
            long endTime2 = System.currentTimeMillis();
            long time2 = endTime2 - startTime2;
            
            System.out.println(max1);
            System.out.println(time1);
            System.out.println(max2);
            System.out.println(time2);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
