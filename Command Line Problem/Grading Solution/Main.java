import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    public static Semaphore sharedFile = new Semaphore(1);
    public static Semaphore wTurn = new Semaphore(0); // Priority will be given to readers instead.
    public static Semaphore rTurn = new Semaphore(1);
    public static Semaphore mutex = new Semaphore(1); // Used to manipulate number of readers agents accessing.

    public static int readingAgents;
    public static int coordinatingAgents;
    public static int maxReadingAgents;

    public static int totalPhilosophers;
    public static int totalMeals;

    public static Semaphore[] chopsticks;
    public static Semaphore mealMutex = new Semaphore(1);
    public static Semaphore semHold = new Semaphore(0);

    public static Scanner in = new Scanner(System.in);
     
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("1 Flag (-A) and 1 Parameter (1, 2) are required in Command Line. No More or No Less.");
            return;
        }

        if (!args[0].equals("-A")) {
            System.out.println("Not valid arguments. Use (-A) Flag followed by one of (1, 2) Parameter.");
            return;
        }

        try {
            int param = Integer.parseInt(args[1]);
            if (param == 1) {
                System.out.println("-A 1\nPhilosophers Problem:");
                philosophersProblem();
            } else if (param == 2) {
                System.out.println("-A 2\nReaders-Coordinators Problem:");
                readersCoordinatorsProblem();
            } else {
                System.out.println("Not a valid parameter. Use one of (1, 2).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Parameter must be a number (1 or 2).");
        }
    }
    
    public static void philosophersProblem() {
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


    public static void readersCoordinatorsProblem() {
        // Reading Agents
        System.out.print("Enter number of Reading Agents (Non-negative Integers only): ");
        if (!in.hasNextInt()) {
            System.out.println("Input Error: Reading Agents must be a non-negative integer.");
            in.close();
            return;
        }
        readingAgents = in.nextInt();
        if (readingAgents < 0) {
            System.out.println("Input Error: Reading Agents cannot be negative.");
            in.close();
            return;
        }

        // Coordinating Agents
        System.out.print("Enter number of Coordinating Agents (Non-negative Integers only): ");
        if (!in.hasNextInt()) {
            System.out.println("Input Error: Coordinating Agents must be a non-negative integer.");
            in.close();
            return;
        }
        coordinatingAgents = in.nextInt();
        if (coordinatingAgents < 0) {
            System.out.println("Input Error: Coordinating Agents cannot be negative.");
            in.close();
            return;
        }

        // Max Reading Agents
        System.out.print("Enter maximum number of Reading Agents (Non-zero Integers only): ");
        if (!in.hasNextInt()) {
            System.out.println("Input Error: Maximum Reading Agents must be a positive integer.");
            in.close();
            return;
        }
        maxReadingAgents = in.nextInt();
        if (maxReadingAgents <= 0) {
            System.out.println("Input Error: Maximum Reading Agents must be greater than zero.");
            in.close();
            return;
        }

        in.close();

        for (int i = 0; i < readingAgents; i++) {
            ReadingThread r = new ReadingThread(i);
            r.start();
        }
        for (int j = 0; j < coordinatingAgents; j++) {
            CoordinatingThread c = new CoordinatingThread(j);
            c.start();
        }

        System.out.println("Last line has been executed in \"main.java\".");
    }
}