import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created By John Anderson
 * Producer class is vital for creating objects in the producer consumer modal
 */
public class Producer implements Runnable {

    //LinkedBlockingQueue object
    private LinkedBlockingQueue<String> orders;
    private LinkedBlockingQueue<String> pumpkins;
    private LoggingService loggingService;

    public Producer(LinkedBlockingQueue<String> orders,LinkedBlockingQueue<String> pumpkins) {
        this.orders = orders;
        this.pumpkins= pumpkins;
        this.loggingService = new LoggingService("Producer");
    }

    @Override
    public void run() {
        try {
            int i=1;
            while(i<=5){
                //add item
                orders.put("A" + i);
                loggingService.writeToConsole("A" + i + " added.");
                i++;
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
