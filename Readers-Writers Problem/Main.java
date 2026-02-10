import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static Semaphore area = new Semaphore(1);
    public static Semaphore mutex = new Semaphore(1); // For doing readersCount operations
    public static Semaphore readersGate = new Semaphore(1); // For stoping readers after maxReaders
    public static int readers;
    public static int writers;
    public static int maxReaders;
    public static int readersCount = 0; // It will be used to make sure no more than 10 threads will read a file at one time
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of readers: ");
        readers = in.nextInt();
        System.out.print("Enter number of writers: ");
        writers = in.nextInt();
        System.out.print("Enter maximum number of readers: ");
        maxReaders = in.nextInt();
        in.close();

        for (int i = 0; i < readers; i++) {
            Read r = new Read(i);
            r.start();
        }
        for (int j = 0; j < writers; j++) {
            Write w = new Write(j);
            w.start();
        }
    }
}