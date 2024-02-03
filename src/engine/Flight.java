package engine;

import data.*;
import util.ConversionUtility;
import util.ThreadUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Flight implements Runnable {

    private int speed = 800;

    private Thread thread = null;

    private int countBeforeTakeoff;

    private volatile boolean isRunning = false;

    private volatile boolean isPaused = false;

    private int currentIndex;

    private final MapField mapField;

    private Airplane airplane = null;

    private AirZone currentAirZone;

    private Airport startAirport;

    private Airport destinationAirport;

    private final ArrayList<Position> path = new ArrayList<>();

    public Flight(MapField mapField, AirportManager airports, Position posAirportA, Position posAirportB) {
        this.mapField = mapField;
        this.startAirport = airports.findAiport(posAirportA.getColumn(), posAirportA.getRow());
        this.destinationAirport = airports.findAiport(posAirportB.getColumn(), posAirportB.getRow());

        Random random = new Random();
        countBeforeTakeoff = 5 + random.nextInt(40);
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

    public void removeAirplane(Airplane airplane) {
        startAirport.removeAirplane(airplane);
    }

    public void takeoffAirplane() {
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

    public void landingAirplane() {
        currentAirZone.leaveAirzone();
        airplane.setPosition(destinationAirport);
        destinationAirport.addAirplane(airplane);
        airplane.putOnRunway(true);
        airplane = null;
        thread = null;
    }

    @Override
    public void run() {
        if (airplane != null) {

            isRunning = true;
            isPaused = false;


            takeoffAirplane();

            currentIndex = 0;
            while (currentIndex < path.size()) {

                Position currentPosition = path.get(currentIndex);
                AirZone airZone = mapField.findAirZone(currentPosition);

                ThreadUtility.sleep(speed);
                ThreadUtility.wait(this);

                if (airZone != null && currentAirZone == null) {
                    currentAirZone = airZone;
                    currentAirZone.enterInAirZone(airplane);

                } else if ((airZone != null && !airZone.equals(currentAirZone))) {
                    currentAirZone.leaveAirzone();
                    currentAirZone = airZone;
                    currentAirZone.enterInAirZone(airplane);
                }

                airplane.setPosition(currentPosition);

                // Calculer l'angle de l'avion
                Position otherPosition = currentIndex < path.size() - 4 ? path.get(currentIndex + 4) : destinationAirport.getPosition();

                double angle = ConversionUtility.calculateAngle(currentPosition, otherPosition);
                airplane.setAngle(angle);

                currentIndex++;
            }

            ThreadUtility.sleep(speed);
            ThreadUtility.wait(this);

            landingAirplane();

            Random random = new Random();
            countBeforeTakeoff = 5 + random.nextInt(20);

            reverseDirection();

            isRunning = false;
        }
    }

    public synchronized void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(startAirport.getName());
        s.append(" -> ");
        s.append(destinationAirport.getName());
        s.append("\n");

//        for (Position position : path) {
//            s.append(position.toString());
//            s.append("\n");
//        }

        return s.toString();
    }

    public void startThread(Airplane airplane) {
        if (!isRunning || thread == null) {
            this.airplane = airplane;
            startAirport.incrementAvailableRunwayCount();
            destinationAirport.decrementAvailableRunwayCount();
            thread = new Thread(this);
            thread.start();
        }
    }

    public void removeLastBlock() {
        if (!path.isEmpty()) {
            path.remove(path.size() - 1);
        }
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
        return path.size() - currentIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
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
