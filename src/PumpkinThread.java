import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/8/15.
 * Pumpkin thread is needed to maintain the count of a growing pumpkin
 */
public class PumpkinThread extends Thread {

    private long timeGrowing;
    private LoggingService loggingService;
    private LinkedBlockingQueue<Pumpkin> pumpkins;
    int count = 0;


    public PumpkinThread(LinkedBlockingQueue<Pumpkin> pumpkins) {
        this.timeGrowing = getGrowTime(); //Set growing time to 0
        this.pumpkins = pumpkins;
        loggingService = new LoggingService("Pumpkin Thread");
    }

    public long getGrowTime() {
        RandomService randomService = new RandomService(1, 2);
        return randomService.getTime(); //Enter in the parameters for the pumpkin grow time
    }


    @Override
    public void run() {
        try {
            Pumpkin pumpkin = new Pumpkin(count);
            loggingService.writeToConsole("Pumpkin Planted");
            Thread.sleep(timeGrowing);
            pumpkins.put(pumpkin);
            loggingService.writeToConsole("Pumpkin Put in stash"); //change to enum event
            count++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public class Pumpkin {

        private int id;

        public Pumpkin(int id) {
            this.id = id;
        }

        public String toString() {
            return "Pumpkin: " + id;
        }
    }

}
