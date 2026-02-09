import java.util.Random;

public class Philosopher implements Runnable {
    public int philosopherNumber;
    public int meals = 0;

    private Random rand = new Random();

    public Philosopher(int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
        System.out.println("P" + philosopherNumber + " sit in the table.");
    }

    @Override
    public void run() {
        while (meals < Main.totalMeals) {

            try {
                Main.chopsticks[philosopherNumber].acquire();
                System.out.println("P" + philosopherNumber + " pick up left chopstick.");
                Main.chopsticks[(philosopherNumber + 1) % Main.philosopherCount].acquire();
                System.out.println("P" + philosopherNumber + " pick up right chopstick.");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // eat
            System.out.println("P" + philosopherNumber + " start eating M" + meals + ".");
            waiting();
            meals++;

            Main.chopsticks[philosopherNumber].release();
            System.out.println("P" + philosopherNumber + " put down left chopstick.");
            Main.chopsticks[(philosopherNumber + 1) % Main.philosopherCount].release();
            System.out.println("P" + philosopherNumber + " put down right chopstick.");

            // think
            System.out.println("P" + philosopherNumber + " start thinking.");
            waiting();
        }

        Main.leftFinishingMeal--;
        
        // Sleeping for every 50ms to check if other philisophers are done eating
        while (Main.leftFinishingMeal != 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("P" + philosopherNumber + " left the table.");
    }

    public void waiting() {
        // waiting for 3 to 6 cycles at random
        for (int k = 0; k < rand.nextInt(4) + 3; k++) {
            // Nothing.
        } 
    }
}
