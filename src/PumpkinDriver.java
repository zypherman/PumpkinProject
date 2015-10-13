import java.util.concurrent.LinkedBlockingQueue;

/**
 * Driver class for our pumpkin patch program
 */
public class PumpkinDriver {

    //Main Driver to start our program
    public static void main(String[] args) {

        //LinkedBlockingQueue object created with size 1
        LinkedBlockingQueue<Thread> threads = new LinkedBlockingQueue<Thread>();
        LinkedBlockingQueue<Order> orders = new LinkedBlockingQueue<Order>(); //Orders
        LinkedBlockingQueue<Pumpkin> pumpkins = new LinkedBlockingQueue<Pumpkin>(10000); //Pumpkin Stash
        LinkedBlockingQueue<Pumpkin> ripePumpkins = new LinkedBlockingQueue<Pumpkin>();
        PropertyLoader propertyLoader = PropertyLoader.getInstance(); //Create our PropertyLoader instance


        //Start order producer process
        new Thread(new Jack(pumpkins, ripePumpkins, orders)).start();
        new Thread(new OrderProducer(orders)).start();


    }
}
