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
    private LinkedBlockingQueue<Pumpkin> pumpkins;
    private ArrayList<PumpkinThread> pumpkinThreads;
    private LinkedBlockingQueue<Order> orders;
    private ArrayList<Order> deliveryOrders;
    private LoggingService loggingService;
    private RandomService randomService;
    private Instant startTime;

    /**
     * Constructor for our consumer class
     * Needs to know the orders and pumpkins queues
     */
    public Jack(LinkedBlockingQueue<Pumpkin> pumpkins, LinkedBlockingQueue<Pumpkin> ripePumpkins,
                LinkedBlockingQueue<Order> orders) {

        this.pumpkinThreads = new ArrayList<PumpkinThread>();
        this.loggingService = new LoggingService("Jack");
        this.randomService = new RandomService(1, 1);
        this.ripePumpkins = ripePumpkins;
        this.startTime = Instant.now();
        this.pumpkins = pumpkins;
        this.orders = orders;
    }

    /**
     * Check to see if 10 min's have elapsed
     * Also checks that if it is that orders are also empty
     * Uses the distance and instant classes
     * If time is up then stop all the growing threads
     *
     * @return boolean
     */
    private boolean checkTime() {
        if ((Duration.between(startTime, Instant.now()).toMinutes() > 10) && orders.isEmpty()) {
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
     * If under 1000 pumpkins we need to plant 4 more
     * If over 10000 we need to compost 5 ripe plants
     * Return 1 for under
     * 2 for over
     * -1 for normal
     */
    private int checkStash() {
        if (pumpkins.size() < 20) {
            return 1;
        } else if (pumpkins.size() >= 9000) {
            return 2;
        } else {
            return -1;
        }
    }

    @Override
    public void run() {
        try {

            //Start our 1000 growing pumpkin threads
            for (int i = 0; i < 25; i++) {
                PumpkinThread temp = new PumpkinThread(ripePumpkins);
                temp.start();
                pumpkinThreads.add(temp);
                pumpkins.add(new Pumpkin());
            }

            loggingService.writeToConsole("Added 25 Pumpkin Plants");

            while (checkTime()) {

                /**
                 * Checks if there is a ripe pumpkin to pick
                 * As well as makes sure there is room in the stash
                 */
                int stashCheck = checkStash();
                if (!ripePumpkins.isEmpty() && (stashCheck != 2)) { //If there are ripe pumpkins
                    if (stashCheck == 1) { //Our stash is low
                        pumpkins.put(ripePumpkins.take()); //Add them to the pumpkins stash
                        loggingService.logEvent(Event.GATHER_PUMPKIN, Instant.now()); //Log Event
                        Thread.sleep(Duration.ofSeconds(2).toMillis()); //Sleep for 2 time units while gathering pumpkin

                        //After pick pumpkin plant 4 new pumpkins
                        for (int i = 0; i < 4; i++) {
                            PumpkinThread temp = new PumpkinThread(ripePumpkins);
                            temp.start();
                            pumpkinThreads.add(temp);
                        }

                    } else { //Gather all ripe pumpkins
                        while(!ripePumpkins.isEmpty()) {
                            pumpkins.put(ripePumpkins.take()); //Add them to the pumpkins stash
                            loggingService.logEvent(Event.GATHER_PUMPKIN, Instant.now()); //Log Event
                            Thread.sleep(Duration.ofSeconds(1).toMillis()); //Sleep for 2 time units while gathering pumpkin
                        }
                    }
                }

                /**
                 * Entire delivery of the pumpkin orders
                 * Will add pumpkins from pumpkin stash to order
                 * then it will deliver them
                 */
                if (!orders.isEmpty()) {
                    deliveryOrders = new ArrayList<Order>();
                    for (Order order : orders) {
                        order.addPumpkin(pumpkins.take()); //Add Pumpkin
                        deliveryOrders.add(order); //Add to delivery
                    }

                    //Sleep while we drive to the destination
                    Thread.sleep(Duration.ofSeconds(30).toMillis()); //Drive from post office

                    for (Order order: deliveryOrders) {
                        loggingService.logOrder(Instant.now(), order.toString());
                    }

                    deliveryOrders = null; //Clear our the delivery's
                }

            }
        } catch (InterruptedException e)

        {
            e.printStackTrace();
        }
    }
}
