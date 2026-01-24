package lt.esdc.multithreading;

public class Part2 {
    private int c1;
    private int c2;
    private final int n;
    private final int k;
    private final int t;

    public Part2(int n, int k, int t) {
        this.n = n;
        this.k = k;
        this.t = t;
        this.c1 = 0;
        this.c2 = 0;
    }

    public void reset() {
        c1 = 0;
        c2 = 0;
    }

    public void test() {
        Thread[] threads = new Thread[n];

        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < k; j++) {
                    System.out.println(c1 + " " + c2);
                    c1++;
                    try {
                        Thread.sleep(t);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    c2++;
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void testSync() {
        Thread[] threads = new Thread[n];

        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < k; j++) {
                    synchronized (Part2.this) {
                        System.out.println(c1 + " " + c2);
                        c1++;
                        try {
                            Thread.sleep(t);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        c2++;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Part2 p = new Part2(3, 5, 100);
        p.test();
        p.reset();
        p.testSync();
    }
}
