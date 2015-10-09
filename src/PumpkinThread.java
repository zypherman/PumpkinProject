import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/8/15.
 * Pumpkin thread is needed to maintain the count of a growing pumpkin
 * As well as move it to the ripe queue when its done growing so
 * it can be picked by the consumer thread
 */
public class PumpkinThread implements Runnable {

    private LinkedBlockingQueue<Pumpkin> ripePumpkins;
    private LoggingService loggingService;
    private long timeGrowing;

    /**
     * Pumpkin Thread constructor
     * Takes in a linked blocked queue of ripe pumpkins
     *
     * @param ripePumpkins LinkedBlockingQueue<Pumpkin>
     */
    public PumpkinThread(LinkedBlockingQueue<Pumpkin> ripePumpkins) {
        this.timeGrowing = new RandomService(1, 2).getTime(); //Get the growing time from random service
        this.ripePumpkins = ripePumpkins;
        loggingService = new LoggingService("Pumpkin Thread");
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
            Pumpkin pumpkin = new Pumpkin(); //New Pumpkin object being planted
            loggingService.logEvent(Event.NEW_PLANT, System.currentTimeMillis()); //Log the event
            Thread.sleep(timeGrowing); //Wait while the pumpkin grows
            ripePumpkins.put(pumpkin); //These are ripe pumpkins this might be an array list
            loggingService.logEvent(Event.RIPE_PUMPKIN, System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
