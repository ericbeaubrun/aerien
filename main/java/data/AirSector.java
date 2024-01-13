package data;


public class AirSector {

    private final Position[] blocks = new Position[2];

    // Représente les deux points permettant de dessiner un rectangle
    public AirSector(Position origin, Position top) {
        blocks[0] = origin;
        blocks[1] = top;
    }

    public Position getOrigin() {
        return blocks[0];
    }

    public Position getTop() {
        return blocks[1];
    }
}
