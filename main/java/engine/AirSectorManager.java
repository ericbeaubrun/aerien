package engine;

import data.Airplane;
import data.MapField;
import data.Position;
import data.AirSector;

import java.util.ArrayList;

public class AirSectorManager {
    private ArrayList<AirSector> rectangles = new ArrayList<>();
    private ArrayList<Airplane> airplanes;
    private MapField mapField;

    public AirSectorManager(ArrayList<Airplane> airplanes, MapField mapField) {
        this.airplanes = airplanes;
        this.mapField = mapField;
    }

    private ArrayList<Position> getBlocksInRectAirZone(AirSector rectanle) {
        ArrayList<Position> result = new ArrayList<>();
        for (int i = rectanle.getOrigin().getColumn(); i <= rectanle.getTop().getRow(); ++i) {
            for (int j = rectanle.getOrigin().getColumn(); j <= rectanle.getTop().getRow(); ++j) {
                result.add(mapField.findBlock(i, j));
            }
        }
        return result;
    }

    private boolean occupiedRectContainsBlock(Position block) {
        for (Airplane airplane : airplanes) {
            int i = airplane.getColumn();
            int j = airplane.getRow();

            for (AirSector rectangle : rectangles) {
                if (i >= rectangle.getOrigin().getColumn() && i <= rectangle.getTop().getColumn()
                        && j >= rectangle.getOrigin().getRow() && j <= rectangle.getTop().getRow()) {
                    if (getBlocksInRectAirZone(rectangle).contains(block)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
