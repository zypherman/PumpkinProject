import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Driver class for our pumpkin patch program
 */
public class PumpkinDriver {

    //Main Driver to start our program
    public static void main(String[] args) {

        //LinkedBlockingQueue object created with size 1
        LinkedBlockingQueue<Order> orders = new LinkedBlockingQueue<Order>(); //Orders
        LinkedBlockingQueue<Pumpkin> pumpkins = new LinkedBlockingQueue<Pumpkin>(PropertyLoader.getInstance().getValue("stashsize")); //Pumpkin Stash
        LinkedBlockingQueue<Pumpkin> ripePumpkins = new LinkedBlockingQueue<Pumpkin>();
        PropertyLoader propertyLoader = PropertyLoader.getInstance(); //Create our PropertyLoader instance
        ArrayList<PumpkinThread> pumpkinThreads = new ArrayList<PumpkinThread>();
        LinkedBlockingQueue<Log> log = new LinkedBlockingQueue<Log>();


        //Start order producer process
        new Thread(new Jack(pumpkins, ripePumpkins, orders, pumpkinThreads, log)).start();
        new Thread(new OrderProducer(orders, log)).start();
        new Thread(new FileIO(log, true)).start();
    }
}
