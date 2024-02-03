package util;

import engine.Flight;
import engine.FlightManager;

public class ThreadUtility {
    public static void sleep(int speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void wait(FlightManager f) {
        synchronized (f) {
            while (f.isPaused()) {
                try {
                    f.wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void wait(Flight f) {
        synchronized (f) {
            while (f.isPaused()) {
                try {
                    f.wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}