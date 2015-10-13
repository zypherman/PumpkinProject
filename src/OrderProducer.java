import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created By John Anderson
 * Producer class to make orders for our program
 */
public class OrderProducer implements Runnable {

    private LinkedBlockingQueue<Order> orders;
    private LoggingService loggingService;
    private RandomService randomService;
    private Instant startTime;
    private int orderCount;

    public OrderProducer(LinkedBlockingQueue<Order> orders) {
        this.loggingService = new LoggingService("Order Generator");
        this.randomService = new RandomService(5, 2);
        this.orders = orders;
    }

    /**
     * Check to see if 10 min's have elapsed can
     * Uses the distance and instant classes
     * @return boolean
     */
    private boolean checkTime() {
        return (Duration.between(startTime, Instant.now()).toMinutes() > PropertyLoader.getInstance().getValue("runtime"));
    }

    /**
     * Will run entire length of the program
     * Once 10 min's has elapsed it will close
     */
    @Override
    public void run() {
        try {
            Thread.sleep(Duration.ofSeconds(PropertyLoader.getInstance().getValue("runtime")).toMillis()); //Wait a bit before placing orders
            this.startTime = Instant.now(); //Set start time

            while (!checkTime()) {
                orders.put(new Order(orderCount));
                orderCount++;
                loggingService.logEvent(Event.ORDER_PLACED, Instant.now());
                Thread.sleep(randomService.getTime().toMillis()); //Wait random amount of time to spawn new order
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
