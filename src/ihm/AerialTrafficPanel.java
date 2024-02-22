package ihm;

import data.*;
import engine.Flight;
import engine.SelectionListener;
import ihm.buttons.DisplayToggle;
import ihm.paint.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static config.Config.*;
import static config.IHMConfig.*;
import static util.ImageUtility.*;

public class AerialTrafficPanel extends JPanel {

    private final MapField map;

    private final ArrayList<Flight> flights;
    private final ArrayList<Airport> airports;
    private final ArrayList<Airplane> airplanes;

    private final DisplayToggle displayToggle;

    private final AirplanePainter airplanePainter;
    private final AirportPainter airportPainter;
    private final FlightPainter flightPainter;
    private final EntityHighlighter entityHighlighter;
    private final VisualDataPainter mapPainter;


    private final BufferedImage backgroundImage;
    private final BufferedImage backgroundReliefImage;

    public AerialTrafficPanel(MapField map, ArrayList<Airplane> airplanes, ArrayList<Airport> airports, ArrayList<Flight> flights) {
        this.flights = flights;
        this.map = map;
        this.airports = airports;
        this.airplanes = airplanes;

        displayToggle = new DisplayToggle();

        airplanePainter = new AirplanePainter(displayToggle);
        airportPainter = new AirportPainter(displayToggle);
        flightPainter = new FlightPainter(displayToggle);
        entityHighlighter = new EntityHighlighter(displayToggle);
        mapPainter = new VisualDataPainter(displayToggle);

        backgroundImage = readImage(BACKGROUND_IMAGE_PATH);
        backgroundReliefImage = readImage(BACKGROUND_RELIEF_IMAGE_PATH);

        setBackground(BACKGROUND_COLOR);
    }

    private void paintBackground(Graphics g) {
        if (displayToggle.showBackgroundEnabled() && displayToggle.showReliefEnabled()) {
            g.drawImage(backgroundReliefImage, 0, 0, (int) (getWidth() * 1.06), (int) (getHeight() * 1.0), null);
        } else if (displayToggle.showBackgroundEnabled()) {
            g.drawImage(backgroundImage, 0, 0, (int) (getWidth() * 1.06), (int) (getHeight() * 1.0), null);
        } else {
            g.setColor(displayToggle.showAirZonesEnabled() ? BACKGROUND_COLOR : BACKGROUND_COLOR_MAP);
            g.fillRect(0, 0, BLOCK_SIZE * getWidth(), BLOCK_SIZE * getWidth());
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        SelectionListener selectionListener = getSelectionListener();

        paintBackground(g);

        mapPainter.paintGridCoords(g, map);
        flightPainter.paintFlights(g, flights, selectionListener);
        mapPainter.paintRelief(g, map);
        mapPainter.paintAirZones(g, map);
        airportPainter.paintAirports(g, airports);
        entityHighlighter.highlightAirplaneAirport(g, selectionListener);
        airplanePainter.paintAirplanes(g, airplanes);
    }

    public SelectionListener getSelectionListener() {
        return (SelectionListener) (getMouseListeners()[0]);
    }

    public DisplayToggle getDisplayToggle() {
        return displayToggle;
    }
}
