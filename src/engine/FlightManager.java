package engine;

import config.Config;
import data.AirZone;
import data.Airplane;
import data.Airport;
import data.MapField;
import util.ThreadUtility;

import java.util.ArrayList;

public class FlightManager implements Runnable {

    private volatile boolean isRunning = false;

    private volatile boolean isPaused = false;

    private int speed = Config.DEFAULT_SIMULATION_SPEED;

    private final ArrayList<Flight> flights = new ArrayList<>();

    private final MapField map;

    public FlightManager(MapField map) {
        this.map = map;
    }

    @Override
    public void run() {

        isRunning = true;
        isPaused = false;

        while (isRunning) {

            ThreadUtility.sleep(speed);
            ThreadUtility.waitWhilePaused(this);

            for (Flight flight : flights) {
                if (!flight.isRunning() && flight.isReadyToLaunch() && flight.getDestinationAirport().hasAvailableRunway()) {
                    // Dans le cas ou le vol n'est pas lancé, qu'il est prêt à être lancé et que l'aeroport d'arrivé n'est pas plein

                    Airport airport = flight.getStartAirport();
                    AirZone airZone = map.findAirZone(airport.getPosition());

                    if (airZone != null && !airZone.isOccupied()) {
                        // Décollage dans le cas ou la zone qui contient l'aeroport de départ ne contient pas un avion

                        Airplane airplane = flight.getStartAirport().getFirstAvailableAirplane();
                        flight.getStartAirport().removeAirplane(airplane);
                        flight.removeAirplane(airplane);

                        if (airplane != null) {
                            flight.start(airplane);
                        }
                    }
                }
                flight.decrementCountBeforeTakeoff();
            }
        }
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public ArrayList<Flight> getFlights() {
        return new ArrayList<>(flights);
    }

    public int getSpeed() {
        return speed;
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

    public void setSpeed(int speed) {
        synchronized (this) {
            this.speed = speed;
            for (Flight flight : flights) {
                flight.setSpeed(speed);
            }
        }
    }
}

