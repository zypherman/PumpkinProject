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

    public void writeToConsole(String event) {
        System.out.println(objectName + " did " + event);
    }
}
