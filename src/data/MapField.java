package data;

import java.util.ArrayList;
import java.util.Arrays;

import static config.Config.*;

public class MapField {

    private final int columns;

    private final int rows;

    private final Position[][] blocks;

    private final ArrayList<AirZone> airZones;

    public MapField(int columns, int rows, int size) {
        blocks = new Position[columns][rows];
        airZones = new ArrayList<>();

        this.columns = columns;
        this.rows = rows;

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Position block = new Position(i * size, j * size);
                blocks[i][j] = block;
            }
        }

        initAirZones();
    }

    private void initAirZones() {
        for (int row = 0; row < ROWS; row += AIR_ZONE_HEIGHT) {
            for (int col = 0; col < COLUMNS; col += AIR_ZONE_WIDTH) {

                AirZone airZones = new AirZone();
                for (int i = row; i < row + AIR_ZONE_HEIGHT; i++) {
                    for (int j = col; j < col + AIR_ZONE_WIDTH; j++) {
                        airZones.addPosition(blocks[j][i]);
                    }
                }
                this.airZones.add(airZones);
            }
        }
    }

    public Position findPosition(int columns, int rows) {
        if (columns < this.columns && rows < this.rows) {
            return blocks[columns][rows];
        }
        return null;
    }

    public AirZone findAirZone(Position position){
        for (AirZone airZone : airZones) {
            if (airZone.getPositions().contains(position)) {
                return airZone;
            }
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

    public ArrayList<AirZone> getAirZones() {
        return new ArrayList<>(airZones);
    }
}
