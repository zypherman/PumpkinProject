import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class is in charge of spawnining pumpkin threads which represent a growing pumpkin
 * It will also keep track of the stash of pumpkins and ensure its not too empty or too full
 */
public class PumpkinProducer implements Runnable {

    //private LinkedBlockingQueue<Order> orders;
    private LinkedBlockingQueue<PumpkinThread> pumpkinThreads; //Growing Pumpkins
    private LinkedBlockingQueue<Pumpkin> ripePumpkins; //Ripe Pumpkins
    private LinkedBlockingQueue<Pumpkin> pumpkins; //Stash
    private LoggingService loggingService;


    public PumpkinProducer(LinkedBlockingQueue<Pumpkin> pumpkins,
                           LinkedBlockingQueue<Pumpkin> ripePumpkins) {

        this.pumpkinThreads = new LinkedBlockingQueue<PumpkinThread>();
        this.loggingService = new LoggingService("Producer");
        this.ripePumpkins = ripePumpkins;
        this.pumpkins = pumpkins;
    }

    @Override //Run until all orders have been filled and its past 1,000,000 time units
    public void run() {
        try {
            int i = 1;
            while (i < 5) {
                //Create and start a new pumpkin thread to grow
                PumpkinThread thread = new PumpkinThread(ripePumpkins);
                thread.start();
                pumpkinThreads.put(thread); //Add thread to the thread queue

                //Remove this shit eventually
                i++;
                Thread.sleep(1000); //Create new thread every 1000 time units


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
