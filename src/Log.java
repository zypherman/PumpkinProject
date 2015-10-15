import java.time.Instant;

public class Log {

    //Normal event log entry
    private Instant timestamp;
    private String logString;

    /**
     * Normal Log constructor
     * @param event Event
     */
    public Log(Event event) {
        this.logString = getEvent(event);
        this.timestamp = Instant.now();
    }

    /**
     * Delivered order log entry
     * @param time Instant
     * @param orderDetails String
     */
    public Log(Instant time, String orderDetails) {
        this.logString = getEvent(Event.ORDER_DELIVERED);
        this.logString += " " + orderDetails;
        this.timestamp = time;
    }

    /**
     * Determine the event that occured and return a string to represent it
     *
     * @param event Event
     */
    public String getEvent(Event event) {

        String eventString;
        switch (event) {
            case NEW_PLANT:
                eventString = "New Pumpkin was planted";
                break;
            case RIPE_PUMPKIN:
                eventString = "A pumpkin is ripe";
                break;
            case GATHER_PUMPKIN:
                eventString = "Pumpkin was gathered from the patch";
                break;
            case ORDER_PLACED:
                eventString = "Order was placed";
                break;
            case ORDER_DELIVERED:
                eventString = "Order was delivered";
                break;
            case PUMPKIN_COMPOSTED:
                eventString = "Pumpkin was composted";
                break;
            default:
                eventString = "Unlisted Event";
                break;
        }
        return eventString;
    }

    public String toString() {
        return logString + " at time: " + timestamp.toString();
    }
}
