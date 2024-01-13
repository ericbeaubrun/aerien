package engine;

import data.Airport;

import java.util.ArrayList;
import java.util.Collections;

public class AirportManager {

    private final ArrayList<Airport> airports = new ArrayList<>();

    public Airport findAiport(int column, int row) {
        for (Airport airport : airports) {
            if (airport.getColumn() == column && airport.getRow() == row) {
                return airport;
            }
        }
        return null;
    }

    public Airport getRandomSpawnAirport() {
        ArrayList<Airport> list = getAirports();
        Collections.shuffle(list);

        for (Airport airport : list) {
            if (!airport.isAvailableSpawn()) {
                return airport;
            }
        }
        return null;
    }

    public void add(Airport airport) {
        airports.add(airport);
    }

    public int getAmountAirport() {
        return airports.size();
    }

    public ArrayList<Airport> getAirports() {
        return new ArrayList<>(airports);
    }

}
