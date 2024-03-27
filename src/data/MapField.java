package data;

import java.util.ArrayList;

import static config.Config.*;

/**
 * This class represents the Map of the Simulation.
 */
public class MapField {

    private final int columns;

    private final int rows;

    private final Position[][] positions;

    private final ArrayList<AirZone> airZones;

    /**
     * Instantiates a new Map field with given dimensions.
     *
     * @param columns the total amount of columns
     * @param rows    the total amount of rows
     * @param size    the size of each block
     */
    public MapField(int columns, int rows, int size) {
        this.columns = columns;
        this.rows = rows;

        positions = new Position[columns][rows];

        airZones = new ArrayList<>();

        initPostions(size);

        initAirZones();
    }


    /**
     * @param size the size of each block.
     */
    private void initPostions(int size) {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Position block = new Position(i * size, j * size);
                positions[i][j] = block;
            }
        }
    }


    /**
     * Initialize all air zones in the map.
     */
    private void initAirZones() {
        for (int row = 0; row < ROWS; row += AIR_ZONE_HEIGHT) {
            for (int col = 0; col < COLUMNS; col += AIR_ZONE_WIDTH) {
                AirZone airZones = new AirZone();
                for (int i = row; i < row + AIR_ZONE_HEIGHT; i++) {
                    for (int j = col; j < col + AIR_ZONE_WIDTH; j++) {
                        airZones.addPosition(positions[j][i]);
                    }
                }
                this.airZones.add(airZones);
            }
        }
    }

    /**
     * Find air zone by giving one of its position.
     *
     * @param position the position
     * @return the air zone
     */
    public AirZone findAirZone(Position position) {
        if (position != null) {
            for (AirZone airZone : airZones) {
                for (Position position1 : airZone.getPositions()) {
                    if (position1.getX() == position.getX() && position1.getY() == position.getY()) {
                        return airZone;
                    }
                }
            }
        }
        return null;
    }

    public Position getPosition(int columns, int rows) {
        if (columns < this.columns && rows < this.rows) {
            return positions[columns][rows];
        }
        return null;
    }

    public ArrayList<AirZone> getAirZones() {
        return new ArrayList<>(airZones);
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}
