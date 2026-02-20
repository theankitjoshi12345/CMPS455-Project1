import java.util.Random;

public class CoordinatingThread extends Thread {
    public static Random rand = new Random();
    public static int enteredAgents = 0;
    public int agentNumber;

    public CoordinatingThread(int agentNumber) {
        this.agentNumber = agentNumber;
    }

    @Override
    public void run() {
        if (Main.readingAgents != 0) {
            try {
                Main.wTurn.acquire();
            } catch (Exception e) {
                System.out.println("Exception has occured: " + e);
            }
        }

        try {
            Main.sharedFile.acquire();
        } catch (InterruptedException e) {
            System.out.println("Exception has occured: " + e);
        }
        System.out.println("CA"+ agentNumber + " is coordinating.");
        for (int i = 0; i < rand.nextInt(10) + 11; i++) {
            // Coordinating.
        }
        Main.sharedFile.release();

        enteredAgents++;
        if (ReadingThread.enteredAgents == Main.readingAgents) {
            Main.wTurn.release();
        } else {
            Main.rTurn.release();
        }
            
    }
    
}
