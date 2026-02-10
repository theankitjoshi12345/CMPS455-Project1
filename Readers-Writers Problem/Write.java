import java.util.Random;

public class Write extends Thread {
    public static Random rand = new Random();
    public int writerNumber;

    public Write(int writerNumber) {
        this.writerNumber = writerNumber;
    }

    @Override
    public void run() {
            try {
                Main.area.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("W" + writerNumber + " is writing.");
            for (int i = 0; i < rand.nextInt(10) + 11; i++) {
                // Writing.
            }

            Main.area.release();
    }
    
}
