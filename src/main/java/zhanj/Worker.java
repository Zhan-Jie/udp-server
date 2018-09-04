package zhanj;

public class Worker {
    public void handle(byte[] data) {
        try {
            // simulate work load
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("current worker: " + this);
        System.out.println("current thread: " + Thread.currentThread());
        // print byte array in hexadecimal format
        int i = 0;
        for (byte b : data) {
            System.out.printf("%02x ", b);
            i = (i + 1) % 8;
            if (i == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
}
