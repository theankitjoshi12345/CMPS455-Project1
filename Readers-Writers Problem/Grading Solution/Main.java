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
        System.out.print("Enter number of Reading Agents (Non-negative Integers only): ");
        readingAgents = in.nextInt(); // Can be any non-zero integers only
        System.out.print("Enter number of Coordinating Agents (Non-negative Integers only): ");
        coordinatingAgents = in.nextInt(); // Can be any non-zero integers only
        System.out.print("Enter maximum number of Reading Agents (Non-zero Integers only): ");
        maxReadingAgents = in.nextInt(); // Can be any positive integers equal to or less than reading agents.
        in.close();

        for (int i = 0; i < readingAgents; i++) {
            ReadingThread r = new ReadingThread(i);
            r.start();
        }
        for (int j = 0; j < coordinatingAgents; j++) {
            CoordinatingThread c = new CoordinatingThread(j);
            c.start();
        }
    }
}