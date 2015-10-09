import java.util.concurrent.LinkedBlockingQueue;

public class PumpkinProducer implements Runnable {

    //private LinkedBlockingQueue<Order> orders;
    private LinkedBlockingQueue<PumpkinThread> pumpkinThreads;
    private LoggingService loggingService;
    private LinkedBlockingQueue<PumpkinThread.Pumpkin> pumpkins;

    public PumpkinProducer(LinkedBlockingQueue<PumpkinThread> pumpkinThreads,
                           LinkedBlockingQueue<PumpkinThread.Pumpkin> pumpkins) {
        //this.orders = orders;
        this.pumpkinThreads = pumpkinThreads;
        this.pumpkins = pumpkins;
        this.loggingService = new LoggingService("Producer"); //Start the logging service
    }

    @Override
    public void run() {
        try {
            int i = 1;
            while (i < 5) {
                //Create a new pumpkin to plant
                PumpkinThread thread = new PumpkinThread(pumpkins);
                thread.start();
                pumpkinThreads.put(thread);
                loggingService.writeToConsole("Created new Pumpkin Thread");
                i++;
                Thread.sleep(1000); //Create new thread every 1000 time units
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
