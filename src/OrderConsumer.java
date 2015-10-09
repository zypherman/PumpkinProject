import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/9/15.
 * Order consumer
 * Handles getting pumpkins and adding them to orders
 * Also hands off pumpkins to the delivery worker
 */
public class OrderConsumer implements Runnable {

    //LinkedBlockingQueue object
    private LinkedBlockingQueue<Pumpkin> pumpkins; //Stash
    private LinkedBlockingQueue<Order> orders; //Order queue
    private LoggingService loggingService;

    /**
     * Constructor for our consumer class
     * Needs to know the orders and pumpkins queues
     */
    public OrderConsumer(LinkedBlockingQueue<Pumpkin> pumpkins,
                           LinkedBlockingQueue<Order> orders) {

        this.loggingService = new LoggingService("Order Consumer");
        this.pumpkins = pumpkins;
        this.orders = orders;
    }

    @Override
    public void run() {
        try {
            while(!orders.isEmpty()) {
                //Also add a pumpkin to that order
                orders.take().addPumpkin(pumpkins.take()); //Give this order to the delivery something service?
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
