package ihm.paint;

import data.Position;
import engine.Flight;
import engine.SelectionListener;
import ihm.buttons.DisplayToggle;
import util.ConversionUtility;
import util.SortUtility;

import java.awt.*;
import java.util.ArrayList;

import static config.Config.BLOCK_SIZE;
import static config.IHMConfig.*;
import static config.IHMConfig.TRAVELED_FLIGHT_COLOR;

public class FlightPainter {

    private final DisplayToggle displayToggle;

    public FlightPainter(DisplayToggle displayToggle) {
        this.displayToggle = displayToggle;
    }

    public void paintFlights(Graphics g, ArrayList<Flight> flights, SelectionListener selectionListener) {
        if (displayToggle.showTrajectsEnabled() || displayToggle.showFlightsEnabled()) {

            Flight selectedFlight = selectionListener.getSelectedFlight();

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(displayToggle.showFlightsEnabled() ? FLIGHT_STROKE_1 : FLIGHT_STROKE_2);

            int x1, x2, y1, y2;

            // To paint in order
            SortUtility.sortFlights(flights);

            for (Flight flight : flights) {

                if (flight.isRunning() || displayToggle.showFlightsEnabled()) {
                    ArrayList<Position> path = flight.getPath();

                    // By default
                    g2d.setColor(BASIC_FLIGHT_COLOR);

                    if (flight.equals(selectedFlight)) {
                        g2d.setStroke(HIGHLIGHT_STROKE);
                    }

                    // Find the flight color
                    if (displayToggle.showFlightsEnabled()) {
                        if (flight.isRunning() && flight.getAirplane().isWaiting()) {
                            g2d.setColor(PAUSED_FLIGHT_COLOR);
                        } else if (flight.isRunning()) {
                            g2d.setColor(RUNNING_FLIGHT_COLOR);
                        } else if (flight.getCountdown() < 0 && !flight.isRunning() && flight.isAssociatedToAirplane()) {
                            // There is a plane in the area of the departure airport or the arrival airport is full
                            g2d.setColor(DELAYED_FLIGHT_COLOR);
                        }
                    }

                    // For first position
                    paintStartAirportLine(g, flight);

                    // For all position constituting the flight path
                    for (int i = 0; i < path.size() - 1; i++) {
                        if (flight.equals(selectedFlight)) {
                            g2d.setStroke(HIGHLIGHT_STROKE);
                        } else if (displayToggle.showFlightsEnabled()) {
                            g2d.setStroke(FLIGHT_STROKE_1);
                        } else {
                            g2d.setStroke(FLIGHT_STROKE_2);
                        }

                        if (!displayToggle.showFlightsEnabled() && (flight.getCurrentPositionIndex() - 1 == i
                                || flight.getCurrentPositionIndex() == 0)) {
                            g2d.setColor(TRAVELED_FLIGHT_COLOR);
                        }

                        // Draw the line
                        x1 = path.get(i).getX();
                        y1 = path.get(i).getY();
                        x2 = path.get(i + 1).getX();
                        y2 = path.get(i + 1).getY();
                        g.drawLine(x1 + BLOCK_SIZE / 2, y1 + BLOCK_SIZE / 2, x2 + BLOCK_SIZE / 2, y2 + BLOCK_SIZE / 2);
                    }

                    // For last position
                    paintDestinationAirportLine(g, flight);
                }
            }
        }
    }

    private void paintTriangles(Graphics g, int length, int x1, int y1, int x2, int y2) {
        int[][] val = ConversionUtility.calculateArrowheadPoints(BLOCK_SIZE / 2,
                x1 + length, x2 + length, y1 + length, y2 + length);
        g.fillPolygon(val[0], val[1], 3);
    }

    private void paintStartAirportLine(Graphics g, Flight flight) {
        if (flight != null) {

            int x1 = flight.getStartAirport().getX();
            int y1 = flight.getStartAirport().getY();
            int x2 = flight.getPath().get(0).getX();
            int y2 = flight.getPath().get(0).getY();

            if (displayToggle.showFlightsEnabled()) {
                paintTriangles(g, BLOCK_SIZE / 2, x1, y1, x2, y2);
            } else {
                g.setColor(flight.getCurrentPositionIndex() > 0 ? BASIC_FLIGHT_COLOR : TRAVELED_FLIGHT_COLOR);
            }

            g.drawLine(x1 + BLOCK_SIZE / 2, y1 + BLOCK_SIZE / 2, x2 + BLOCK_SIZE / 2, y2 + BLOCK_SIZE / 2);
        }
    }

    private void paintDestinationAirportLine(Graphics g, Flight flight) {
        if (flight != null) {
            if (!displayToggle.showFlightsEnabled()) {
                g.setColor(TRAVELED_FLIGHT_COLOR);
            }
            int x1 = flight.getPath().get(flight.getPath().size() - 1).getX();
            int y1 = flight.getPath().get(flight.getPath().size() - 1).getY();
            int x2 = flight.getDestinationAirport().getX();
            int y2 = flight.getDestinationAirport().getY();
            g.drawLine(x1 + BLOCK_SIZE / 2, y1 + BLOCK_SIZE / 2, x2 + BLOCK_SIZE / 2, y2 + BLOCK_SIZE / 2);
        }
    }
}
