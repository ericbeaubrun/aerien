package engine;

import config.Config;
import data.*;
import util.ConversionUtility;
import util.ThreadUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Flight implements Runnable {

    private String departureTime;

    private String arrivalTime;

    private int speed = Config.DEFAULT_SIMULATION_SPEED;

    /**
     * The thread that allows to run this Flight.
     */
    private Thread thread;

    /**
     * Defines the countdown before Airplane will takeoffs, when the count is negative that means
     * the Flight is delayed.
     */
    private int countdown;

    private volatile boolean isRunning = false;

    private volatile boolean isPaused = false;

    private int currentPositionIndex;

    private final MapField map;

    private volatile Airplane airplane = null;

    /**
     * The AirZone object where is currently placed the Airplane.
     */
    private AirZone currentAirZone;

    private Airport startAirport;

    private Airport destinationAirport;

    private final AirplaneDataCalculator dataCalculator;

    private final ArrayList<Position> path = new ArrayList<>();

    public Flight(MapField map, AirportManager airports, Position posAirportA, Position posAirportB) {
        this.map = map;
        this.startAirport = airports.findAirport(posAirportA);
        this.destinationAirport = airports.findAirport(posAirportB);
        dataCalculator = new AirplaneDataCalculator();

        resetCountdown();
    }

    public boolean willTakeoff(Airport airport) {
        return startAirport.equals(airport) && !isRunning;
    }

    public void addPosition(Position block) {
        path.add(block);
    }

    public void decrementCountdown() {
        countdown--;
    }

    public void reverseDirection() {
        Airport tmpAirport = startAirport;
        startAirport = destinationAirport;
        destinationAirport = tmpAirport;
        Collections.reverse(path);
    }

    public void takeoff() {
        currentPositionIndex = 0;
        AirZone airZone = map.findAirZone(airplane.getPosition());
        if (airZone != null) {
            currentAirZone = airZone;
            currentAirZone.enterInAirZone(airplane);
        }
        airplane.setAngle(ConversionUtility.calculateAngle(startAirport.getPosition(), path.get(1)));
        airplane.changePosition(startAirport);
        startAirport.removeAirplane(airplane);
        airplane.putOnRunway(false);
    }

    public void landing() {
        currentPositionIndex = 0;
        currentAirZone.leaveAirzone();
        airplane.changePosition(destinationAirport);
        destinationAirport.addAirplane(airplane);
        airplane.putOnRunway(true);
        airplane.setAvailable(true);
        airplane = null;
    }

    public void resetCountdown() {
        Random random = new Random();
        countdown = Config.MIN_COUNT_BEFORE_TAKEOFF + random.nextInt(Config.MAX_COUNT_BEFORE_TAKEOFF);
    }

    @Override
    public void run() {
        if (airplane != null) {

            isRunning = true;
            isPaused = false;

            takeoff();

            while (currentPositionIndex < path.size()) {
                ThreadUtility.sleep(speed);
                ThreadUtility.waitWhilePaused(this);

                nextPosition();
            }

            ThreadUtility.sleep(speed);
            ThreadUtility.waitWhilePaused(this);

            landing();

            resetCountdown();
            reverseDirection();

            isRunning = false;
        }
    }

    /**
     * Sets the AirZone based on the current Airplane position. Pauses the thread
     * if the zone to enter is not available.
     */
    private void updateAirZone() {
        AirZone airZone = map.findAirZone(getCurrentPosition());

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
    }

    /**
     * Moves the Airplane to the next Position in the Path and update the AirZone
     */
    private void nextPosition() {
        if (airplane != null) {

            updateAirZone();

            dataCalculator.recalculateAirplaneData(this);

            airplane.changePosition(getCurrentPosition());

            currentPositionIndex++;
        }
    }

    public void start() {
        if ((!isRunning || thread == null) && airplane != null) {
//            this.airplane = airplane;
            startAirport.incrementAvailableRunwayCount();
            destinationAirport.decrementAvailableRunwayCount();
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * @return true when the countdown before takeoff is negative.
     */
    public boolean isReadyToLaunch() {
        return countdown <= 0;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getAmountRemainingBlocks() {
        return path.size() - currentPositionIndex;
    }

    public int getAmountBlockPath() {
        return path.size();
    }

    public int getCurrentPositionIndex() {
        return currentPositionIndex;
    }

    public ArrayList<Position> getPath() {
        return new ArrayList<>(path);
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public int getCountdown() {
        return countdown;
    }

    public synchronized void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public synchronized Airplane getAirplane() {
        return airplane;
    }

    public Airport getStartAirport() {
        return startAirport;
    }

    public int getRemainingMinutes() {
        return (path.size() - currentPositionIndex) * 10;
    }

    public String getArrivalTime(TimeCounter time, int speed) {
        int i = getRemainingMinutes();
        return time.calculateTimeAfterCount(i);
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public Position getCurrentPosition() {
        return path.get(currentPositionIndex);
    }

    public void togglePause() {
        synchronized (this) {
            isPaused = !isPaused;
            if (!isPaused) {
                this.notifyAll();
            }
        }
    }

    public boolean isAssociatedToAirplane() {
        return airplane != null && !airplane.isAvailable();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDepartureTime(TimeCounter time) {
        departureTime = time.toString();
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return startAirport.getName() + " -> " + destinationAirport.getName() + "\n";
    }

}
