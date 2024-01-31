package data;

public class Airplane extends Entity {


    public Airplane(int x, int y) {
        super(x, y);
        this.isOnTrail = false;
    }

    public Airplane(int x, int y, String reference, int maxFuel, int maxSpeed, int comFrequency, String imagePath) {
        super(x, y);
        this.altitude = 0;
        this.angle = 0;
        this.inclinaison = 0;

        this.reference = reference;

        this.imagePath = imagePath;
        this.communicationFrequency = comFrequency;

        this.fuel = maxFuel;
        this.maxFuel = maxFuel;

        this.speed = 0;
        this.maxSpeed = maxSpeed;

        this.isOnTrail = false;
    }


    private boolean isOnTrail;

    private String imagePath;

    private int altitude;

    private double angle;

    private int inclinaison;

    private int fuel;
    private int maxFuel;

    private int speed;
    private int maxSpeed;

    private String reference;

    private String direction;

    private float communicationFrequency;

    public void setOnTrail(boolean onTrail) {
        isOnTrail = onTrail;
    }

    public boolean isOnTrail() {
        return isOnTrail;
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

    public float getCommunicationFrequency() {
        return communicationFrequency;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
