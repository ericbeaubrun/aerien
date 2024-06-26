package data;


import java.util.ArrayList;

public class Airport extends Entity {

    private final int maxAirplanes;

    private int availableRunwayCount;

    private final ArrayList<Airplane> airplanes;

    private final String name;

    public Airport(int x, int y, String name, int maxAirplanes) {
        super(x, y);
        airplanes = new ArrayList<>();
        availableRunwayCount = maxAirplanes;
        this.maxAirplanes = maxAirplanes;
        this.name = name;
    }

    public boolean addAirplane(Airplane airplane) {
        if (airplane != null && airplanes.size() <= maxAirplanes) {
            airplane.putOnRunway(true);
            airplane.setX(this.getX());
            airplane.setY(this.getY());
            return airplanes.add(airplane);
        }
        return false;
    }

    public boolean removeAirplane(Airplane airplane) {
        if (airplane != null) {
            return airplanes.remove(airplane);
        }
        return false;
    }

    public int getAmountAirplanesOnRunway() {
        return airplanes.size();
    }

    public int getMaxAirplanes() {
        return maxAirplanes;
    }

    public boolean isFilled() {
        return airplanes.size() >= maxAirplanes;
    }

    /**
     * @return true if there is at least 1 available runway, false else.
     */
    public boolean isAvailableToSpawn() {
        return airplanes.size() >= maxAirplanes - 1;
    }

    public boolean hasAvailableRunway() {
        return availableRunwayCount > 0;
    }

    public void incrementAvailableRunwayCount() {
        availableRunwayCount++;
    }

    public void decrementAvailableRunwayCount() {
        availableRunwayCount--;
    }

    public int getAmountAirplanesIncoming() {
        return maxAirplanes - availableRunwayCount - airplanes.size();
    }

    public ArrayList<Airplane> getAirplanes() {
        return new ArrayList<>(airplanes);
    }

    public String getName() {
        return name;
    }

    public int getAvailableRunwayCount() {
        return availableRunwayCount;
    }

    @Override
    public String toString() {
        return name;
    }
}
