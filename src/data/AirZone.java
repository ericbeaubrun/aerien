package data;


import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class AirZone {

    //    private Lock mutex = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(1, true);

    private Airplane airplane;

    private final ArrayList<Position> positions = new ArrayList<>();
    public boolean tryToAcquire = false;

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

    public void enterInAirZone(Airplane airplane) {
        if (airplane != null) {
            try {
                airplane.setWaiting(true);
//                tryToAcquire = true;
                semaphore.acquire();
                this.airplane = airplane;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            airplane.setWaiting(false);
        }
//        tryToAcquire = false;
    }

    public void leaveAirzone() {
        this.airplane = null;
        semaphore.release();
    }

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public Airplane getAirplane() {
        return airplane;
    }
}
