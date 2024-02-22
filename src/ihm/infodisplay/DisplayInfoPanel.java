package ihm.infodisplay;

import config.IHMConfig;
import data.Airport;
import engine.Flight;
import engine.SelectionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class allows to display Airports and Airplanes information in real time during the simulation.
 */
public class DisplayInfoPanel extends JPanel {

    private final AirplaneInfoPanel airplanePanel;

    private final AirportInfoPanel airportPanel;

    public void updateFlightInfo(Flight flight) {
        airplanePanel.updateFlightInfo(flight);
    }

    public void updateAirportInfo(ArrayList<Flight> flights, Airport airport) {
        airportPanel.updateAirportInfo(flights, airport);
    }

    public void toggleAirportPanelVisible() {
        airplanePanel.setVisible(false);
        airportPanel.setVisible(true);
    }

    public void toggleFlightPanelVisible() {
        airplanePanel.setVisible(true);
        airportPanel.setVisible(false);
    }

    /**
     * Hide all Panels.
     */
    public void hideDisplay() {
        airplanePanel.setVisible(false);
        airportPanel.setVisible(false);
    }

    public DisplayInfoPanel(int width) {
        airplanePanel = new AirplaneInfoPanel(width);
        airportPanel = new AirportInfoPanel(width);

        airplanePanel.setVisible(false);
        airportPanel.setVisible(false);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(IHMConfig.BACKGROUND_COLOR);
        setPreferredSize(new Dimension((width / 8) + (width / 32) , 0));

        add(airplanePanel);
        add(airportPanel);
    }

    public void setSelectionListener(SelectionListener selectionListener) {
        airportPanel.setSelectionListener(selectionListener);
    }
}
