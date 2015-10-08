import java.util.concurrent.LinkedBlockingQueue;

/**
 * Driver class for our pumpkin patch program
 */
public class PumpkinDriver {

    //Main Driver to start our program
    public static void main(String[] args) {

        //LinkedBlockingQueue object created with size 1
        LinkedBlockingQueue<String> orders = new LinkedBlockingQueue<String>(100);
        LinkedBlockingQueue<String> pumpkins = new LinkedBlockingQueue<String>(100);

        new Thread(new Producer(pumpkins, orders)).start();
        new Thread(new Consumer(pumpkins, orders)).start();

    }
}
