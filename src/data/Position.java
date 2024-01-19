package data;

import util.ConversionUtility;

public class Position {


    private int x;

    private int y;

    private int z; // Représente l'altitude

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getColumn() {
        return ConversionUtility.pixelToBlock(x);
    }

    public int getRow() {
        return ConversionUtility.pixelToBlock(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setXYZ(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "pixel=(" + x + ":" + y + ":" + z + ") block=(" + getColumn() + ":" + getRow() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return ((Position) obj).getX() == x && ((Position) obj).getY() == y && ((Position) obj).getZ() == z;
        }
        return false;
    }
}
