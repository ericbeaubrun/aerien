package data;

import util.ConversionUtility;

/**
 * The type Entity represents all elements that hava a Position and can be put / move on the map.
 */
public class Entity {

    public Entity(int x, int y) {
        this.position = new Position(x, y);
    }

    private final Position position;

    public void changePosition(Entity obj) {
        this.changePosition(obj.getPosition());
    }

    /**
     * Change x and y of the Entity.
     *
     * @param position the new position of the Entity
     */
    public void changePosition(Position position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    public Position getPosition() {
        return position;
    }

    public void setX(int x) {
        position.setX(x);
    }

    public void setY(int y) {
        position.setY(y);
    }

    public void setZ(int z) {
        position.setZ(z);
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public int getZ() {
        return position.getZ();
    }

    public int getColumn() {
        return ConversionUtility.pixelToBlock(position.getX());
    }

    public int getRow() {
        return ConversionUtility.pixelToBlock(position.getY());
    }

    public Boolean hasSamePosition(int column, int row) {
        return getColumn() == column && getRow() == row;
    }

    public Boolean hasSamePosition(Position pos) {
        return getX() == pos.getX() && getY() == pos.getY() && getZ() == pos.getZ();
    }
}
