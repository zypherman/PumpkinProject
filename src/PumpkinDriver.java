import java.util.concurrent.LinkedBlockingQueue;

/**
 * Driver class for our pumpkin patch program
 */
public class PumpkinDriver {

    //Main Driver to start our program
    public static void main(String[] args) {

        //LinkedBlockingQueue object created with size 1
        //LinkedBlockingQueue<Order> orders = new LinkedBlockingQueue<Order>(100);
        LinkedBlockingQueue<PumpkinThread.Pumpkin> pumpkins = new LinkedBlockingQueue<PumpkinThread.Pumpkin>();
        LinkedBlockingQueue<PumpkinThread> pumpkinThreads = new LinkedBlockingQueue<PumpkinThread>(100);

        //new Thread(new OrderProducer(pumpkins)).start();
        new Thread(new PumpkinProducer(pumpkinThreads, pumpkins)).start();
        new Thread(new PumpkinConsumer(pumpkins)).start();

    }
}
