package engine;

public class TimeCounter {

    private int daysCount = 1;
    private int hours;
    private int minuts;
    private boolean isPaused = false;

    public TimeCounter(int hours, int minuts) {
        this.hours = hours < 24 && hours >= 0 ? hours : 0;
        this.minuts = minuts < 60 && hours >= 0 ? hours : 0;
    }

    public void incrementMinuts() {
        if (!isPaused) {
            minuts++;
            if (minuts >= 60) {
                minuts = 0;
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

    public int getMinuts() {
        return minuts;
    }

    public void setMinuts(int minuts) {
        this.minuts = minuts;
    }

    public void togglerPaused() {
        isPaused = !isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public String toString() {
        return (hours < 10 ? "0" + hours : "" + hours) + " : " + (minuts < 10 ? "0" + minuts : "" + minuts);
    }

    public int getDaysCount() {
        return daysCount;
    }
}
