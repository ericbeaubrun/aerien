package engine;

import data.Airport;
import data.Airplane;
import data.MapField;
import data.Position;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.ConversionUtility;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

import static util.ConversionUtility.blockToPixel;

public class SimulationInitializer {

    private static final String CONFIG_FILE_PATH = "src/config/config.xml";

    private static InputStream getConfigFileInputStream(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }

    private static Element getConfigFileElement(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream).getDocumentElement();
    }

    private static void initAirports(Element element, AirportManager airports) {
        NodeList airportsNodes = element.getElementsByTagName("Airport");

        for (int i = 0; i < airportsNodes.getLength(); i++) {
            Element airportElement = (Element) airportsNodes.item(i);

            String name = airportElement.getAttribute("Name");
            int airplaneMax = Integer.parseInt(airportElement.getAttribute("AirplaneMax"));
            int x = blockToPixel(airportElement.getAttribute("X"));
            int y = blockToPixel(airportElement.getAttribute("Y"));

            Airport airport = new Airport(x, y, name, airplaneMax);
            airports.add(airport);
        }
    }

    private static void initFlights(Element element, MapField mapField, AirportManager airports, FlightManager flights) {
        NodeList flightNodes = element.getElementsByTagName("Flight");

        for (int i = 0; i < flightNodes.getLength(); i++) {
            Element flightElement = (Element) flightNodes.item(i);

            String enabled = flightElement.getAttribute("Enabled");
            if (!enabled.equals("false")) {
                // Extraction dans le fichier xml
                String startAirportX = flightElement.getAttribute("StartAirportX");
                String startAirportY = flightElement.getAttribute("StartAirportY");
                Position startAirportPos = new Position(blockToPixel(startAirportX), blockToPixel(startAirportY));

                String destinationAirportX = flightElement.getAttribute("DestinationAirportX");
                String destinationAirportY = flightElement.getAttribute("DestinationAirportY");
                Position destinationAirportPos = new Position(blockToPixel(destinationAirportX), blockToPixel(destinationAirportY));

                // Creation de l'objet Flight
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

                flights.addFlight(flight);
            }
        }
    }

    private static void initAirplanes(Element element, AirportManager airports, ArrayList<Airplane> airplanes) {
        NodeList airplaneNodes = element.getElementsByTagName("Airplane");
        for (int i = 0; i < airplaneNodes.getLength(); i++) {
            Element airplaneElement = (Element) airplaneNodes.item(i);

            int x = blockToPixel(airplaneElement.getAttribute("X"));
            int y = blockToPixel(airplaneElement.getAttribute("Y"));
            int maxSpeed = Integer.parseInt(airplaneElement.getAttribute("MaxSpeed"));
            int maxFuel = Integer.parseInt(airplaneElement.getAttribute("MaxFuel"));
            int comFrequency = Integer.parseInt(airplaneElement.getAttribute("ComFreq"));
            String imagePath = airplaneElement.getAttribute("ImagePath");

            String reference = airplaneElement.getAttribute("Reference");
            Airplane airplane = new Airplane(x, y, reference, maxFuel, maxSpeed, comFrequency);

            Airport unfilledAirport = airports.getRandomSpawnAirport();
            if (unfilledAirport != null) {
                unfilledAirport.addAirplane(airplane);
                airplanes.add(airplane);
            }
        }
    }

    public static void initSimulationObjects(MapField mapField, AirportManager airports, ArrayList<Airplane> airplanes, FlightManager flights) {
        try {
            InputStream inputStream = getConfigFileInputStream(CONFIG_FILE_PATH);
            Element root = getConfigFileElement(inputStream);

            initAirports(root, airports);
            initFlights(root, mapField, airports, flights);
            initAirplanes(root, airports, airplanes);

            inputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}





