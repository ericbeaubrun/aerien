package data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static config.Config.*;

public class MapField {

    private final int columns;
    private final int rows;
    private final Position[][] blocks;
    private final ArrayList<AirSector> airSectors;

    public MapField(int columns, int rows, int size) {
        blocks = new Position[columns][rows];
        airSectors = new ArrayList<>();

        this.columns = columns;
        this.rows = rows;

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Position block = new Position(i * size, j * size);
                blocks[i][j] = block;
            }
        }

        initAirSectors();
    }

    private void initAirSectors() {
        for (int row = 0; row < 20; row += 2) {
            for (int col = 0; col < 30; col += 3) {
                AirSector airSector = new AirSector();
                for (int r = row; r < row + 2; r++) {
                    for (int c = col; c < col + 3; c++) {
                        airSector.addPosition(blocks[c][r]);
                    }
                }
                airSectors.add(airSector);
            }
        }
    }

    public Position findPosition(int columns, int rows) {
        if (columns < this.columns && rows < this.rows) {
            return blocks[columns][rows];
        }
        return null;
    }


    public ArrayList<Position> getBlocks() {
        ArrayList<Position> result = new ArrayList<>();
        for (Position[] positions : blocks) {
            result.addAll(Arrays.asList(positions));
        }
        return result;
    }

    public ArrayList<AirSector> getAirSectors() {
        return new ArrayList<>(airSectors);
    }
}
