import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/8/15.
 * This is now jack.
 */
public class PumpkinConsumer implements Runnable {

    //LinkedBlockingQueue object
    private LinkedBlockingQueue<Pumpkin> ripePumpkins;
    private LinkedBlockingQueue<Pumpkin> pumpkins;
    private LinkedBlockingQueue<Order> orders;
    private LoggingService loggingService;

    /**
     * Constructor for our consumer class
     * Needs to know the orders and pumpkins queues
     */
    public PumpkinConsumer(LinkedBlockingQueue<Pumpkin> pumpkins, LinkedBlockingQueue<Pumpkin> ripePumpkins,
                           LinkedBlockingQueue<Order> orders) {
        this.loggingService = new LoggingService("Consumer");
        this.ripePumpkins = ripePumpkins;
        this.pumpkins = pumpkins;
        this.orders = orders;
    }

    @Override
    public void run() {
        try {
            int i = 1;
            while (i <= 999999) {
                if (!ripePumpkins.isEmpty()) { //If there are ripe pumpkins
                    pumpkins.put(ripePumpkins.take()); //Add them to the pumpkins stash
                    loggingService.logEvent(Event.GATHER_PUMPKIN, System.currentTimeMillis()); //Log Event
                }
                i++;
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
