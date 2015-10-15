import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/8/15.
 * Logging service class to help files write to the log to output all the events
 * Implementing the singleton pattern so we can initialize it once and then
 * Hold the queues in it
 * Also synchronized the instance method ensures only one threads gets the instance
 */
public class LoggingService {

    private LinkedBlockingQueue<Pumpkin> ripePumpkins;
    private ArrayList<PumpkinThread> pumpkinThreads;
    private LinkedBlockingQueue<String> log;

    public LoggingService(LinkedBlockingQueue<Pumpkin> ripePumpkins,
                           ArrayList<PumpkinThread> pumpkinThreads,
                           LinkedBlockingQueue<String> log) {

        this.pumpkinThreads = pumpkinThreads;
        this.ripePumpkins = ripePumpkins;
        this.log = log;
    }

    public String getPumpkinStats() {
        return "Growing Pumpkins: " + pumpkinThreads.size() + '\n' + "Ripe Pumpkins: " + ripePumpkins.size();
    }

    /**
     * Synchronized method to handle logging events
     *
     * @param event     Event
     * @param timestamp Instant
     */
    public synchronized void logEvent(Event event, Instant timestamp) {
        switch (event) {
            case NEW_PLANT:
                System.out.println("New Pumpkin was planted at time: " + timestamp.toString() + getPumpkinStats());
                break;
            case RIPE_PUMPKIN:
                System.out.println("A pumpkin is ripe at time: " + timestamp.toString() + getPumpkinStats());
                break;
            case GATHER_PUMPKIN:
                System.out.println("Pumpkin was gathered from the patch at time: " + timestamp.toString() + getPumpkinStats());
                break;
            case ORDER_PLACED:
                System.out.println("Order was placed at time:" + timestamp.toString() + getPumpkinStats());
                break;
            case ORDER_DELIVERED:
                System.out.println("Order was delivered at time: " + timestamp.toString() + getPumpkinStats());
                break;
            case PUMPKIN_COMPOSTED:
                System.out.println("Pumpkin was composted at time: " + timestamp.toString() + getPumpkinStats());
                break;
            default:
                System.out.println("Unlisted Event at time: " + timestamp.toString() + getPumpkinStats());
                break;
        }
    }

    /**
     * Needed an extra method to log the extra data
     */
    public void logOrder(Instant timestamp, String orderString) {
        System.out.println("Order was delivered: " + orderString + " at time: " + timestamp.toString());
    }

    public void writeToConsole(String event) {
        System.out.println(event);
    }

}
