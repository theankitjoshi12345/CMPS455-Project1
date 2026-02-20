import java.util.Random;

public class Philosopher implements Runnable {
    public static int waitingPhilosophers = 0;
    private static Random rand = new Random();
    private static int meals = 0;

    public int philosopherNumber;

    public Philosopher(int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
    }

    @Override
    public void run() {
        waitForOtherPhilosophers();
        System.out.println("P" + philosopherNumber + " sit in the table.");

        while (true) {

            try {
                Main.mealMutex.acquire();
            } catch (Exception e) {
                System.out.println("Exception has occured: " + e);
                return;
            }

            if (meals >= Main.totalMeals) {
                Main.mealMutex.release();
                break;
            }
            int currentMeal = meals;
            meals++;
            Main.mealMutex.release();

            if (philosopherNumber == Main.totalPhilosophers - 1) {
                try {
                    Main.chopsticks[(philosopherNumber + 1) % Main.totalPhilosophers].acquire();
                }  catch (Exception e) {
                    System.out.println("Exception has occured: " + e);
                    return;
                }
                System.out.println("P" + philosopherNumber + " pick up right chopstick.");

                try {
                    Main.chopsticks[philosopherNumber].acquire();
                }  catch (Exception e) {
                    System.out.println("Exception has occured: " + e);
                    return;
                }
                System.out.println("P" + philosopherNumber + " pick up left chopstick.");
            } else {
                try {
                    Main.chopsticks[philosopherNumber].acquire();
                }  catch (Exception e) {
                    System.out.println("Exception has occured: " + e);
                    return;
                }
                System.out.println("P" + philosopherNumber + " pick up left chopstick.");

                try {
                    Main.chopsticks[(philosopherNumber + 1) % Main.totalPhilosophers].acquire();
                }  catch (Exception e) {
                    System.out.println("Exception has occured: " + e);
                    return;
                }
                System.out.println("P" + philosopherNumber + " pick up right chopstick.");
            }

            System.out.println("P" + philosopherNumber + " start eating M" + currentMeal + ".");
            waiting();

            Main.chopsticks[philosopherNumber].release();
            System.out.println("P" + philosopherNumber + " put down left chopstick.");
            Main.chopsticks[(philosopherNumber + 1) % Main.totalPhilosophers].release();
            System.out.println("P" + philosopherNumber + " put down right chopstick.");

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
            System.out.println("Exception has occured: " + e);
            return;
        }
        waitingPhilosophers++;
        if (waitingPhilosophers == Main.totalPhilosophers) {
            System.out.println("All Philosophers are ready. Barrier is Open.");
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
                System.out.println("Exception has occured: " + e);
                return;
            }
        }
        
    }

}
