package ihm.paint;

import data.Airport;
import ihm.buttons.DisplayToggle;

import java.awt.*;
import java.util.ArrayList;

import static config.Config.BLOCK_SIZE;
import static config.IHMConfig.BASIC_AIRPORT_COLOR;
import static config.IHMConfig.BASIC_TEXT_COLOR;

public class AirportPainter {

    private final DisplayToggle displayToggle;

    public AirportPainter(DisplayToggle displayToggle) {
        this.displayToggle = displayToggle;
    }

    public void paintAirports(Graphics g, ArrayList<Airport> airports) {
        for (Airport airport : airports) {

            int x = airport.getX() + BLOCK_SIZE / 6;
            int y = airport.getY() + BLOCK_SIZE / 6;
            int width = BLOCK_SIZE / 2;
            int height = BLOCK_SIZE / 2;

            g.setColor(BASIC_AIRPORT_COLOR);

            if (displayToggle.showAirportsEnabled()) {
                g.fillOval(x, y, width + BLOCK_SIZE / 4, height + BLOCK_SIZE / 4);

                int maxAirplanes = airport.getMaxAirplanes();
                int amountAirplanes = airport.getAmountAirplanesOnRunway();

                // Draw Airport Name and amount airplanes
                g.setColor(maxAirplanes == amountAirplanes ? Color.RED : BASIC_TEXT_COLOR);
                g.drawString(airport.getName() + " (" + airport.getAmountAirplanesOnRunway() + "/" + airport.getMaxAirplanes() + ")",
                        x - BLOCK_SIZE / 5, y - BLOCK_SIZE / 5);
            } else {
                g.fillOval(x + BLOCK_SIZE / 8, y + BLOCK_SIZE / 8, width, height);
            }
        }
    }
}
