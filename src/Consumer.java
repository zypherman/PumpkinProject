import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/8/15.
 * Consumer class manages consuming objects from the queue that the producer is creating
 */
//Consumer class that will consume or remove item from LinkedBlockingQueue object
public class Consumer implements Runnable {

    //LinkedBlockingQueue object
    private LinkedBlockingQueue<String> orders;
    private LinkedBlockingQueue<String> pumpkins;
    private LoggingService loggingService;

    /**
     * Constructor for our consumer class
     * Needs to know the orders and pumpkins queues
     *
     * @param pumpkins
     * @param orders
     */
    public Consumer(LinkedBlockingQueue<String> pumpkins, LinkedBlockingQueue<String> orders) {
        this.orders = orders;
        this.pumpkins = pumpkins;
        this.loggingService = new LoggingService("Consumer");
    }

    @Override
    public void run() {
        try {
            int i = 1;
            while (!orders.isEmpty()) {
                //removes the item
                String s = orders.take();
                loggingService.writeToConsole(s + " removed.");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
