import java.time.Duration;
import java.time.Instant;

/**
 * Created by John on 10/8/15.
 * Orders are created at random intervals when people are sending them in
 */
public class Order {

    private int orderNumber;
    private Pumpkin pumpkin;
    private Instant orderTime;

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
        this.orderTime = Instant.now();
    }

    public Duration getOrderTime() {
        return Duration.between(orderTime, Instant.now());
    }

    //Add a pumpkin to the order to bring to the customer
    public void addPumpkin(Pumpkin pumpkin) {
        this.pumpkin = pumpkin;
    }

    public String toString() {
        return "Order:" + orderNumber + " Took: " +
                getOrderTime().toMillis() + " milliseconds to complete";
    }
}
