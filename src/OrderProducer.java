import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created By John Anderson
 * Producer class is vital for creating objects in the producer consumer modal
 * Producer will mostly make pumpkins and add them to the queue
 */
public class OrderProducer implements Runnable {

    //LinkedBlockingQueue object
    private LinkedBlockingQueue<Order> orders;
    private LoggingService loggingService;
    private RandomService randomService;
    private long startTime;
    private int orderCount;

    public OrderProducer(LinkedBlockingQueue<Order> orders) {
        this.loggingService = new LoggingService("Order Producer");
        this.randomService = new RandomService(1, 2);
        this.startTime = System.currentTimeMillis(); //Saves the start time of the whole thread
        this.orders = orders;
    }

    /**
     * Check to see if 1 million time units have elapsed can
     * Change the value to something in the property file for easy change
     * @return boolean
     */
    public boolean elapsedTimeMax() {
        return 1000000 < System.currentTimeMillis() - startTime;
    }

    @Override //Run for 1 million units of time
    public void run() {
        try {
            while (elapsedTimeMax()) {
                Thread.sleep(randomService.getTime()); //Wait random amount of time to spawn new order
                orders.put(new Order(orderCount));
                orderCount++;
                loggingService.logEvent(Event.ORDER_PLACED, System.currentTimeMillis());
            }

            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
