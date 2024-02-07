package engine;

import config.Config;
import data.*;
import util.ConversionUtility;
import util.ThreadUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Flight implements Runnable {

    private int speed = Config.DEFAULT_SIMULATION_SPEED;

    private Thread thread;

    private int countBeforeTakeoff;

    private volatile boolean isRunning = false;

    private volatile boolean isPaused = false;

    private int currentBlockIndex;

    private final MapField mapField;

    private Airplane airplane = null;

    private AirZone currentAirZone;

    private Airport startAirport;

    private Airport destinationAirport;

    private final ArrayList<Position> path = new ArrayList<>();

    public Flight(MapField mapField, AirportManager airports, Position posAirportA, Position posAirportB) {
        this.mapField = mapField;
        this.startAirport = airports.findAirport(posAirportA.getColumn(), posAirportA.getRow());
        this.destinationAirport = airports.findAirport(posAirportB.getColumn(), posAirportB.getRow());

        resetCountBeforeTakeoff();
    }

    public boolean willLeave(Airport airport) {
        return startAirport.equals(airport) && !isRunning;
    }

    public int getCountBeforeTakeoff() {
        return countBeforeTakeoff;
    }

    public void addNextBlock(Position block) {
        path.add(block);
    }

    public void decrementCountBeforeTakeoff() {
        countBeforeTakeoff--;
    }

    public void reverseDirection() {
        Airport tmp = startAirport;
        startAirport = destinationAirport;
        destinationAirport = tmp;
        Collections.reverse(path);
    }

    public void takeoff() {
        AirZone airZone = mapField.findAirZone(airplane.getPosition());
        if (airZone != null) {
            currentAirZone = airZone;
            currentAirZone.enterInAirZone(airplane);
        }
        airplane.setAngle(ConversionUtility.calculateAngle(startAirport.getPosition(), path.get(1)));
        airplane.setPosition(startAirport);
        startAirport.removeAirplane(airplane);
        airplane.putOnRunway(false);
    }

    public void landing() {
        currentAirZone.leaveAirzone();
        airplane.setPosition(destinationAirport);
        destinationAirport.addAirplane(airplane);
        airplane.putOnRunway(true);
        airplane = null;
    }

    public void resetCountBeforeTakeoff() {
        Random random = new Random();
        countBeforeTakeoff = 5 + random.nextInt(40);
    }

    @Override
    public void run() {
        if (airplane != null) {

            isRunning = true;

            isPaused = false;

            takeoff();

            currentBlockIndex = 0;
            while (currentBlockIndex < path.size()) {

                Position currentPosition = path.get(currentBlockIndex);
                AirZone airZone = mapField.findAirZone(currentPosition);

                ThreadUtility.sleep(speed);
                ThreadUtility.waitWhilePaused(this);

                if (airZone != null) {
                    if (currentAirZone == null) {
                        currentAirZone = airZone;
                        currentAirZone.enterInAirZone(airplane);

                    } else if (!airZone.equals(currentAirZone)) {
                        currentAirZone.leaveAirzone();
                        currentAirZone = airZone;
                        currentAirZone.enterInAirZone(airplane);
                    }
                }

                airplane.setPosition(currentPosition);

                // Calculer l'angle de l'avion
                Position otherPosition = currentBlockIndex < path.size() - 4 ? path.get(currentBlockIndex + 4) : destinationAirport.getPosition();

                double angle = ConversionUtility.calculateAngle(currentPosition, otherPosition);
                airplane.setAngle(angle);

                currentBlockIndex++;
            }

            ThreadUtility.sleep(speed);
            ThreadUtility.waitWhilePaused(this);

            landing();

            resetCountBeforeTakeoff();

            reverseDirection();

            isRunning = false;
        }
    }

    public synchronized void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    @Override
    public String toString() {
        return startAirport.getName() + " -> " + destinationAirport.getName() + "\n";
    }

    public void start(Airplane airplane) {
        if ((!isRunning || thread == null) && airplane != null) {
            this.airplane = airplane;
            startAirport.incrementAvailableRunwayCount();
            destinationAirport.decrementAvailableRunwayCount();
            thread = new Thread(this);
            thread.start();
        }
    }

    public void removeAirplane(Airplane airplane) {
        startAirport.removeAirplane(airplane);
    }

    public boolean isReadyToLaunch() {
        return countBeforeTakeoff <= 0;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getAmountBlockPath() {
        return path.size();
    }

    public int getAmountBlockRemainingPath() {
        return path.size() - currentBlockIndex;
    }

    public int getCurrentBlockIndex() {
        return currentBlockIndex;
    }

    public ArrayList<Position> getPath() {
        return new ArrayList<>(path);
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public Airport getStartAirport() {
        return startAirport;
    }

    public void togglePause() {
        synchronized (this) {
            isPaused = !isPaused;
            if (!isPaused) {
                this.notifyAll();
            }
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
