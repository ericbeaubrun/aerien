package ihm.infodisplay;

import config.Config;
import data.Airport;
import engine.Flight;
import engine.SelectionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DisplayInfoPanel extends JPanel {

    private final AirplaneInfoPanel airplanePanel;

    private final AirportInfoPanel airportPanel;

    public void updateFlightInfo(Flight flight) {
        airplanePanel.updateFlightInfo(flight);
    }

    public void updateAirportInfo(ArrayList<Flight> flights, Airport airport) {
        airportPanel.updateAirportInfo(flights, airport);
    }

    public void setDisplayOnlyAirportInfo() {
        airplanePanel.setVisible(false);
        airportPanel.setVisible(true);
    }

    public void setDisplayOnlyFlightInfo(){
        airplanePanel.setVisible(true);
        airportPanel.setVisible(false);
    }

    public void hideDisplay(){
        airplanePanel.setVisible(false);
        airportPanel.setVisible(false);
    }

    public void resetLabelsText() {
        airplanePanel.resetLabelsText();
    }

    public DisplayInfoPanel(int width) {
        airplanePanel = new AirplaneInfoPanel(width);
        airportPanel = new AirportInfoPanel(width);

        airplanePanel.setVisible(false);
        airportPanel.setVisible(false);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Config.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(width / 6, 0));

        add(airplanePanel);
        add(airportPanel);
    }

    public void setSelectionListener(SelectionListener selectionListener){
        airportPanel.setSelectionListener(selectionListener);
    }

}
