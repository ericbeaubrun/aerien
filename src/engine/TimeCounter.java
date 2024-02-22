package engine;

/**
 * The class manage simulation time with a simple counter.
 * It just provides a method to increment minutes.
 */
public class TimeCounter {

    private int day;

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
        this.day = daysCount;
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
                    day++;
                }
            }
        }
    }

    /**
     * Increments the counter by a specified number of minutes count.
     *
     * @param count the number of minutes to add to the counter
     */
    public String calculateTimeAfterCount(int count) {
        int day = this.day;
        int hours = this.hours;
        int minutes = this.minutes;

        // Calculate total
        int totalMinutes = day * 24 * 60 + hours * 60 + minutes;
        totalMinutes += count;

        // Convert to String
        day = totalMinutes / (24 * 60);
        totalMinutes %= 24 * 60;
        hours = totalMinutes / 60;
        minutes = totalMinutes % 60;
        return (hours < 10 ? "0" + hours : "" + hours) + " : " + (minutes < 10 ? "0" + minutes : "" + minutes);
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        return (hours < 10 ? "0" + hours : "" + hours) + " : " + (minutes < 10 ? "0" + minutes : "" + minutes);
    }
}
