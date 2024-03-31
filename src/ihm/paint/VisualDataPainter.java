package ihm.paint;

import data.AirZone;
import data.MapField;
import data.Position;
import ihm.buttons.DisplayToggle;

import java.awt.*;
import java.util.ArrayList;

import static config.Config.*;
import static config.IHMConfig.*;

public class VisualDataPainter {

    private final DisplayToggle displayToggle;

    public VisualDataPainter(DisplayToggle displayToggle) {
        this.displayToggle = displayToggle;
    }

    public void paintGridCoords(Graphics g, MapField map) {
        g.setFont(BASIC_FONT);

        for (int i = 0; i < map.getColumns(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                Position block = map.getPosition(i, j);
                if (displayToggle.showGridEnabled()) {
                    paintGridRectangle(g, block);
                }
                if (displayToggle.showCoordsEnabled()) {
                    paintCoordinates(g, block);
                }
            }
        }
    }

    private void paintCoordinates(Graphics g, Position block) {
        if (block != null) {
            g.setColor(BASIC_TEXT_COLOR);
            g.drawString(block.getColumn() + ":" + block.getRow(), block.getX() + BLOCK_SIZE / 6, block.getY() + BLOCK_SIZE / 2);
        }
    }

    private void paintGridRectangle(Graphics g, Position block) {
        if (block != null) {
            g.setColor(GRID_COLOR);
            g.drawRect(block.getX(), block.getY(), BLOCK_SIZE, BLOCK_SIZE);
        }
    }

    public void paintAirZones(Graphics g, MapField map) {
        if (displayToggle.showAirZonesEnabled() || displayToggle.showOccupiedZonesEnabled()) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setComposite(AlphaComposite.SrcOver.derive(0.2f));

            ArrayList<AirZone> airZones = map.getAirZones();

            boolean alternateColor = true;

            for (int i = 0; i < airZones.size(); i++) {
                AirZone airZone = airZones.get(i);

                if (!(i % AIR_ZONE_GAP == 0)) {
                    alternateColor = !alternateColor;
                }

                if (airZone.isOccupied() && displayToggle.showOccupiedZonesEnabled()) {
                    g2d.setColor(OCCUPIED_AIRZONE_COLOR);
                } else if (alternateColor) {
                    g2d.setColor(AIRZONE_1_COLOR);
                } else {
                    g2d.setColor(AIRZONE_2_COLOR);
                }

                if (displayToggle.showAirZonesEnabled() || (displayToggle.showOccupiedZonesEnabled() && airZone.isOccupied())) {
                    paintAirZone(g, airZone);
                }
            }

            g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        }
    }

    private void paintAirZone(Graphics g, AirZone airZone) {
        if (airZone != null) {
            for (Position block : airZone.getPositions()) {
                g.fillRect(block.getX(), block.getY(), BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    public void paintRelief(Graphics g, MapField map) {
        if (displayToggle.showReliefEnabled()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.3f));
            for (int i = 0; i < map.getColumns(); i++) {
                for (int j = 0; j < map.getRows(); j++) {
                    Position block = map.getPosition(i, j);
                    paintReliefRect(g2d, block);
                }
            }
            g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        }
    }

    private void paintReliefRect(Graphics2D g2d, Position block) {
        if (block != null) {
            switch (block.getZ()) {
                case HIGH_ALTITUDE:
                    g2d.setColor(HIGH_ALTITUDE_COLOR);
                    g2d.fillRect(block.getX(), block.getY(), BLOCK_SIZE, BLOCK_SIZE);
                    break;

                case MEDIUM_ALTITUDE:
                    g2d.setColor(MEDIUM_ALTITUDE_COLOR);
                    g2d.fillRect(block.getX(), block.getY(), BLOCK_SIZE, BLOCK_SIZE);
                    break;

                case LOW_ALTITUDE:
                    g2d.setColor(LOW_ALTITUDE_COLOR);
                    g2d.fillRect(block.getX(), block.getY(), BLOCK_SIZE, BLOCK_SIZE);
                    break;
            }
        }
    }
}
