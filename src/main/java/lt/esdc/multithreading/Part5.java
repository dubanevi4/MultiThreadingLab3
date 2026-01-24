package lt.esdc.multithreading;

public class Part5 {
    private static final Object M = new Object();
    private static volatile boolean threadInSync = false;
    private static volatile boolean threadWaiting = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                synchronized (M) {
                    threadInSync = true;
                    try {
                        threadWaiting = true;
                        M.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        System.out.println(t.getState());

        synchronized (M) {
            t.start();
            
            while (t.getState() == Thread.State.NEW) {
            }
            System.out.println(t.getState());
            
            while (t.getState() != Thread.State.BLOCKED) {
            }
            System.out.println(t.getState());
        }
        
        while (!threadWaiting) {
        }
        
        System.out.println(t.getState());
        
        synchronized (M) {
            M.notify();
        }
        
        t.join();
        
        System.out.println(t.getState());
    }
}
