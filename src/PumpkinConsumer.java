import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/8/15.
 * Consumer class manages consuming objects from the queue that the producer is creating
 */
//Consumer class that will consume or remove item from LinkedBlockingQueue object
public class PumpkinConsumer implements Runnable {

    //LinkedBlockingQueue object
//    private LinkedBlockingQueue<Order> orders;
    private LinkedBlockingQueue<PumpkinThread.Pumpkin> pumpkins;
    private LoggingService loggingService;

    /**
     * Constructor for our consumer class
     * Needs to know the orders and pumpkins queues
     *
     * @param pumpkinThreads LinkedBlockingQueue<Pumpkin>
     */
    public PumpkinConsumer(LinkedBlockingQueue<PumpkinThread.Pumpkin> pumpkins) {
        this.pumpkins = pumpkins;
        this.loggingService = new LoggingService("Consumer");
    }

    @Override
    public void run() {
        try {
            while(true) {
                if(!pumpkins.isEmpty()) {
                    loggingService.writeToConsole(pumpkins.take().toString());
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
