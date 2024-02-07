package data;


import engine.Flight;

import java.util.ArrayList;

public class Airport extends Entity {

    private final int maxAirplanes;

    private int availableRunwayCount;

    private final ArrayList<Airplane> airplanes;

    private String name;

    public Airport(int x, int y, String name, int maxAirplanes) {
        super(x, y);
        airplanes = new ArrayList<>();
        this.maxAirplanes = maxAirplanes;
        availableRunwayCount = maxAirplanes;
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

    public boolean isAvailableSpawn() {
        return airplanes.size() >= maxAirplanes - 1;
    }

    public String coordToString() {
        return "pixel=(" + getX() + ":" + getY() + ") block=(" + getColumn() + ":" + getRow() + ")";
    }

    public Airplane getFirstAvailableAirplane() {
        if (!airplanes.isEmpty()) {
            return airplanes.get(0);
        }
        return null;
    }

    public Airplane getFirstAvailableAirplaneExcept(ArrayList<Airplane> exceptAirplanes) {
        if (exceptAirplanes != null) {
            for (int i = 0; i < airplanes.size(); i++) {
                Airplane airplane = airplanes.get(i);
                if (!exceptAirplanes.contains(airplane)) {
                    return airplane;
                }
            }
        }
        return null;
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

    public ArrayList<Airplane> getAirplanes() {
        return new ArrayList<>(airplanes);
    }

    public String getName() {
        return name;
    }

    public int getAvailableRunwayCount() {
        return availableRunwayCount;
    }
}
