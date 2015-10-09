import java.util.concurrent.LinkedBlockingQueue;

/**
 * Driver class for our pumpkin patch program
 */
public class PumpkinDriver {

    //Main Driver to start our program
    public static void main(String[] args) {

        //LinkedBlockingQueue object created with size 1
        LinkedBlockingQueue<Order> orders = new LinkedBlockingQueue<Order>(100);
        LinkedBlockingQueue<Pumpkin> pumpkins = new LinkedBlockingQueue<Pumpkin>(100);

        new Thread(new OrderProducer(orders, pumpkins)).start();
        new Thread(new Consumer(pumpkins, orders)).start();

    }
}
