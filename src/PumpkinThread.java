import java.time.Duration;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/8/15.
 * Pumpkin thread is needed to maintain the count of a growing pumpkin
 * As well as move it to the ripe queue when its done growing so
 * it can be picked by the consumer thread
 */
public class PumpkinThread extends Thread {

    private LinkedBlockingQueue<Pumpkin> ripePumpkins;
    private LinkedBlockingQueue<Log> log;
    private RandomService randomService;
    Duration timeGrowing;
    private boolean grow = true;

    /**
     * Pumpkin Thread constructor
     * Takes in a linked blocked queue of ripe pumpkins
     *
     * @param ripePumpkins LinkedBlockingQueue<Pumpkin>
     */
    public PumpkinThread(LinkedBlockingQueue<Pumpkin> ripePumpkins, LinkedBlockingQueue<Log> log) {
        this.randomService = new RandomService(PropertyLoader.getInstance().getValue("growtime"),
                PropertyLoader.getInstance().getValue("growdistribution"));

        this.log = log;
        this.ripePumpkins = ripePumpkins;
    }

    /**
     * Method to tell the thread to stop growing another pumpkin
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
            while (grow) {
                //Get new grow time
                timeGrowing = randomService.getTime();

                Pumpkin pumpkin = new Pumpkin(); //New Pumpkin object being planted
                log.put(new Log(Event.NEW_PLANT)); //Log the event
                Thread.sleep(timeGrowing.toMillis()); //Wait while the pumpkin grows

                ripePumpkins.put(pumpkin); //These are ripe pumpkins
                log.put(new Log(Event.RIPE_PUMPKIN)); //Log the event
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
