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
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
    

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