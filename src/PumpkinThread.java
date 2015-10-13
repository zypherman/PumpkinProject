import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/8/15.
 * Pumpkin thread is needed to maintain the count of a growing pumpkin
 * As well as move it to the ripe queue when its done growing so
 * it can be picked by the consumer thread
 */
public class PumpkinThread extends Thread {

    private LinkedBlockingQueue<Pumpkin> ripePumpkins;
    private LoggingService loggingService;
    private boolean grow = true;

    /**
     * Pumpkin Thread constructor
     * Takes in a linked blocked queue of ripe pumpkins
     * @param ripePumpkins LinkedBlockingQueue<Pumpkin>
     */
    public PumpkinThread(LinkedBlockingQueue<Pumpkin> ripePumpkins) {
        loggingService = new LoggingService("Pumpkin Thread");
        this.ripePumpkins = ripePumpkins;
    }

    /**
     * Method to tell the thread to grow another pumpkin
     */
    public void stopGrow() {
        grow = false;
    }

    /**
     * Run method will create a new pumpkin object
     * Log the event
     * Wait for the pumpkin to ripen based on the random service's time limit
     * then add the pumpkin to the ripe queue
     * Log the second event
     */
    @Override
    public void run() {
        try {
            while(grow) {
                Duration timeGrowing = new RandomService(45,2).getTime(); //Get new grow time
                Pumpkin pumpkin = new Pumpkin(); //New Pumpkin object being planted
                loggingService.logEvent(Event.NEW_PLANT, Instant.now()); //Log the event
                Thread.sleep(timeGrowing.toMillis()); //Wait while the pumpkin grows
                ripePumpkins.put(pumpkin); //These are ripe pumpkins
                loggingService.logEvent(Event.RIPE_PUMPKIN, Instant.now());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
