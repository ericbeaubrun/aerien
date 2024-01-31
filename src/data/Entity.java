package data;

import util.ConversionUtility;

public class Entity {

    public Entity(int x, int y) {
        this.position = new Position(x, y);
    }

    public Entity(Position position) {
        this.position = position;
    }

    public Entity(int x, int y, int z) {
        this.position = new Position(x, y, z);
    }

    private Position position;

    public void setPosition(Entity obj) {
        this.setPosition(obj.getPosition());
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public Boolean hasSamePosition(Entity obj) {
        return getX() == obj.getX() && getY() == obj.getY() && getZ() == obj.getZ();
    }

    public Boolean hasSamePosition(Position pos) {
        return getX() == pos.getX() && getY() == pos.getY() && getZ() == pos.getZ();
    }
}
