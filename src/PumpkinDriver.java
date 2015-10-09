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
        //LinkedBlockingQueue<PumpkinThread> pumpkinThreads = new LinkedBlockingQueue<PumpkinThread>();
        LinkedBlockingQueue<Pumpkin> ripePumpkins = new LinkedBlockingQueue<Pumpkin>();

        //Start up jack
        new Thread(new Jack()).start();
        //Can produce orders all the time
        OrderProducer orderProducer = new OrderProducer(orders);

        //Can only produce pumpkins when jack is at the patch to plant
        Thread pumpkinProducer = new Thread(new PumpkinProducer(pumpkins, ripePumpkins));
        pumpkinProducer.start();
        //Can only produce pumpkins when jack is at the patch to plant
        PumpkinConsumer pumpkinConsumer = new PumpkinConsumer(pumpkins,ripePumpkins, orders);

        //Can only consume orders when jack is processing orders
        OrderConsumer orderConsumer = new OrderConsumer(pumpkins, orders);

       // threads.put(new Thread(pumpkinProducer));



    }
}
