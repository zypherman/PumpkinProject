import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by John on 10/8/15.
 * This service will handle creating the random numbers for the program
 * Each object that needs a random number will create an instance of this class to call into to get random number
 * Must implement the guacian something random spread
 * Will need to adjust the class variables depending on what we need to figure that out
 */
public class RandomService {

    private long variable1;
    private long variable2;

    /**
     * Constructor for the random class
     *
     * @param i int
     * @param j int
     */
    public RandomService(int i, int j) {
        this.variable1 = i;
        this.variable2 = j;
    }

    public Duration getTime() {
        return Duration.ofSeconds(variable1);
    }

    public boolean flipCoin() {
        if (ThreadLocalRandom.current().nextInt(1, 2 + 1) == 1) return true;
        else return false;
    }
}
