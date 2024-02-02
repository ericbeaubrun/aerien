package ihm;

import config.Config;
import data.*;
import engine.Flight;
import engine.SelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static config.Config.*;
import static util.ImageUtility.*;

public class AerialTrafficPanel extends JPanel {

    private final String BACKGROUND_IMAGE_PATH = Config.RESSOURCES_PATH + "map.png";

    private final Font BASIC_FONT = new Font("Arial", Font.PLAIN, BLOCK_SIZE / 4);

    private final Color FLIGHT_STROKE_COLOR = new Color(110, 110, 110);

    private final float[] DASH_PATTERN = {5.0f, 5.0f};

    private final Stroke FLIGHT_STROKE = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, DASH_PATTERN, 0.0f);

    private final MapField mapField;

    private final ArrayList<Flight> flights;

    private final ArrayList<Airport> airports;

    private final ArrayList<Airplane> airplanes;

//    private final HashMap<Airplane, BufferedImage> airplaneImageHashMap = new HashMap<>();

    private boolean showGrid = false;

    private boolean showStyle = false;

    private boolean showCoords = false;

    private boolean showFlights = false;

    private boolean showTrajects = false;

    private boolean showAirZones = false;

    private boolean showBackground = false;

    private final BufferedImage backgroundImage;

    public AerialTrafficPanel(MapField mapField, ArrayList<Airplane> airplanes, ArrayList<Airport> airports, ArrayList<Flight> flights) {
        this.flights = flights;
        this.mapField = mapField;
        this.airports = airports;
        this.airplanes = airplanes;

//        for (Airplane airplane : airplanes) {
//            airplaneImageHashMap.put(airplane, getAirplaneImage(airplane));
//        }

        backgroundImage = readImage(BACKGROUND_IMAGE_PATH);
        setBackground(Config.BACKGROUND_COLOR);
    }

    private void paintBackground(Graphics g) {
        if (showBackground) {
            g.drawImage(backgroundImage, 0, 0, (int) (getWidth() * 1.06), (int) (getHeight() * 1.0), null);
        } else {
            g.setColor(showAirZones ? Color.GRAY : Color.BLACK);
            g.fillRect(0, 0, BLOCK_SIZE * getWidth(), BLOCK_SIZE * getWidth());
        }
    }

    private void paintGridCoords(Graphics g) {
        g.setFont(BASIC_FONT);

        for (Position block : mapField.getBlocks()) {
            if (showGrid) {
                g.setColor(Color.GREEN);
                g.drawRect(block.getX(), block.getY(), BLOCK_SIZE, BLOCK_SIZE);
            }
            if (showCoords) {
                g.setColor(Color.WHITE);
                g.drawString(block.getColumn() + ":" + block.getRow(), block.getX() + BLOCK_SIZE / 6, block.getY() + BLOCK_SIZE / 2);
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
            if (!airplane.isOnRunway()) {
                if (showStyle) {
                    BufferedImage image = getAirplaneImage(airplane);
                    if (image != null) {
                        image = rotateImage(image, airplane.getAngle());
//                        g.drawImage(image, airplane.getX(), airplane.getY(), null);
                        g.drawImage(image, airplane.getX() + BLOCK_SIZE / 12, airplane.getY() + BLOCK_SIZE / 12,
                                (int) (38 * SCALING_WIDTH_RATIO), (int) (40 * SCALING_HEIGHT_RATIO), null);

                    } else {
                        //traitement
                    }
                } else {

                    int x = airplane.getX() + BLOCK_SIZE / 4;
                    int y = airplane.getY() + BLOCK_SIZE / 4;
                    int width = (BLOCK_SIZE - 5) / 2;
                    int height = (BLOCK_SIZE - 5) / 2;

                    g.setColor(airplane.isWaiting() ? Color.YELLOW : Color.GREEN);
                    g.fillOval(x, y, width, height);
                    g.drawString(airplane.getReference(), x - (BLOCK_SIZE / 2) - (BLOCK_SIZE / 6), y - (BLOCK_SIZE / 6));

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
            g.drawString(airport.getAmountAirplanesOnRunway() + "/" + airport.getMaxAirplanes(), x, y);
        }
    }

    public void paintFlights(Graphics g) {
        if (showTrajects) {

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(FLIGHT_STROKE);

            int x1, x2, y1, y2;

            for (Flight flight : flights) {
//                if (true) {
                if (flight.isRunning() || showFlights) {
//                    Airplane airplane = flight.getAirplane();

                    ArrayList<Position> path = flight.getPath();

                    g2d.setColor(Color.WHITE);

                    for (int i = -1; i < path.size(); i++) {

                        if (flight.getCurrentIndex() - 1 == i || flight.getCurrentIndex() == 0) {
                            g2d.setColor(FLIGHT_STROKE_COLOR);
//                            g2d.setColor(Color.YELLOW);
                        }
                        if (i == -1) {
                            x1 = flight.getStartAirport().getX() + BLOCK_SIZE / 2;
                            y1 = flight.getStartAirport().getY() + BLOCK_SIZE / 2;
                            x2 = path.get(0).getX() + BLOCK_SIZE / 2;
                            y2 = path.get(0).getY() + BLOCK_SIZE / 2;

                        } else if (i == path.size() - 1) {
                            x1 = path.get(path.size() - 1).getX() + BLOCK_SIZE / 2;
                            y1 = path.get(path.size() - 1).getY() + BLOCK_SIZE / 2;
                            x2 = flight.getDestinationAirport().getX() + BLOCK_SIZE / 2;
                            y2 = flight.getDestinationAirport().getY() + BLOCK_SIZE / 2;

                        } else {
                            Position p1 = path.get(i);
                            Position p2 = path.get(i + 1);
                            x1 = p1.getX() + BLOCK_SIZE / 2;
                            y1 = p1.getY() + BLOCK_SIZE / 2;
                            x2 = p2.getX() + BLOCK_SIZE / 2;
                            y2 = p2.getY() + BLOCK_SIZE / 2;
                        }
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
                AirZone airZone = airZones.get(i);

                if (!(i % Config.AIR_ZONE_GAP == 0)) {
                    isColored = !isColored;
                }

                g2d.setColor(airZone.isOccupied() ? Color.RED : isColored ? new Color(168, 168, 168) : Color.WHITE);

                for (Position block : airZone.getPositions()) {
                    g.fillRect(block.getX(), block.getY(), BLOCK_SIZE, BLOCK_SIZE);
                }

//                if (airZone.isOccupied()) {
//                    if (airZone.getAirplane().isWaiting()) {
//                        System.out.println(airZone.getAirplane().getPosition());
//                        g.setColor(Color.YELLOW);
//
//                        g.fillRect(airZone.getAirplane().getX(), airZone.getAirplane().getY(), BLOCK_SIZE, BLOCK_SIZE);
//                    }
//                }

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
        paintAirZones(g);
        paintAirplanes(g);
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
