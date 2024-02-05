package engine;

public class TimeCounter {

    private int daysCount = 1;
    private int hours;
    private int minutes;
    private boolean isPaused = false;

    public TimeCounter(int hours, int minuts) {
        this.hours = hours < 24 && hours >= 0 ? hours : 0;
        this.minutes = minuts < 60 && hours >= 0 ? hours : 0;
    }

    public void incrementMinutes() {
        if (!isPaused) {
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

    @Override
    public String toString() {
        return (hours < 10 ? "0" + hours : "" + hours) + " : " + (minutes < 10 ? "0" + minutes : "" + minutes);
    }

    public int getDaysCount() {
        return daysCount;
    }
}
