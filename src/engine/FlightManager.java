package engine;

import data.Airplane;
import data.Airport;
import data.MapField;

import java.util.ArrayList;

public class FlightManager implements Runnable {

    private boolean isRunning = false;

    private int speed = 1000;

    private final ArrayList<Flight> flights = new ArrayList<>();

    private final MapField map;

    public FlightManager(MapField map) {
        this.map = map;
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
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

            try {
                Thread.sleep(speed);
                for (Flight flight : flights) {
                    if (!flight.isRunning() && flight.isReadyToLaunch() && !flight.getDestinationAirport().isFilled()) {
                        Airplane airplane = flight.getStartAirport().getAvailableAirplane();
                        flight.getStartAirport().removeAirplane(airplane);
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

    public void setSpeed(int speed) {
        this.speed = speed;
        System.out.println("Speed set to :"+speed);
    }
}

