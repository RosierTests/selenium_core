package utilities;

/**
 * Created By: bsmith on 4/10/14.
 * Package: utilities
 * Description: Calculate the elapsed time for a single test.
 */
public class ElapsedTime {
    private static Long startTime;

    // Set the start time.
    public static void setStartTime() {
        startTime = System.nanoTime();
    }
    // Return the elapsed time in whole seconds.
    public static Integer getElapsedTime() {
        Integer elapsedTimeInSeconds = Math.round((float) ((int) ((System.nanoTime() - startTime) / 1000000) * .001));
        elapsedTimeInSeconds = elapsedTimeInSeconds > 1 ? elapsedTimeInSeconds : 1;
        return elapsedTimeInSeconds;
    }
}
