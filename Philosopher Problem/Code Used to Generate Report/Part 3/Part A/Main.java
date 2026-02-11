import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    // Declaring global variables which will be shared across threads.
    public static int totalPhilosophers;
    // public static int totalMeals;

    public static Semaphore[] chopsticks;
    public static Semaphore semHold = new Semaphore(0);
    public static Semaphore mutex = new Semaphore(1);

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the number of philosophers: ");
        totalPhilosophers = in.nextInt();
        // System.out.print("Enter total number of meals: ");
        // totalMeals = in.nextInt();
        // totalMeals = totalPhilosophers * 4;
        in.close();
        
        long start_time = System.nanoTime();

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

        // IMPORTANT: wait for all philosophers to finish
        for (int i = 0; i < totalPhilosophers; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                System.out.println("Exception has occured: "+ e);
            }
        }
                
        long end_time = System.nanoTime();
        System.out.printf("Runtime in milliseconds = "); 
        System.out.println((end_time - start_time) / 1000000.0);

        System.out.println("Last line has been executed in \"main.java\".");
    }
}