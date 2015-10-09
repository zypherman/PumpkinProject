/**
 * Created by John on 10/8/15.
 * Pumpkin thread is needed to maintain the count of a growing pumpkin
 */
public class Pumpkin extends Thread {

    private double timeGrowing;

    public Pumpkin() {
        this.timeGrowing = 0; //Set growing time to 0
    }

    public long getGrowTime() {
        RandomService randomService = new RandomService(1,2);
        randomService.getTime(); //Enter in the parameters for the pumpkin grow time

        return 0;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(getGrowTime()); //sleep for amount of growing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
