package data;

public class Airplane extends Entity {

    private int fuel;

    private int speed;

    private int maxFuel;

    private int maxSpeed;

    private double angle;

    private int altitude;

    private int inclinaison;

    private String reference;

    private String direction;

    private boolean isOnRunway;

    private boolean isWaiting = false;

    private float communicationFrequency;

    public Airplane(int x, int y) {
        super(x, y);
        this.isOnRunway = false;
    }

    public Airplane(int x, int y, String reference, int maxFuel, int maxSpeed, int comFrequency) {
        super(x, y);
        this.altitude = 0;
        this.angle = 0;
        this.inclinaison = 0;

        this.reference = reference;

        this.communicationFrequency = comFrequency;

        this.fuel = maxFuel;
        this.maxFuel = maxFuel;

        this.speed = 0;
        this.maxSpeed = maxSpeed;

        this.isOnRunway = false;
    }

    public void putOnRunway(boolean isOnRunway) {
        this.isOnRunway = isOnRunway;
    }

    public boolean isOnRunway() {
        return isOnRunway;
    }

    public double getAngle() {
        return angle;
    }

    public int getFuel() {
        return fuel;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public String getReference() {
        return reference;
    }

    public String getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }
}
