package ihm;

import config.Config;
import data.*;
import engine.Flight;
import engine.SelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static config.Config.BLOCK_SIZE;
import static util.ImageUtility.*;

public class AerialTrafficPanel extends JPanel {

    private final String BACKGROUND_IMAGE_PATH = Config.RESSOURCES_PATH + "map.png";

    private final float[] DASH_PATTERN = {5.0f, 5.0f};

    private final Stroke FLIGHT_STROKE = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, DASH_PATTERN, 0.0f);

    private final MapField mapField;

    private final ArrayList<Flight> flights;

    private final ArrayList<Airplane> airplanes;

    private final ArrayList<Airport> airports;

    private boolean showAirZones = false;

    private boolean showFlights = false;

    private boolean showGrid = false;

    private boolean showCoords = false;

    private boolean showStyle = false;

    private boolean showBackground = false;

    private boolean showTrajects = false;

    private final BufferedImage backgroundImage;

    public AerialTrafficPanel(MapField mapField, ArrayList<Airplane> airplanes, ArrayList<Airport> airports, ArrayList<Flight> flights) {
        this.mapField = mapField;
        this.airplanes = airplanes;
        this.airports = airports;
        this.flights = flights;

        backgroundImage = readImage(BACKGROUND_IMAGE_PATH);
        setBackground(Config.BACKGROUND_COLOR);
    }

    private void paintBackground(Graphics g) {
        if (showBackground) {
            g.drawImage(backgroundImage, 0, 0, (int) (getWidth() * 1.06), (int) (getHeight() * 1.0), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, BLOCK_SIZE * getWidth(), BLOCK_SIZE * getWidth());
        }
    }

    private void paintGridCoords(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, 9));

        for (Position block : mapField.getBlocks()) {
            if (showGrid) {
                g.setColor(Color.GREEN);
                g.drawRect(block.getX(), block.getY(), BLOCK_SIZE, BLOCK_SIZE);
            }
            if (showCoords) {
                g.setColor(Color.WHITE);
                g.drawString(block.getColumn() + ":" + block.getRow(), block.getX() + 5, block.getY() + 15);
            }
        }
    }

    private void highlightEntity(Graphics g, Entity entity) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.3f));
        g2d.fillOval(entity.getX(), entity.getY(), BLOCK_SIZE, BLOCK_SIZE);
        g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
    }

    private void paintAirplanes(Graphics g) {

        if (getMouseListeners().length == 1) {
            SelectionListener selectionListener = (SelectionListener) (getMouseListeners()[0]);
            Entity airplane = selectionListener.getSelectedAirplane();
            Entity airport = selectionListener.getSelectedAirport();

            if (airplane != null) {
                highlightEntity(g, airplane);
            } else if (airport != null) {
                highlightEntity(g, airport);
            }
        }

        for (Airplane airplane : airplanes) {
            if (!airplane.isOnTrail()) {
                if (showStyle) {
                    BufferedImage image = getAirplaneImage(airplane);
                    if (image != null) {
                        image = rotateImage(image, airplane.getAngle());
                        g.drawImage(image, airplane.getX(), airplane.getY(), null);
//                      o  g.drawImage(image, airplane.getX(), airplane.getY(), (int) ((48*Config.SCREEN_DIMENSION.getHeight())/1220),
//                                (int) ((50*Config.SCREEN_DIMENSION.getHeight())/1220), null);

                    } else {
                        //traitement
                    }
                } else {

                    int x = airplane.getX() + BLOCK_SIZE / 4;
                    int y = airplane.getY() + BLOCK_SIZE / 4;
                    int width = (BLOCK_SIZE - 5) / 2;
                    int height = (BLOCK_SIZE - 5) / 2;

                    g.setColor(Color.GREEN);
                    g.fillOval(x, y, width, height);
                }
            }
        }
    }

    private void paintAirports(Graphics g) {
        for (Airport airport : airports) {

            int x = airport.getX() + BLOCK_SIZE / 4;
            int y = airport.getY() + BLOCK_SIZE / 4;
            int width = BLOCK_SIZE / 2;
            int height = BLOCK_SIZE / 2;

            g.setColor(Color.RED);
            g.fillOval(x, y, width, height);

            g.setColor(Color.WHITE);
            g.drawString(airport.getAmountAirplanes() + "/" + airport.getMaxAirplanes(), x, y);
        }
    }


    public void paintFlights(Graphics g) {
        if (showTrajects) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(FLIGHT_STROKE);
            for (Flight flight : flights) {
                if (flight.isRunning() || showFlights) {
                    Airplane airplane = flight.getAirplane();

                    ArrayList<Position> path = flight.getPath();

                    g2d.setColor(Color.WHITE);

                    boolean hasBeenCovered = true;

                    for (int i = 0; i < path.size() - 1; i++) {
                        Position p1 = path.get(i);
                        Position p2 = path.get(i + 1);

                        if (flight.getCurrentIndex() - 1 == i || flight.getCurrentIndex() == 0) {
                            hasBeenCovered = false;
                            g2d.setColor(new Color(110, 110, 110));
                            g2d.setColor(Color.YELLOW);
                        }

                        int x1 = p1.getX() + BLOCK_SIZE / 2;
                        int y1 = p1.getY() + BLOCK_SIZE / 2;
                        int x2 = p2.getX() + BLOCK_SIZE / 2;
                        int y2 = p2.getY() + BLOCK_SIZE / 2;

                        g.drawLine(x1, y1, x2, y2);
                    }

                    // Ligne Aéroport de départ
                    {
                        int x1 = flight.getStartAirport().getX() + BLOCK_SIZE / 2;
                        int y1 = flight.getStartAirport().getY() + BLOCK_SIZE / 2;
                        int x2 = path.get(0).getX() + BLOCK_SIZE / 2;
                        int y2 = path.get(0).getY() + BLOCK_SIZE / 2;
                        g.drawLine(x1, y1, x2, y2);
                    }

                    // Ligne Aéroport d'arrivé
                    {
                        int x1 = path.get(path.size() - 1).getX() + BLOCK_SIZE / 2;
                        int y1 = path.get(path.size() - 1).getY() + BLOCK_SIZE / 2;
                        int x2 = flight.getDestinationAirport().getX() + BLOCK_SIZE / 2;
                        int y2 = flight.getDestinationAirport().getY() + BLOCK_SIZE / 2;
                        g.drawLine(x1, y1, x2, y2);
                    }


                } else {
//                    g.drawString(flight.getCountBeforeTakeoff() + "", flight.getStartAirport().getX(), flight.getStartAirport().getY());
                }
            }
        }
    }

    public void paintAirZones(Graphics g) {
        if (showAirZones) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setComposite(AlphaComposite.SrcOver.derive(0.2f));

            ArrayList<AirZone> airZones = mapField.getAirZones();

            boolean isColored = true;

            for (int i = 0; i < airZones.size(); i++) {
                if (!(i % 10 == 0)) {
                    isColored = !isColored;
                }

                AirZone airZone = airZones.get(i);

                if (airZone.isOccupied()) {
                    g2d.setColor(Color.RED);
                } else {
                    g2d.setColor(isColored ? Color.BLUE : Color.WHITE);
                }

                for (Position block : airZone.getPositions()) {
                    g.fillRect(block.getX(), block.getY(), BLOCK_SIZE, BLOCK_SIZE);
                }
            }

            g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        paintBackground(g);
        paintGridCoords(g);
        paintAirports(g);
        paintFlights(g);
        paintAirplanes(g);
        paintAirZones(g);

    }

    public void toggleShowCoords() {
        showCoords = !showCoords;
    }

    public void toggleShowBackground() {
        showBackground = !showBackground;
    }

    public void toggleShowStyle() {
        showStyle = !showStyle;
    }

    public void toggleShowGrid() {
        showGrid = !showGrid;
    }

    public void toggleShowTrajects() {
        showTrajects = !showTrajects;
    }

    public void toggleShowAirZones() {
        showAirZones = !showAirZones;
    }
}
