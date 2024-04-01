package util;

import engine.EmergencyFlight;
import engine.Flight;
import engine.FlightManager;

/**
 * This class provides sleep and wait méthods for FlightManager and Flight Threads.
 */
public class ThreadUtility {

    /**
     * @param speed the time during sleeping.
     */
    public static void sleep(int speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wait while flightManager is paused.
     *
     * @param flightManager the flight manager to wait
     */
    public static void waitWhilePaused(FlightManager flightManager) {
        if (flightManager != null) {
            synchronized (flightManager) {
                while (flightManager.isPaused()) {
                    try {
                        flightManager.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Wait while flight is paused.
     *
     * @param flight the flight Thread to wait
     */
    public static void waitWhilePaused(Flight flight) {
        if (flight != null) {
            synchronized (flight) {
                while (flight.isPaused()) {
                    try {
                        flight.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Wait while emergency flight is paused.
     *
     * @param emergencyFlight the emergency flight Thread to wait
     */
    public static void waitWhilePaused(EmergencyFlight emergencyFlight) {
        if (emergencyFlight != null) {
            synchronized (emergencyFlight) {
                while (emergencyFlight.isPaused()) {
                    try {
                        emergencyFlight.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
