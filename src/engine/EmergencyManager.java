package engine;

import config.Config;
import data.Airplane;
import data.Airport;
import data.MapField;
import data.Position;
import util.ThreadUtility;

import java.util.ArrayList;
import java.util.HashMap;

public class EmergencyManager implements Runnable {

//    private MapField map;

    private Thread thread;

    private int speed = Config.DEFAULT_SIMULATION_SPEED * 4;

    private volatile boolean isRunning = false;

    private volatile boolean isPaused = false;

//    private final AirplaneDataCalculator dataCalculator;

    int currentPositionIndex;

    private Airplane airplane;

    private Position startPosition;

    private Airport emergencyAirport;

    private final HashMap<Position, ArrayList<Position>> emergencyPaths = new HashMap<>();

    public EmergencyManager(MapField map) {
        ArrayList<Position> emergencyPath1 = new ArrayList<>();
        emergencyPath1.add(map.getPosition(16, 14));
        emergencyPath1.add(map.getPosition(17, 13));
        emergencyPath1.add(map.getPosition(17, 12));
        emergencyPath1.add(map.getPosition(17, 11));
        emergencyPath1.add(map.getPosition(17, 10));
        emergencyPath1.add(map.getPosition(17, 9));
        emergencyPaths.put(map.getPosition(15, 15), emergencyPath1);

        ArrayList<Position> emergencyPath2 = new ArrayList<>();
        emergencyPath2.add(map.getPosition(14, 12));
        emergencyPath2.add(map.getPosition(15, 11));
        emergencyPath2.add(map.getPosition(16, 10));
        emergencyPath2.add(map.getPosition(17, 9));
        emergencyPaths.put(map.getPosition(13, 13), emergencyPath2);

        ArrayList<Position> emergencyPath3 = new ArrayList<>();
        emergencyPath3.add(map.getPosition(16, 4));
        emergencyPath3.add(map.getPosition(17, 5));
        emergencyPath3.add(map.getPosition(17, 6));
        emergencyPath3.add(map.getPosition(17, 7));
        emergencyPath3.add(map.getPosition(17, 8));
        emergencyPath3.add(map.getPosition(17, 9));
        emergencyPaths.put(map.getPosition(15, 13), emergencyPath3);

        ArrayList<Position> emergencyPath4 = new ArrayList<>();
        emergencyPath4.add(map.getPosition(20, 8));
        emergencyPath4.add(map.getPosition(19, 9));
        emergencyPath4.add(map.getPosition(18, 9));
        emergencyPath4.add(map.getPosition(17, 9));
        emergencyPaths.put(map.getPosition(21, 7), emergencyPath4);

        ArrayList<Position> emergencyPath5 = new ArrayList<>();
        emergencyPath5.add(map.getPosition(15, 11));
        emergencyPath5.add(map.getPosition(16, 10));
        emergencyPath5.add(map.getPosition(17, 9));
        emergencyPaths.put(map.getPosition(14, 11), emergencyPath5);

//        dataCalculator = new AirplaneDataCalculator();
    }

    public void setEmergencyAirport(Airport emergencyAirport) {
        this.emergencyAirport = emergencyAirport;
    }

    public Airport getEmergencyAirport() {
        return emergencyAirport;
    }

    public void start(Airplane airplane, Position startPosition) {
        if ((!isRunning || thread == null) && airplane != null && startPosition != null) {
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
            airplane.putOnRunway(true);
            airplane.setAvailable(true);
            airplane.setEmergency(false);
            startPosition = null;
            airplane = null;
        }
    }

    @Override
    public void run() {
        isRunning = true;

        currentPositionIndex = 0;
        ArrayList<Position> path = emergencyPaths.get(startPosition);

        while (currentPositionIndex < path.size()) {
            ThreadUtility.sleep(speed);
            ThreadUtility.waitWhilePaused(this);

            Position currentPosition = currentPositionIndex < path.size() ? path.get(currentPositionIndex) : null;
            if (currentPosition != null) {
                airplane.changePosition(currentPosition);
            }
            currentPositionIndex++;
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
