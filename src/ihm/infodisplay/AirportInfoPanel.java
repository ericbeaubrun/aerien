package ihm.infodisplay;

import config.Config;
import data.Airplane;
import data.Airport;
import engine.Flight;
import engine.SelectionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AirportInfoPanel extends JPanel {

    private final ArrayList<JButton> buttons = new ArrayList<>();

    public void initButtons(int amount) {
        for (int i = 0; i <= amount; i++) {
            JButton button = new JButton();
            button.setVisible(false);
            buttons.add(button);
            add(button);
        }
    }

    public void updateAirportInfo(ArrayList<Flight> flights, Airport airport) {

        if (airport != null) {
            ArrayList<Airplane> exceptedAirplanes = new ArrayList<>();
//            removeAll();

            int currentButtonIndex = 0;

            for (Flight flight : flights) {
                if (flight.getStartAirport().equals(airport) && !flight.isRunning()) { //&& !flight.isRunning()
                    Airplane airplane = airport.getFirstAvailableAirplaneExcept(exceptedAirplanes);
                    if (airplane != null) {
                        exceptedAirplanes.add(airplane);
                        JButton button = buttons.get(currentButtonIndex);
                        button.setVisible(true);

//                        Permet de cliquer sur le bouton du panel info aeroport afin de suivre l'avion quand il décolerra
                        AirportInfoButtonListener listener = (AirportInfoButtonListener) button.getActionListeners()[0];
                        listener.setAirplane(airplane);

                        button.setText(flight.toString() + " " + airplane.getReference() + " : " + flight.getCountBeforeTakeoff());
                        currentButtonIndex++;
                        //                        JButton button = new JButton(airplane.getReference() + " " + flight.getCountBeforeTakeoff());
                        //                        add(button);
                    }
                }
            }

            ArrayList<Airplane> airplanes = airport.getAirplanes();

            for (Airplane airplane : airplanes) {
                if (!exceptedAirplanes.contains(airplane)) {
                    JButton button = buttons.get(currentButtonIndex);
                    button.setText(airplane.getReference() + " U/D"); // UNPLANNED DEPARTURE
                    button.setVisible(true);
                    AirportInfoButtonListener listener = (AirportInfoButtonListener) button.getActionListeners()[0];
                    listener.setAirplane(airplane);

                    currentButtonIndex++;
                }
            }

            for (int i = currentButtonIndex; i < buttons.size(); i++) {
                buttons.get(i).setVisible(false);
            }
        }
    }

    public void setSelectionListener(SelectionListener selectionListener) {
        for (JButton button : buttons) {
            button.addActionListener(new AirportInfoButtonListener(selectionListener));
        }
    }

    public AirportInfoPanel(int width) {
        initButtons(5);
        setPreferredSize(new Dimension(width / 6, 0));
        setBackground(Config.BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

}
