package engine;

import data.AirZone;
import data.Airplane;
import data.Airport;
import data.MapField;
import util.ThreadUtility;

import java.util.ArrayList;

public class FlightManager implements Runnable {

    private volatile boolean isRunning = false;

    private volatile boolean isPaused = false;

    private int speed = 800;

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
            ThreadUtility.wait(this);

            for (Flight flight : flights) {
                if (!flight.isRunning() && flight.isReadyToLaunch() && flight.getDestinationAirport().hasAvailableRunway()) {

                    Airport airport = flight.getStartAirport();
                    AirZone airZone = map.findAirZone(airport.getPosition());

                    if (airZone != null && !airZone.isOccupied()) {
                        Airplane airplane = flight.getStartAirport().getFirstAvailableAirplane();
                        flight.getStartAirport().removeAirplane(airplane);
                        flight.removeAirplane(airplane);

                        if (airplane != null) {
                            flight.startThread(airplane);
                        }
                    }
                }
                flight.decrementCountBeforeTakeoff();
            }
        }
    }

//            try {
//                Thread.sleep(2000);
//
//                for (int i = 0; i < flights.size(); i++) {
////                    Flight flight = flights.get(i);
////                    System.out.println(i);
////                    System.out.println(flight.toString());
//                }
//
//                //flight 1
//                Flight flight1 = flights.get(16);
//
//                Airplane airplane1 = flight1.getStartAirport().getAvailableAirplane();
//                flight1.getStartAirport().removeAirplane(airplane1);
//
//                if (airplane1 != null) {
//                    flight1.startThread(airplane1);
//                }
//
//                //flight 2
//                Flight flight2 = flights.get(13);
//
//                Airplane airplane2 = flight2.getStartAirport().getAvailableAirplane();
//                flight2.getStartAirport().removeAirplane(airplane2);
//
//                if (airplane2 != null) {
//                    flight2.startThread(airplane2);
//                }
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            ==================================================================================================================================
//    public Flight findFlight(Airport startAirport, Airport destinationAirport) {
//        for (Flight flight : flights) {
//            if (flight.getStartAirport().equals(startAirport) && flight.getDestinationAirport().equals(destinationAirport)) {
//                return flight;
//            } else if (flight.getDestinationAirport().equals(startAirport) && flight.getStartAirport().equals(destinationAirport)) {
//                // Dans le cas du vol dans le sens inverse
//                flight.reverseDirection();
//                return flight;
//            }
//        }
//        return null;
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

//    public void togglePause() {
//        isPaused = !isPaused;
//    }

    public void togglePause() {
        synchronized (this) {
            isPaused = !isPaused;
            if (!isPaused) {
                this.notifyAll();
            }
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        for (Flight flight : flights) {
            flight.setSpeed(speed);
        }
    }
}

