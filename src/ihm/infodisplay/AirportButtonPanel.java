package ihm.infodisplay;

import config.Config;
import config.IHMConfig;
import data.Airplane;
import engine.Flight;
import engine.SelectionListener;
import ihm.buttons.listeners.AirportInfoButtonListener;
import util.ImageUtility;

import javax.swing.*;
import java.awt.*;

public class AirportButtonPanel extends JPanel {

    private final JLabel label = new JLabel();

    private final JButton button = new JButton();

    private final JLabel stateLabel = new JLabel();

    private final JLabel imageLabel = new JLabel();

    public AirportButtonPanel(int width) {
        setBorder(IHMConfig.BUTTON_PANEL_BORDER);
        setBackground(IHMConfig.BACKGROUND_COLOR);
        stateLabel.setForeground(IHMConfig.BASIC_TEXT_COLOR);
        stateLabel.setFont(IHMConfig.BUTTON_FONT);

        button.setPreferredSize(new Dimension((Config.BLOCK_SIZE / 2) * 10, Config.BLOCK_SIZE / 2));
        button.setForeground(IHMConfig.BASIC_TEXT_COLOR);
        button.setBackground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFont(IHMConfig.BUTTON_FONT);

        add(label);
        add(button);
        add(imageLabel);
        add(stateLabel);
    }

    public void setAirplane(Airplane airplane) {
        if (airplane != null) {
            AirportInfoButtonListener listener = (AirportInfoButtonListener) button.getActionListeners()[0];
            listener.setAirplane(airplane);
        }
    }

    public void updatePanel(Flight flight) {
        if (flight != null && flight.getAirplane() != null) {

            button.setText(flight.getAirplane().getReference());

            String color;
            String path = Config.RESSOURCES_PATH + "planes/";
            String imageName = ImageUtility.getPlaneImageName(flight.getAirplane());

            int count = flight.getCountdown();
            if (count >= 0) {
                if (count == 0) {
                    color = "green";
                    stateLabel.setForeground(IHMConfig.RUNNING_FLIGHT_COLOR);
                    stateLabel.setText(flight + " :  STARTING");
                } else {
                    color = "white";
                    stateLabel.setForeground(IHMConfig.BASIC_FLIGHT_COLOR);
                    stateLabel.setText(flight + " : (" + count + ")");
                }
            } else {
                color = "red";
                stateLabel.setForeground(IHMConfig.DELAYED_FLIGHT_COLOR);
                stateLabel.setText(flight + " : DELAYED");
            }

            if (imageName != null) {
                path += imageName + "_" + color + ".png";
                imageLabel.setIcon(new ImageIcon(path));
            }
        }
    }

    public void updatePanel(Airplane airplane) {
        if (airplane != null) {
            String path = Config.RESSOURCES_PATH + "planes/" + ImageUtility.getPlaneImageName(airplane) + "_yellow.png";
            imageLabel.setIcon(new ImageIcon(path));
            button.setText(airplane.getReference());
            stateLabel.setForeground(IHMConfig.UNPLANNED_FLIGHT_COLOR);
            stateLabel.setText(airplane.isAvailable() ? "UNPLANNED" : "UNKNOWN");
        }
    }

    public void initActionListener(SelectionListener selectionListener) {
        button.addActionListener(new AirportInfoButtonListener(selectionListener));
    }
}
