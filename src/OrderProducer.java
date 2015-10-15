import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created By John Anderson
 * Order producer class to create orders for jack to fill
 */
public class OrderProducer implements Runnable {

    private LinkedBlockingQueue<Order> orders;
    LinkedBlockingQueue<Log> log;
    private RandomService randomService;
    private Instant startTime;
    private int orderCount;
    private int runtime;
    private long delay;


    public OrderProducer(LinkedBlockingQueue<Order> orders, LinkedBlockingQueue<Log> log) {

        this.delay = Duration.ofSeconds(PropertyLoader.getInstance().getValue("orderdelay")).toMillis();
        this.randomService = new RandomService(PropertyLoader.getInstance().getValue("ordertime"),
                PropertyLoader.getInstance().getValue("ordertimedistribution"));

        this.runtime = PropertyLoader.getInstance().getValue("runtime");
        this.log = log;
        this.orders = orders;
    }

    /**
     * Check to see if total time has elapsed can
     * Uses the distance and instant classes
     *
     * @return boolean
     */
    private boolean checkTime() {
        return (Duration.between(startTime, Instant.now()).toMinutes() > runtime);
    }

    /**
     * Will run entire length of the program
     * Will get the config value of the total runtime of the program
     */
    @Override
    public void run() {
        try {
            //Wait a bit before placing orders
            Thread.sleep(delay);
            this.startTime = Instant.now(); //Set start time

            while (!checkTime()) {
                orders.put(new Order(orderCount));
                orderCount++;
                log.put(new Log(Event.ORDER_PLACED)); //Write to log
                Thread.sleep(randomService.getTime().toMillis()); //Wait random amount of time to spawn new order
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
