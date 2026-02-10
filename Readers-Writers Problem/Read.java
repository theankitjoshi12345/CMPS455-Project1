import java.util.Random;

public class Read extends Thread {
    public Random rand = new Random();
    public int readerNumber;

    public Read(int readerNumber) {
        this.readerNumber = readerNumber;
    }

    public void run() {
        try {
            Main.readersGate.acquire();
            Main.mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.readersCount++;

        if (Main.readersCount == 1) {
            try {
                Main.area.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (Main.readersCount < Main.maxReaders) {
            Main.readersGate.release();
        }
        Main.mutex.release();        

        System.out.println("R" + readerNumber + " is reading.");
        for (int i = 0; i < rand.nextInt(10) + 11; i++) {
            // Reading.
        }

        try {
            Main.mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.readersCount--;

        if (Main.readersCount == 0) {
            Main.area.release();
            Main.readersGate.release();
        }
        Main.mutex.release();
    }
}
