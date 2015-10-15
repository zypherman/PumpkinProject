import java.time.Duration;
import java.util.Random;

/**
 * Created by John on 10/8/15.
 * This service will handle creating the random numbers for the program
 * Each object that needs a random number will create an instance of this class to call into to get random number
 * Must implement the nextGaussian random spread
 * Will need to adjust the class variables depending on what we need to figure that out
 */
public class RandomService {

    private int mean;
    private int distribution;
    Random random;

    /**
     * Constructor for the random class
     *
     * @param mean int
     * @param distribution int
     */
    public RandomService(int mean, int distribution) {
        this.mean = mean;
        this.distribution = distribution;
        random = new Random();
    }

    /**
     * Creates a random time within the mean and distribution desired
     * @return Duration
     */
    public Duration getTime() {
        Duration time = Duration.ofSeconds(Math.round(((random.nextGaussian() * distribution) + mean)));
        if(time.isNegative()) { // If we got a negative recursivly call this method again
            getTime();
        }
        return time;
    }

}
