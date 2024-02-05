package util;

import engine.Flight;

import java.util.ArrayList;

public class SortUtility {

    public static ArrayList<Flight> sortFlights(ArrayList<Flight> flights) {
        ArrayList<Flight> runningFlights = new ArrayList<>();
        ArrayList<Flight> otherFlights = new ArrayList<>();

        for (Flight flight : flights) {
            if (flight.isRunning()) {
                otherFlights.add(flight);
            }
        }

        for (Flight flight : flights) {
            if (!flight.isRunning()) {
                runningFlights.add(flight);
            }
        }

        otherFlights.addAll(runningFlights);

        return otherFlights;
    }


}
