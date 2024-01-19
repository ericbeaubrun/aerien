package data;


import java.util.ArrayList;

public class Airport extends SimulationEntity {

    private final int maxAirplanes;

    private final ArrayList<Airplane> airplanes;

    private String name;

    public Airport(int x, int y, String name, int maxAirplanes) {
        super(x, y);
        airplanes = new ArrayList<>();
        this.maxAirplanes = maxAirplanes;
        this.name = name;
    }

    public boolean addAirplane(Airplane airplane) {
        if (airplane != null && airplanes.size() <= maxAirplanes) {
            airplane.setOnTrail(true);
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

    public int getAmountAirplanes() {
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

    public Airplane getAvailableAirplane() {
        if (!airplanes.isEmpty()) {
            return airplanes.get(0);
        }
        return null;
    }

    public Airplane getAvailableAirplaneExcept(ArrayList<Airplane> exceptAirplanes) {
        for (int i = 0; i < airplanes.size(); i++) {
            Airplane airplane = airplanes.get(i);
            if (!exceptAirplanes.contains(airplane)) {
                return airplane;
            }
        }
        return null;
    }

    public ArrayList<Airplane> getAirplanes() {
        return new ArrayList<>(airplanes);
    }

    public String getName() {
        return name;
    }
}
