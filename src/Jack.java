import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by John on 10/8/15.
 * This is now jack.
 * Pumpkin consumer
 */
public class Jack implements Runnable {

    private LinkedBlockingQueue<Pumpkin> ripePumpkins;
    private ArrayList<PumpkinThread> pumpkinThreads;
    private LinkedBlockingQueue<Pumpkin> pumpkins;
    private LinkedBlockingQueue<Order> orders;
    private LinkedBlockingQueue<Log> log;
    boolean startedDelivering = false;
    ArrayList<Order> deliveryOrders;
    int deliverytimedistribution;
    private Instant startTime;
    int deliverytime;
    int runtime;

    /**
     * Constructor for our consumer class
     * Needs to know the orders and pumpkins queues
     * As well as the ripe pumpkins to pick
     */
    public Jack(LinkedBlockingQueue<Pumpkin> pumpkins,
                LinkedBlockingQueue<Pumpkin> ripePumpkins,
                LinkedBlockingQueue<Order> orders,
                ArrayList<PumpkinThread> pumpkinThreads,
                LinkedBlockingQueue<Log> log) {

        this.deliverytimedistribution = PropertyLoader.getInstance().getValue("deliverytimedistribution");
        this.deliverytime = PropertyLoader.getInstance().getValue("deliverytime");
        this.runtime = PropertyLoader.getInstance().getValue("runtime");
        this.pumpkinThreads = pumpkinThreads;
        this.ripePumpkins = ripePumpkins;
        this.startTime = Instant.now();
        this.pumpkins = pumpkins;
        this.orders = orders;
        this.log = log;
    }

    /**
     * Check to see if time is up
     * Also checks that if it is that orders are also empty
     * Uses the distance and instant classes
     * If time is up then stop all the growing threads
     *
     * @return boolean
     */
    private boolean checkTime() {
        if ((Duration.between(startTime, Instant.now()).toMinutes()) > runtime && orders.isEmpty()) {
            stopGrowing();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Goes to each thread and tells it to stop growing pumpkins
     */
    private void stopGrowing() {
        for (PumpkinThread thread : pumpkinThreads) {
            thread.stopGrow();
        }
    }

    /**
     * Check the stash to make sure its an okay level
     * Verify that we have started delivering orders
     * If under threshold we need to plant 4 more
     * If over threshold we need to compost 5 ripe plants
     * Return 1 for under
     * 2 for over
     * -1 for normal
     */
    private int checkStash() {
        if (pumpkins.size() < 20 && startedDelivering) {
            return 1;
        } else if (pumpkins.size() >= 9000) {
            return 2;
        } else {
            return -1;
        }
    }

    /**
     * Pick pumpkins from the patch that are ripe
     *
     * @throws InterruptedException
     */
    private int pickPumpkins() throws InterruptedException {
        int pumpkinCount = ripePumpkins.size(); //Gather time is 2n
        while (!ripePumpkins.isEmpty()) {
            pumpkins.put(ripePumpkins.take()); //Add them to the pumpkins stash
            Log loggedEvent = new Log(Event.GATHER_PUMPKIN); //Log Event
            log.put(loggedEvent);
        }

        Thread.sleep(Duration.ofSeconds(pumpkinCount * 2).toMillis());
        return pumpkinCount;
    }

    /**
     * Composts 5 pumpkins that are ripe
     *
     * @throws InterruptedException
     */
    private void compostPumpkins() throws InterruptedException {
        if (!ripePumpkins.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                ripePumpkins.take();
            }
        }
    }

    /**
     * Gathers pumpkins based on the differences in the stash size
     *
     * @throws InterruptedException
     */
    private void gatherPumpkins() throws InterruptedException {

        int stashCheck = checkStash();

        if (!ripePumpkins.isEmpty() && (stashCheck != 2)) {
            //Our stash is low
            if (stashCheck == 1) {
                int pumpkinCount = pickPumpkins();

                //Plant 4 a new pumpkin for each ripe pumpkin
                for (int i = 0; i < (pumpkinCount * 4); i++) {
                    PumpkinThread temp = new PumpkinThread(ripePumpkins, log);
                    temp.start();
                    pumpkinThreads.add(temp);
                }

            } else { //Gather pumpkins like normal
                pickPumpkins();
            }
        } else if (stashCheck == 2) { //If our stash is too full
            compostPumpkins(); //Compost pumpkins
        }

    }

    /**
     * Entire delivery of the pumpkin orders
     * Will add pumpkins from pumpkin stash to order
     * then it will deliver them if we have orders to fill
     *
     * @throws java.lang.InterruptedException
     */
    private void deliverPumpkins() throws InterruptedException {

        deliveryOrders = new ArrayList<Order>();
        for (Order order : orders) {
            order.addPumpkin(pumpkins.take()); //Add Pumpkin
            deliveryOrders.add(order); //Add to delivery
        }

        //Sleep while we drive to the destination and get a random amount of time it takes to deliver
        Thread.sleep(new RandomService(deliverytime, deliverytimedistribution).getTime().toMillis()); //Drive from post office

        for (Order order : deliveryOrders) {
            log.put(new Log(Instant.now(), order.toString()));
        }

        deliveryOrders = null; //Clear our the delivery's
    }

    @Override
    public void run() {
        try {

            //Start out growing pumpkins
            for (int i = 0; i < 25; i++) {
                PumpkinThread temp = new PumpkinThread(ripePumpkins, log);
                temp.start();
                pumpkinThreads.add(temp);
            }

            while (checkTime()) {

                //Gathers Pumpkins
                gatherPumpkins();

                //Delivers orders if there are orders to deliver
                if (!orders.isEmpty()) {
                    deliverPumpkins();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
