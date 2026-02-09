import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    // Declaring global variables which will be shared across threads.
    public static int philosopherCount;
    public static int totalMeals;

    public static Semaphore[] chopsticks;
    public static int leftFinishingMeal;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the number of philosophers: ");
        philosopherCount = in.nextInt();
        System.out.print("Enter total number of meals: ");
        totalMeals = in.nextInt();

        in.close();

        // Initally, all of the philosophers are yet to finish their meal.
        leftFinishingMeal = philosopherCount;

        // # of chopsticks = # of philosophers
        chopsticks = new Semaphore[philosopherCount];
        Philosopher[] philosophers = new Philosopher[philosopherCount];
        Thread[] threads = new Thread[philosopherCount];
    
        // All chopsticks are initialized to semaphore value 1
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Semaphore(1);
        }

        // 1 thread is being created per philosopher
        // All philosophers are sitting together in the table
        for (int i = 0; i < philosopherCount; i++) {
            philosophers[i] = new Philosopher(i);
            threads[i] = new Thread(philosophers[i]);
        }

        // All philosophers, together, starting to eat the meals
        for (int i = 0; i < philosopherCount; i++) {
            threads[i].start();
        }

        System.out.println("Last line has been executed in \"main.java\".");
    }
}