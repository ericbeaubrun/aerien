package engine;

import config.Config;
import data.Airplane;
import data.Airport;
import data.MapField;

import java.util.ArrayList;

public class Simulation {

    public FlightManager flightManager = new FlightManager();

    private MapField mapField;

    private final TimeCounter time;

    private final ArrayList<Airplane> airplanes = new ArrayList<>();
    private final AirportManager airports = new AirportManager();

    public void initMap() {
        mapField = new MapField(Config.COLUMNS, Config.ROWS, Config.BLOCK_SIZE);
    }

    public Simulation() {
        time = new TimeCounter(0, 0);
        initMap();
        SimulationInitializer.initSimulationObjects(mapField, airports, airplanes, flightManager);

        Thread thread = new Thread(flightManager);
        thread.start();
    }

    public MapField getMapField() {
        return mapField;
    }

    public ArrayList<Airplane> getAirplanes() {
        return new ArrayList<>(airplanes);
    }

    public ArrayList<Airport> getAirports() {
        return airports.getAirports();
    }

    public ArrayList<Flight> getFlights() {
        return flightManager.getFlights();
    }

    public TimeCounter getTime() {
        return time;
    }
}
