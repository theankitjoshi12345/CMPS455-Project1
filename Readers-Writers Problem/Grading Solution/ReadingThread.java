import java.util.Random;

public class ReadingThread extends Thread {
    public static Random rand = new Random();
    public static int enteredAgents = 0;
    public static int agentsInExecution = 0;
    public int agentNumber;

    public ReadingThread(int agentNumber) {
        this.agentNumber = agentNumber;
    }

    public void run() {
        try {
            Main.rTurn.acquire();
        } catch (Exception e) {
            System.out.println("Exception has occured: " + e);
        }
        try {
            Main.mutex.acquire();
        } catch (Exception e) {
            System.out.println("Exception has occured: " + e);
        }
        enteredAgents++;
        agentsInExecution++;
        if (agentsInExecution == 1) {
            try {
                Main.sharedFile.acquire();
            } catch (InterruptedException e) {
                System.out.println("Exception has occured: " + e);
            }
        }
        if (enteredAgents % Main.maxReadingAgents != 0 && enteredAgents != Main.readingAgents) {
            Main.rTurn.release();
            Main.mutex.release();            
        }else {
            Main.mutex.release();
            if (CoordinatingThread.enteredAgents == Main.coordinatingAgents) {
                Main.rTurn.release();
            } else {
                Main.wTurn.release();
            }
        }

        System.out.println("RA" + agentNumber + " is reading.");
        for (int i = 0; i < rand.nextInt(10) + 11; i++) {
            // Reading.
        }

        if (agentsInExecution == Main.maxReadingAgents || enteredAgents == Main.readingAgents) {
            agentsInExecution = 0;
            Main.sharedFile.release();
        }

    }
}