package engine;

import data.Airplane;
import data.Airport;

import java.util.ArrayList;

public class FlightManager implements Runnable {

    private boolean isRunning = false;

    private final ArrayList<Flight> flights = new ArrayList<>();

    public Flight findFlight(Airport startAirport, Airport destinationAirport) {
        for (Flight flight : flights) {
            if (flight.getStartAirport().equals(startAirport) && flight.getDestinationAirport().equals(destinationAirport)) {
                return flight;
            } else if (flight.getDestinationAirport().equals(startAirport) && flight.getStartAirport().equals(destinationAirport)) {
                //Dans le cas du vol dans le sens inverse
                flight.reverseDirection();
                return flight;
            }
        }
        return null;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public ArrayList<Flight> getFlights() {
        return new ArrayList<>(flights);
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            try {
                Thread.sleep(1000);
                for (Flight flight : flights) {
                    if (!flight.isRunning() && flight.isReadyToLauch() && !flight.getDestinationAirport().isFilled()) {
                        Airplane airplane = flight.getStartAirport().getAvailableAirplane();
//                        flight.getStartAirport().removeAirplane(airplane);
                        flight.removeAirplane(airplane);

                        if (airplane != null) {
                            flight.startThread(airplane);
                        }
                    }
                    flight.decrementCountBeforeTakeoff();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

