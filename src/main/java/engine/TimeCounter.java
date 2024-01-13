package engine;

public class TimeCounter {

    private int hours;
    private int minuts;

    public TimeCounter(int hours, int minuts) {
        this.hours = hours < 24 && hours >= 0 ? hours : 0;
        this.minuts = minuts < 60 && hours >= 0 ? hours : 0;
    }

    public void incrementMinuts() {
        minuts++;

        if (minuts >= 60) {
            minuts = 0;
            hours++;
            if (hours >= 24) {
                hours = 0;
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

    @Override
    public String toString() {
        return (hours < 10 ? "0" + hours : "" + hours) + " : " + (minuts < 10 ? "0" + minuts : "" + minuts);
    }
}
