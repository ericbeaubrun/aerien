package engine;

import config.Config;
import data.Airport;
import data.Airplane;
import data.MapField;
import data.Position;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

import static util.ConversionUtility.blockToPixel;

public class SimulationInitializer {


    private InputStream inputStream;

    private Element root;

    public SimulationInitializer() {
        try {
            inputStream = getConfigFileInputStream(Config.CONFIG_FILE_PATH);
            root = getConfigFileElement(inputStream);

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public InputStream getConfigFileInputStream(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }

    public Element getConfigFileElement(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream).getDocumentElement();
    }

    public MapField initMap() {
        return new MapField(Config.COLUMNS, Config.ROWS, Config.BLOCK_SIZE);
    }

    public AirportManager initAirports() {
        AirportManager airports = new AirportManager();
        NodeList airportsNodes = root.getElementsByTagName("Airport");

        for (int i = 0; i < airportsNodes.getLength(); i++) {
            Element airportElement = (Element) airportsNodes.item(i);
            String name = airportElement.getAttribute("Name");

            int airplaneMax = Integer.parseInt(airportElement.getAttribute("AirplaneMax"));
            int x = blockToPixel(airportElement.getAttribute("X"));
            int y = blockToPixel(airportElement.getAttribute("Y"));

            Airport airport = new Airport(x, y, name, airplaneMax);
            airports.add(airport);
        }
        return airports;
    }

    public FlightManager initFlights(MapField mapField, AirportManager airports) {
        FlightManager flightManager = new FlightManager(mapField);
        NodeList flightNodes = root.getElementsByTagName("Flight");

        for (int i = 0; i < flightNodes.getLength(); i++) {
            Element flightElement = (Element) flightNodes.item(i);

            String enabled = flightElement.getAttribute("Enabled");
            if (!enabled.equals("false")) {

                String startAirportX = flightElement.getAttribute("StartAirportX");
                String startAirportY = flightElement.getAttribute("StartAirportY");
                Position startAirportPos = new Position(blockToPixel(startAirportX), blockToPixel(startAirportY));
                String destinationAirportX = flightElement.getAttribute("DestinationAirportX");
                String destinationAirportY = flightElement.getAttribute("DestinationAirportY");
                Position destinationAirportPos = new Position(blockToPixel(destinationAirportX), blockToPixel(destinationAirportY));

                Flight flight = new Flight(mapField, airports, startAirportPos, destinationAirportPos);

                // Ajout de chaque Block dans l'objet Flight
                NodeList posNodes = flightElement.getElementsByTagName("pos");
                for (int j = 0; j < posNodes.getLength(); j++) {
                    Element posElement = (Element) posNodes.item(j);
                    int x = blockToPixel(posElement.getAttribute("X"));
                    int y = blockToPixel(posElement.getAttribute("Y"));
                    Position block = new Position(x, y);
                    flight.addNextBlock(block);
                }

                flightManager.addFlight(flight);
            }
        }
        return flightManager;
    }

    public ArrayList<Airplane> initAirplanes(AirportManager airports) {
        ArrayList<Airplane> airplanes = new ArrayList<>();
        int k = 0;

        NodeList airplaneNodes = root.getElementsByTagName("Airplane");
        for (int i = 0; i < airplaneNodes.getLength(); i++) {
            Element airplaneElement = (Element) airplaneNodes.item(i);

            int x = blockToPixel(airplaneElement.getAttribute("X"));
            int y = blockToPixel(airplaneElement.getAttribute("Y"));
            int maxSpeed = Integer.parseInt(airplaneElement.getAttribute("MaxSpeed"));
            int maxFuel = Integer.parseInt(airplaneElement.getAttribute("MaxFuel"));
            int comFrequency = Integer.parseInt(airplaneElement.getAttribute("ComFreq"));

            String reference = airplaneElement.getAttribute("Reference");
            Airplane airplane = new Airplane(x, y, reference, maxFuel, maxSpeed, comFrequency);

            Airport unfilledAirport = airports.getRandomSpawnAirport();
            if (unfilledAirport != null) {
                unfilledAirport.addAirplane(airplane);
                unfilledAirport.decrementAvailableRunwayCount();
                airplanes.add(airplane);
            } else {
                k++;
            }
        }
        // When there are not enough places in airports to initialize all airplanes
        if (k > 0) {
            System.err.println(k + " avion(s) n'ont pas pu être initialisés.");
        }

        return airplanes;
    }

    public void terminateInitialisation() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





