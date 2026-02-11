import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    // Declaring global variables which will be shared across threads.
    public static int totalPhilosophers;
    public static int totalMeals;

    public static Semaphore[] chopsticks;
    public static Semaphore mealMutex = new Semaphore(1);
    public static Semaphore semHold = new Semaphore(0);
    public static Semaphore mutex = new Semaphore(1);

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // Number of Philosophers
        System.out.print("Enter the number of philosophers (Non-negative integers Only): ");
        if (!in.hasNextInt()) {
            System.out.println("Input Error: Number of philosophers must be a non-negative integer.");
            in.close();
            return;
        }
        totalPhilosophers = in.nextInt();

        if (totalPhilosophers == 1) {
            System.out.println("1 Philosopher means 1 Chopstick. He cannot eat any meal.");
            in.close();
            return;
        }

        if (totalPhilosophers < 0) {
            System.out.println("Input Error: Number of philosophers cannot be negative.");
            in.close();
            return;
        }

        // Total Meals
        System.out.print("Enter total number of meals (Non-negative integers only): ");
        if (!in.hasNextInt()) {
            System.out.println("Input Error: Total meals must be a non-negative integer.");
            in.close();
            return;
        }

        totalMeals = in.nextInt();
        if (totalMeals < 0) {
            System.out.println("Input Error: Total meals cannot be negative.");
            in.close();
            return;
        }

        in.close();

        // # of chopsticks = # of philosophers
        chopsticks = new Semaphore[totalPhilosophers];
        Philosopher[] philosophers = new Philosopher[totalPhilosophers];
        Thread[] threads = new Thread[totalPhilosophers];
    
        // All chopsticks are initialized to semaphore value 1
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Semaphore(1);
        }

        for (int i = 0; i < totalPhilosophers; i++) {
            philosophers[i] = new Philosopher(i);
            threads[i] = new Thread(philosophers[i]);
            threads[i].start();
        }

        System.out.println("Last line has been executed in \"main.java\".");
    }
}