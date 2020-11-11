public class Main implements Runnable {
    boolean running = true;

    public static void main(String[] args) {
        Main obj = new Main();
        Thread thread = new Thread(obj);
        thread.start();
        while (true) {
            System.out.println("This code is outside of the thread");
        }
    }

    public void run() {
        while (running) {
            System.out.println("This code is running in a thread");
        }
    }
}

