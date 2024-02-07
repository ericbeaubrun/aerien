package engine;

/**
 * The class manage simulation time with a simple counter.
 * It just provides a method to increment minutes.
 */
public class TimeCounter {

    private int daysCount;

    private int hours;

    private int minutes;

    private boolean isPaused = false;

    /**
     * @param hours     the hours
     * @param minutes   the minutes
     * @param daysCount the days count
     */
    public TimeCounter(int hours, int minutes, int daysCount) {
        this.hours = hours < 24 && hours >= 0 ? hours : 0;
        this.minutes = minutes < 60 && hours >= 0 ? hours : 0;
        this.daysCount = daysCount;
    }

    /**
     * Increments the counter by one minutes.
     */
    public void incrementMinutes() {
        if (!isPaused) {
            //Don't increment when paused
            minutes++;
            if (minutes >= 60) {
                minutes = 0;
                hours++;
                if (hours >= 24) {
                    hours = 0;
                    daysCount++;
                }
            }
        }
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getDaysCount() {
        return daysCount;
    }

    @Override
    public String toString() {
        return (hours < 10 ? "0" + hours : "" + hours) + " : " + (minutes < 10 ? "0" + minutes : "" + minutes);
    }
}
