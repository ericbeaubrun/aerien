package engine;

import config.Config;
import data.*;
import util.ThreadUtility;

import java.util.ArrayList;

public class FlightManager implements Runnable {

    private volatile boolean isRunning = false;

    private volatile boolean isPaused = false;

    private int speed = Config.DEFAULT_SIMULATION_SPEED;

    private final ArrayList<Flight> flights = new ArrayList<>();

    private final MapField map;

    private final TimeCounter time;

    private final EmergencyFlight emergencyFlight;

    public FlightManager(MapField map, TimeCounter time) {
        this.time = time;
        this.map = map;
        emergencyFlight = new EmergencyFlight(map);
    }

    @Override
    public void run() {

        isRunning = true;
        isPaused = false;

        while (isRunning) {

            ThreadUtility.sleep(speed);
            ThreadUtility.waitWhilePaused(this);

            for (Flight flight : flights) {
                // For all running Flights
                boolean hasFoundAirplane = flight.getAirplane() != null;

                if (!flight.isRunning()) {
                    Airport airport = flight.getStartAirport();

                    // For all airplanes in the departure airport
                    for (Airplane airplane : airport.getAirplanes()) {

                        if (airplane != null) {

                            // Refill fuel only when < 25%
                            if (airplane.getFuel() < airplane.getMaxFuel() / 4) {
                                airplane.fillFuel();
                            }

                            // When the flight is not running and is ready to takeoff (with destination airport not full)

                            AirZone airZone = map.findAirZone(airport.getPosition());
                            if (flight.getAirplane() == null && airplane.isAvailable()) {
                                airplane.setAvailable(false);
                                flight.setAirplane(airplane);
                                hasFoundAirplane = true;
                            }

                            airplane = flight.getAirplane();

                            if (airplane != null && flight.isReadyToLaunch() && flight.getDestinationAirport().hasAvailableRunway()
                                    && !airplane.isAvailable() && airZone != null && !airZone.isOccupied()) {

                                flight.getStartAirport().removeAirplane(airplane);
                                flight.setDepartureTime(time);
                                flight.start();
                            }

                            if (hasFoundAirplane) {
                                break;
                            }
                        }
                    }
                } else {
                    // Manage emergencies
                    for (Position position : emergencyFlight.getEmergencyPaths().keySet()) {

                        Position currentPosition = flight.getCurrentPosition();

                        if (position.equals(currentPosition) && !emergencyFlight.isRunning() &&
                                ((emergencyFlight.getEmergencyAirport().hasAvailableRunway() || Config.allowAlwaysEmergency))
                                && Config.emergencyEnabled) {

                            Airplane airplane = flight.cancelFlight();

                            AirZone airZone = map.findAirZone(currentPosition);
                            if (airZone != null) {
                                airZone.leaveAirzone();
                            }

                            emergencyFlight.start(airplane, position);
                            break;
                        }

                    }
                }
                flight.decrementCountdown();
            }
        }
    }

    //    public void setTimesFlight(Flight flight) {
    //        flight.setDepartureTime(time.toString()); // à modifier
    //        flight.setArrivalTime(flight.calculateArrivalTime(time, flight.getSpeed())); // à modifier
    //    }

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
            emergencyFlight.togglePause();
        }
    }

    public void setSpeed(int speed) {
        synchronized (this) {
            this.speed = speed;
            for (Flight flight : flights) {
                flight.setSpeed(speed);
            }
            emergencyFlight.setSpeed(speed);
        }
    }

    public void setEmergencyAirport(Airport airport) {
        emergencyFlight.setEmergencyAirport(airport);
    }
}

