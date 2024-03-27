package data;

import config.Config;

import java.util.Random;

public class Airplane extends Entity {

    private volatile boolean isAvailable = true;

    private int fuel;

    private int speed;

    private int maxFuel;

    private int maxSpeed;

    private double angle;

    private final String reference;

    private boolean isOnRunway;

    private boolean isWaiting = false;

    private boolean isEmergency = false;

    private int totalDistanceTraveledCount;


    public Airplane(int x, int y, String reference, int maxFuel, int maxSpeed, int totalDistanceTraveledCount) {
        super(x, y);
        this.angle = 0;
        this.reference = reference;
        this.totalDistanceTraveledCount = totalDistanceTraveledCount;
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

    public synchronized boolean isAvailable() {
        return isAvailable;
    }

    public synchronized void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getTotalDistanceTraveledCount() {
        return totalDistanceTraveledCount;
    }

    public void incrementTotalDistanceTraveled(int count) {
        totalDistanceTraveledCount += count;
    }

    public void fillFuel() {
        fuel = maxFuel;
    }

    @Override
    public String toString() {
        return "ref='" + reference + "'";
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }
}
