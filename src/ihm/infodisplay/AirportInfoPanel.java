package ihm.infodisplay;

import config.IHMConfig;
import data.Airplane;
import data.Airport;
import engine.Flight;
import engine.SelectionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AirportInfoPanel extends JPanel {

    private final ArrayList<AirportButtonPanel> buttonPanels = new ArrayList<>();

    private final JPanel headerPanel = new JPanel();

    private final JLabel headerLabel = new JLabel();

    private final JLabel airplanesIncomingLabel = new JLabel();

    private final JLabel airplanesOnRunwaysLabel = new JLabel();


    public AirportInfoPanel(int width) {
        initHeaderPanel();
        initButtonPanels(5, width);
        setBackground(IHMConfig.BACKGROUND_COLOR);
        setLayout(new GridLayout(7, 0));
    }

    public void initHeaderPanel() {
        headerLabel.setFont(IHMConfig.HEADER_FONT);
        headerLabel.setForeground(IHMConfig.BASIC_TEXT_COLOR);
        airplanesIncomingLabel.setForeground(IHMConfig.BASIC_TEXT_COLOR);
        airplanesOnRunwaysLabel.setForeground(IHMConfig.BASIC_TEXT_COLOR);

        headerPanel.setBackground(IHMConfig.BACKGROUND_COLOR);

        headerPanel.add(headerLabel);
        headerPanel.add(airplanesIncomingLabel);
        headerPanel.add(airplanesOnRunwaysLabel);
        add(headerPanel);
    }

    public void initButtonPanels(int amount, int width) {
        for (int i = 0; i <= amount; i++) {
            AirportButtonPanel buttonPanel = new AirportButtonPanel(width);
            buttonPanel.setVisible(false);

            buttonPanels.add(buttonPanel);
            add(buttonPanel);
        }
    }

    public void updateAirportInfo(ArrayList<Flight> flights, Airport airport) {

        if (airport != null) {
            headerLabel.setText("  " + airport.getName() + " (" + airport.getMaxAirplanes()+" Max)");
            airplanesIncomingLabel.setText("Airplanes incoming = " + airport.getAmountAirplanesIncoming()+"/" + airport.getMaxAirplanes());
            airplanesOnRunwaysLabel.setText("Airplanes on runways = " + airport.getAmountAirplanesOnRunway()+"/" + airport.getMaxAirplanes());

            ArrayList<Airplane> exceptedAirplanes = new ArrayList<>();

            int i = 0;

            for (Flight flight : flights) {
                if (flight.willTakeoff(airport)) {
                    Airplane airplane = flight.getAirplane();

                    if (airplane != null) {
                        AirportButtonPanel buttonPanel = buttonPanels.get(i);
                        buttonPanel.setVisible(true);

                        if (!airplane.isAvailable()) {
                            buttonPanel.setAirplane(airplane);
                            buttonPanel.updatePanel(flight);
                            exceptedAirplanes.add(airplane);
                        }
                        i++;
                    }
                }
            }

            for (Airplane airplane : airport.getAirplanes()) {
                if (!exceptedAirplanes.contains(airplane)) {
                    AirportButtonPanel buttonPanel = buttonPanels.get(i);
                    buttonPanel.updatePanel(airplane);
                    buttonPanel.setVisible(true);
                    buttonPanel.setAirplane(airplane);
                    i++;
                }
            }

            for (int k = i; k < buttonPanels.size(); k++) {
                buttonPanels.get(k).setVisible(false);
            }
        }
    }

    public void setSelectionListener(SelectionListener selectionListener) {
        for (AirportButtonPanel buttonPanel : buttonPanels) {
            buttonPanel.initActionListener(selectionListener);
        }
    }
}
