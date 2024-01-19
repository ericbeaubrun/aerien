package data;

import util.ConversionUtility;

import java.util.ArrayList;

public class MapField {

    private int columns;
    private int rows;
    private ArrayList<Position> blocks;

    public MapField(int columns, int rows, int size) {
        blocks = new ArrayList<>();

        this.columns = ConversionUtility.blockToPixel(columns);
        this.rows = ConversionUtility.blockToPixel(rows);

        for (int i = 0; i < this.columns; i += size) {
            for (int j = 0; j < this.rows; j += size) {
                Position block = new Position(i, j);
                blocks.add(block);
            }
        }
    }

    public Position findBlock(int column, int row) {
        for (Position block : blocks) {
            if (block.getColumn() == column && block.getRow() == row) {
                return block;
            }
        }
        return null;
    }


    public ArrayList<Position> getBlocks() {
        return new ArrayList<>(blocks);
    }
}
