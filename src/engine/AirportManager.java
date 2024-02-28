package engine;

import data.Airport;
import data.Position;

import java.util.ArrayList;
import java.util.Collections;

public class AirportManager {

    private final ArrayList<Airport> airports = new ArrayList<>();

    /**
     * Find the airport presents in the given Position.
     *
     * @param position the position where we want to find the airport.
     * @return the Airport Object or null if there is no airport.
     */
    public Airport findAirport(Position position) {
        if (position != null) {
            for (Airport airport : airports) {
                if (airport.hasSamePosition(position)) {
                    return airport;
                }
            }
        }
        return null;
    }

    /**
     * @return a random airport with 1 available runway at least.
     */
    public Airport getRandomAvailableAirport() {
        ArrayList<Airport> list = getAirports();
        Collections.shuffle(list);

        for (Airport airport : list) {
            if (!airport.isAvailableToSpawn()) {
                return airport;
            }
        }
        return null;
    }

    public void add(Airport airport) {
        airports.add(airport);
    }

    public ArrayList<Airport> getAirports() {
        return new ArrayList<>(airports);
    }

}
