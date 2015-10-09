import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created By John Anderson
 * Producer class is vital for creating objects in the producer consumer modal
 * Producer will mostly make pumpkins and add them to the queue
 *
 */
public class OrderProducer implements Runnable {

    //LinkedBlockingQueue object
    //private LinkedBlockingQueue<Order> orders;
    private LinkedBlockingQueue<PumpkinThread> pumpkinThreads;
    private LoggingService loggingService;

    public OrderProducer(LinkedBlockingQueue<PumpkinThread> pumpkinThreads) {
        //this.orders = orders;
        this.pumpkinThreads = pumpkinThreads;
        this.loggingService = new LoggingService("Producer");
    }

    @Override
    public void run() {
        try {
            int i=1;
            while(i<=5){
                //add item
//                orders.put(new Order("Order " + i));
//                loggingService.writeToConsole("Order" + i + " added.");
//                i++;
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
