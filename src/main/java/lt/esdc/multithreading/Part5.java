package lt.esdc.multithreading;

public class Part5 {
    private static final Object M = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                synchronized (M) {
                    try {
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
                //just wait then go further
            }
            System.out.println(t.getState());  //print Runnable
            
            while (t.getState() != Thread.State.BLOCKED) {
                //just wait then go further
            }
            System.out.println(t.getState()); //print Blocked
        }

        while (t.getState() != Thread.State.WAITING) {
            //just wait then go further
        }
        System.out.println(t.getState());

        synchronized (M) {
            M.notify();
        }

        t.join();
        
        System.out.println(t.getState());
    }
}
