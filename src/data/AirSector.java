package data;


import java.util.ArrayList;

public class AirSector {

    private final ArrayList<Position> positions = new ArrayList<>();

    // Représente les deux points permettant de dessiner un rectangle
    public AirSector() {

    }

    public void addPosition(Position position){
        positions.add(position);
    }

    public ArrayList<Position> getPositions() {
        return new ArrayList<>(positions);
    }
}
