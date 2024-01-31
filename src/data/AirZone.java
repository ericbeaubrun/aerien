package data;


import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;


public class AirZone {

    //    private Lock mutex = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(1, true);

    private Airplane airplane;

    private final ArrayList<Position> positions = new ArrayList<>();

    // Représente les deux points permettant de dessiner un rectangle
    public AirZone() {

    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public ArrayList<Position> getPositions() {
        return new ArrayList<>(positions);
    }

    public boolean isOccupied() {
        return airplane != null;
    }

    public void enterSectorAirplane(Airplane airplane) {
        if (airplane != null) {
            try {
                semaphore.acquire();
                this.airplane = airplane;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void leaveSectorAirplane() {
        this.airplane = null;
        semaphore.release();
    }

}
