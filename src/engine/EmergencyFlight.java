package engine;

import config.Config;
import data.Airplane;
import data.Airport;
import data.MapField;
import data.Position;
import util.ConversionUtility;
import util.ThreadUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class EmergencyFlight implements Runnable {

    private Thread thread;

    private int speed = Config.DEFAULT_SIMULATION_SPEED * 4;

    private volatile boolean isRunning = false;

    private volatile boolean isPaused = false;

    int currentPositionIndex;

    private Airplane airplane;

    private Position startPosition;

    private Airport emergencyAirport;

    private final HashMap<Position, ArrayList<Position>> emergencyPaths = new HashMap<>();

    public EmergencyFlight(MapField map) {

        ArrayList<Position> path1 = new ArrayList<>();
        emergencyPaths.put(map.getPosition(15, 15), path1);
        path1.add(map.getPosition(16, 14));
        path1.add(map.getPosition(17, 13));
        path1.add(map.getPosition(17, 12));
        path1.add(map.getPosition(17, 11));
        path1.add(map.getPosition(17, 10));
        path1.add(map.getPosition(17, 9));

        ArrayList<Position> path2 = new ArrayList<>();
        emergencyPaths.put(map.getPosition(13, 13), path2);
        path2.add(map.getPosition(14, 12));
        path2.add(map.getPosition(15, 11));
        path2.add(map.getPosition(16, 10));
        path2.add(map.getPosition(17, 9));

        ArrayList<Position> path3 = new ArrayList<>();
        emergencyPaths.put(map.getPosition(15, 3), path3);
        path3.add(map.getPosition(16, 4));
        path3.add(map.getPosition(17, 5));
        path3.add(map.getPosition(17, 6));
        path3.add(map.getPosition(17, 7));
        path3.add(map.getPosition(17, 8));
        path3.add(map.getPosition(17, 9));

        ArrayList<Position> path4 = new ArrayList<>();
        emergencyPaths.put(map.getPosition(21, 7), path4);
        path4.add(map.getPosition(20, 8));
        path4.add(map.getPosition(19, 9));
        path4.add(map.getPosition(18, 9));
        path4.add(map.getPosition(17, 9));

        ArrayList<Position> path5 = new ArrayList<>();
        emergencyPaths.put(map.getPosition(14, 11), path5);
        path5.add(map.getPosition(15, 11));
        path5.add(map.getPosition(16, 10));
        path5.add(map.getPosition(17, 9));
    }

    public void setEmergencyAirport(Airport emergencyAirport) {
        this.emergencyAirport = emergencyAirport;
    }

    public Airport getEmergencyAirport() {
        return emergencyAirport;
    }

    public void start(Airplane airplane, Position startPosition) {
        if (!isRunning && airplane != null && startPosition != null) {
            isRunning = true;
            this.startPosition = startPosition;
            emergencyAirport.decrementAvailableRunwayCount();
            this.airplane = airplane;
            airplane.setEmergency(true);
            thread = new Thread(this);
            thread.start();
        }
    }

    public void landing() {
        if (airplane != null) {
            currentPositionIndex = 0;
            airplane.changePosition(emergencyAirport);
            emergencyAirport.addAirplane(airplane);
            airplane.setAvailable(true);
            airplane.setEmergency(false);
            startPosition = null;
            airplane = null;
        }
    }

    public void sleep(int speed) {
        ThreadUtility.sleep(speed);
        ThreadUtility.waitWhilePaused(this);
    }

    @Override
    public void run() {

        sleep(speed);

        airplane.changePosition(startPosition);

        currentPositionIndex = 0;
        ArrayList<Position> path = emergencyPaths.get(startPosition);

        Position nextPosition = path.get(0);
        Position currentPosition;

        while (currentPositionIndex < path.size()) {
            if (airplane != null) {
                currentPosition = path.get(currentPositionIndex);
                if (currentPositionIndex < path.size() - 1) {
                    nextPosition = path.get(currentPositionIndex + 1);
                }
                double angle = ConversionUtility.calculateAngle(currentPosition, nextPosition);
                airplane.setAngle(angle);

                airplane.changePosition(currentPosition);
            }
            currentPositionIndex++;
            sleep(speed);
        }

        landing();
        isRunning = false;
    }

    public HashMap<Position, ArrayList<Position>> getEmergencyPaths() {
        return emergencyPaths;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void togglePause() {
        synchronized (this) {
            isPaused = !isPaused;
            if (!isPaused) {
                this.notifyAll();
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
