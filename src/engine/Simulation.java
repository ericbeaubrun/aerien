package engine;

import config.Config;
import data.Airplane;
import data.Airport;
import data.MapField;

import java.util.ArrayList;

/**
 * This class allows to do all processes in the Simulation.
 * It contains the Map, Airplanes, Flights, and Airports.
 */
public class Simulation {

    private final TimeCounter time;

    private final MapField map;

    private final FlightManager flightManager;

    private final AirportManager airportManager;

    private final ArrayList<Airplane> airplanes;

    public Simulation() {
        SimulationInitializer initializer = new SimulationInitializer();

        time = new TimeCounter(0, 0, 1);

        map = initializer.initMap();

        initializer.initReliefs(map);

        airportManager = initializer.initAirports();

        flightManager = initializer.initFlights(map, time, airportManager);

        airplanes = initializer.initAirplanes(airportManager);

        initializer.terminateInitialisation();
    }

    /**
     * Start the Thread of the Simulation.
     */
    public void start() {
        Thread thread = new Thread(flightManager);
        thread.start();
    }

    public void decreaseSpeed() {
        int currentSpeed = flightManager.getSpeed();
        if (currentSpeed < Config.MAX_SIMULATION_SPEED) {
            flightManager.setSpeed(currentSpeed + Config.STEP_SIMULATION_SPEED);
        }
    }

    public void increaseSpeed() {
        int currentSpeed = flightManager.getSpeed();
        if (currentSpeed > Config.MIN_SIMULATION_SPEED) {
            flightManager.setSpeed(currentSpeed - Config.STEP_SIMULATION_SPEED);
        }
    }

    public ArrayList<Airplane> getAirplanes() {
        return new ArrayList<>(airplanes);
    }

    public ArrayList<Airport> getAirports() {
        return airportManager.getAirports();
    }

    public ArrayList<Flight> getFlights() {
        return flightManager.getFlights();
    }

    public TimeCounter getTime() {
        return time;
    }

    public MapField getMap() {
        return map;
    }

    public int getSpeed() {
        return flightManager.getSpeed();
    }

    /**
     * Toggle pause for all thread in the engine side of simulation (FlightManager and all Flights).
     */
    public void togglePause() {
        for (Flight flight : flightManager.getFlights()) {
            flight.togglePause();
        }
        flightManager.togglePause();
    }
}

