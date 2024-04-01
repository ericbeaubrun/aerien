package ihm.buttons;

import config.IHMConfig;
import engine.Simulation;
import engine.TimeCounter;
import ihm.AerialTrafficPanel;
import ihm.Dashboard;
import ihm.buttons.listeners.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static config.Config.*;

public class ButtonsPanel extends JPanel {

    private final String[] BUTTONS_NAMES = {
            "Pause", "+ speed", "- speed", "Grid", "Trajectories", "Airplane Style", "Coordinate", "Background",
            "Flights", "Airports", "Air zones", "Occupied zones", "Relief", "Altitude", "...",
            "Preset 1", "Preset 2", "Preset 3", "...", "..."
    };

    private TimeCounter time;

    private JLabel timeLabel;

    private JLabel speedLabel;

    private final ArrayList<JButton> buttons = new ArrayList<>();

    private final ArrayList<ButtonAction> buttonActions = new ArrayList<>();

    private final int VERTICAL_STRUT_SIZE = (int) (BLOCK_SIZE * 0.6);

    public ButtonsPanel(int width, Simulation simulation, AerialTrafficPanel aerialTrafficPanel, TimeCounter time, Dashboard simulationPanel) {

        initTimeLabel(time);
        initSpeedLabel();

        add(Box.createVerticalStrut(VERTICAL_STRUT_SIZE));

        for (String buttonName : BUTTONS_NAMES) {
            JButton button = new JButton(buttonName);

            initButtonStyle(button);

            addButtonActionListener(button, buttonName, aerialTrafficPanel.getDisplayToggle(), simulation, simulationPanel);

            buttons.add(button);

            add(button);
            add(Box.createVerticalStrut(VERTICAL_STRUT_SIZE));
        }

        setPreferredSize(new Dimension((int) (SCREEN_DIMENSION.getWidth() / 15), (int) (SCREEN_DIMENSION.getHeight() * 1)));
        setBackground(IHMConfig.BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    private void initTimeLabel(TimeCounter time) {
        this.time = time;
        timeLabel = new JLabel("     " + time.toString());
        timeLabel.setForeground(IHMConfig.BASIC_TEXT_COLOR);
        timeLabel.setFont(IHMConfig.LABEL_FONT_1);
        add(timeLabel);
    }

    private void initSpeedLabel() {
        speedLabel = new JLabel("(x1)");
        speedLabel.setForeground(IHMConfig.BASIC_TEXT_COLOR);
        speedLabel.setFont(IHMConfig.LABEL_FONT_1);
        add(speedLabel);
    }

    public void initButtonStyle(JButton button) {
        if (button != null) {
            Dimension buttonSize = new Dimension(140, ((BLOCK_SIZE * 3) / 4) - 3);
            button.setFocusPainted(false);
            button.setMaximumSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setPreferredSize(buttonSize);
            button.setForeground(IHMConfig.BASIC_TEXT_COLOR);
            button.setBackground(Color.BLACK);
            button.addMouseListener(new ButtonHoverEffect(button));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            button.setFont(IHMConfig.BUTTON_FONT);
        }
    }

    public void refreshSpeed(int speed) {
        String s = "(x";
        switch (speed) {
            case 20:
                s += "4";
                break;

            case 40:
                s += "2";
                break;

            case 60:
                s += "1.25";
                break;

            case 80:
                s += "1";
                break;

            case 100:
                s += "0.8";
                break;

            case 120:
                s += "0.7";
                break;

            case 140:
                s += "0.6";
                break;

            case 160:
                s += "0.5";
                break;
        }
        speedLabel.setText(s + ")");
    }

    public void refreshTime() {
        time.incrementMinutes();
        timeLabel.setText(time.toString() + " UTC+1");
        timeLabel.setToolTipText("Current Day : " + time.getDay());
    }

    private void addButtonActionListener(JButton button, String buttonName, DisplayToggle displayToggle,
                                         Simulation simulation, Dashboard simulationPanel) {
        switch (buttonName) {
            case "...":
                button.setEnabled(false);
                break;

            case "Grid":
                ShowGridAction showGridAction = new ShowGridAction(button, displayToggle);
                addButtonAction(button, showGridAction);
                break;

            case "Coordinate":
                ShowCoordsAction showCoordsAction = new ShowCoordsAction(button, displayToggle);
                addButtonAction(button, showCoordsAction);
                break;

            case "Background":
                ShowBackgroundAction showBackgroundAction = new ShowBackgroundAction(button, displayToggle);
                addButtonAction(button, showBackgroundAction);
                button.doClick();
                break;

            case "Airplane Style":
                ShowStyleAction showStyleAction = new ShowStyleAction(button, displayToggle);
                addButtonAction(button, showStyleAction);
                button.doClick();
                break;

            case "Trajectories":
                ShowTrajectsAction showTrajectsAction = new ShowTrajectsAction(button, displayToggle);
                addButtonAction(button, showTrajectsAction);
                button.doClick();
                break;

            case "+ speed":
                IncreaseSpeedAction increaseSpeedAction = new IncreaseSpeedAction(button, simulation, simulationPanel);
                addButtonAction(button, increaseSpeedAction);
                break;

            case "- speed":
                DecreaseSpeedAction decreaseSpeedAction = new DecreaseSpeedAction(button, simulation, simulationPanel);
                addButtonAction(button, decreaseSpeedAction);
                break;

            case "Flights":
                ShowFlightsAction showFlightsAction = new ShowFlightsAction(button, displayToggle);
                addButtonAction(button, showFlightsAction);
                break;

            case "Air zones":
                ShowAirZonesAction showAirZonesAction = new ShowAirZonesAction(button, displayToggle);
                addButtonAction(button, showAirZonesAction);
                break;

            case "Pause":
                PauseAction pauseAction = new PauseAction(button, simulation, time);
                addButtonAction(button, pauseAction);
                break;

            case "Airports":
                ShowAirportsAction showAirportsAction = new ShowAirportsAction(button, displayToggle);
                addButtonAction(button, showAirportsAction);
                button.doClick();
                break;

            case "Occupied zones":
                ShowOccupiedZonesAction showOccupiedZonesAction = new ShowOccupiedZonesAction(button, displayToggle);
                addButtonAction(button, showOccupiedZonesAction);
                break;

            case "Altitude":
                ShowAltitudeAction showAltitudeAction = new ShowAltitudeAction(button, displayToggle);
                addButtonAction(button, showAltitudeAction);
                break;

            case "Relief":
                ShowReliefAction showReliefAction = new ShowReliefAction(button, displayToggle);
                addButtonAction(button, showReliefAction);
                break;

            case "Preset 1":
                String[] names1 = {"Trajectories", "Airplane Style", "Background", "Airports"};
                button.addActionListener(new ShowPresetAction(button, buttonActions, names1));
                break;

            case "Preset 2":
                String[] names2 = {"Flights", "Air zones", "Airports", "Occupied zones"};
                button.addActionListener(new ShowPresetAction(button, buttonActions, names2));
                break;

            case "Preset 3":
                String[] names3 = {"Grid", "Airplane Style", "Airplanes Style", "Background", "Relief", "Altitude"};
                button.addActionListener(new ShowPresetAction(button, buttonActions, names3));
                break;
        }
    }

    private void addButtonAction(JButton button, ButtonAction buttonAction) {
        buttonActions.add(buttonAction);
        button.addActionListener((ActionListener) buttonAction);
    }
}
