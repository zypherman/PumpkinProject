import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * FileIO class to handle writing to our output file
 * Might have to synchronize parts of this depending on how we implement the logging service
 */
public class FileIO implements Runnable {

    //Hold the file
    String outputFileName = "output.txt";
    private boolean consoleNoise;
    Duration logInterval;
    private Instant startTime;
    //System independent new line character
    String newLineChar = System.getProperty("line.separator");
    private LinkedBlockingQueue<Log> log;
    int runtime;

    public FileIO(LinkedBlockingQueue<Log> log, boolean consoleNoise) {
        this.logInterval = Duration.ofSeconds(PropertyLoader.getInstance().getValue("loginterval"));
        this.runtime = PropertyLoader.getInstance().getValue("runtime");
        this.log = log;
        this.startTime = Instant.now();
        this.consoleNoise = consoleNoise;
    }

    /**
     * Will check if there is enough data to write to the file
     * If we have more than 20 things to write then write the whole log to disk
     */
    private void checkLog() throws InterruptedException {
        if (log.size() > 20) ingestLog();
    }

    /**
     * Will take all items from the log queue and send them to get written to disk
     */
    private void ingestLog() throws InterruptedException {
        LinkedList<Log> logs = new LinkedList<Log>();

        // Get all log entries
        while (!log.isEmpty()) {
            if(consoleNoise) {
                System.out.println(log.peek().toString());
            }
            logs.add(log.take());
        }

        saveDataToFile(logs);
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
        if ((Duration.between(startTime, Instant.now()).toMinutes() > runtime) && log.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Will handle writing output from out program to a file of our choice
     *
     * @param data data to save, write out
     */
    public void saveDataToFile(LinkedList<Log> data) {

        try {
            //Create our file writer object
            FileWriter fileWriter = new FileWriter(outputFileName, true); //Append mode
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //Write data
            while (!data.isEmpty()) {
                bufferedWriter.write(data.remove().toString() + newLineChar); //Write next Log to the file and adds new line charecter to the end
            }

            //Close our resources
            bufferedWriter.close();
            fileWriter.close();

        } catch (Exception e) {
            //We could potentially have a couple different types of exceptions so just catch a generic exception and dump the stack
            e.printStackTrace();
        }
    }

    /**
     * Need to watch the log queue and when it gets to a certain amount
     * Dump it into the output file
     */
    @Override
    public void run() {

        try {

            while (checkTime()) {
                //Determines how often we check for new writes
                Thread.sleep(logInterval.toMillis());
                checkLog();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

