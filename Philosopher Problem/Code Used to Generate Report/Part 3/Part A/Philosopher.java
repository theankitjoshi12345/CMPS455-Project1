import java.util.Random;

public class Philosopher implements Runnable {
    public static int waitingPhilosophers = 0;
    private static Random rand = new Random();

    private int meals = 0;
    public int philosopherNumber;

    public Philosopher(int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
    }

    @Override
    public void run() {
        waitForOtherPhilosophers();
        System.out.println("P" + philosopherNumber + " sit in the table.");

        while (meals < 4) {

            try {
                Main.chopsticks[philosopherNumber].acquire();
                System.out.println("P" + philosopherNumber + " pick up left chopstick.");
                Main.chopsticks[(philosopherNumber + 1) % Main.totalPhilosophers].acquire();
                System.out.println("P" + philosopherNumber + " pick up right chopstick.");

            } catch (Exception e) {
                System.out.println("Exception has occured: "+ e);
            }

            // eat
            System.out.println("P" + philosopherNumber + " start eating M" + meals + ".");
            waiting();
            meals++;

            Main.chopsticks[philosopherNumber].release();
            System.out.println("P" + philosopherNumber + " put down left chopstick.");
            Main.chopsticks[(philosopherNumber + 1) % Main.totalPhilosophers].release();
            System.out.println("P" + philosopherNumber + " put down right chopstick.");

            // think
            System.out.println("P" + philosopherNumber + " start thinking.");
            waiting();
        }
        
        waitForOtherPhilosophers();
        System.out.println("P" + philosopherNumber + " left the table.");
    }

    public void waiting() {
        // waiting for 3 to 6 cycles at random
        for (int k = 0; k < rand.nextInt(4) + 3; k++) {
            // Nothing.
        }
    }
    
    public void waitForOtherPhilosophers() {
        try {
            Main.mutex.acquire();
        } catch (Exception e) {
            System.out.println("Exception has occured: "+ e);
        }
        waitingPhilosophers++;
        if (waitingPhilosophers == Main.totalPhilosophers) {
            for (int i = 0; i < Main.totalPhilosophers - 1; i++) {
                Main.semHold.release();
            }
            waitingPhilosophers = 0;
            Main.mutex.release();
        } else {
            Main.mutex.release();
            try {
                Main.semHold.acquire();
            } catch (Exception e) {
                System.out.println("Exception has occured: "+ e);
            }
        }
        
    }

}
