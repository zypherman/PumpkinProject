/**
 * Created by John on 10/8/15.
 * Logging service class to help files write to the log to output all the events
 */
public class LoggingService {
    //Pull in our document writer class here from project 0

    //Maybe we can cache the stuff and write it every so often

    private String objectName;

    public LoggingService(String objectName) {
        this.objectName = objectName;
    }

    public void logEvent(Event event, long timestamp) {
        switch (event) {
            case NEW_PLANT:
                System.out.println("New Pumpkin was planted: " + timestamp);
                break;
            case RIPE_PUMPKIN:
                System.out.println("A pumpkin is ripe: " + timestamp);
                break;
            case GATHER_PUMPKIN:
                System.out.println("Pumpkin was gathered from the patch: " + timestamp);
                break;
            case ORDER_PLACED:
                System.out.println("Order was placed: " + timestamp);
                break;
            case ORDER_DELIVERED:
                System.out.println("Order was delivered: " + timestamp);
                break;
            default:
                System.out.println("Unlisted Event: " + timestamp);
                break;

        }
    }

    public void writeToConsole(String event) {
        System.out.println(objectName + " did " + event);
    }
}
