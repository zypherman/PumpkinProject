/**
 * Created by John on 10/8/15.
 * Orders are created at random intervals when people are sending them in
 */
public class Order {

    private int orderNumber;
    private Pumpkin pumpkin;
    private long orderTime;

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
        this.orderTime = System.currentTimeMillis();
    }

    //Add a pumpkin to the order to bring to the cusotmer
    public void addPumpkin(Pumpkin pumpkin) {
        this.pumpkin = pumpkin;
    }

    public String toString() {
        return "Order:" + orderNumber;
    }
}
