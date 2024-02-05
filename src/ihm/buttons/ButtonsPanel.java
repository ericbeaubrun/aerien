package ihm.buttons;

import engine.Simulation;
import engine.TimeCounter;
import ihm.AerialTrafficPanel;
import ihm.SimulationPanel;
import ihm.buttons.listeners.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static config.Config.*;

public class ButtonsPanel extends JPanel {

    private final String[] BUTTONS_NAMES = {
            "Show grid", "Show trajects", "Show style", "Show coords", "Show background", "Increase speed",
            "Decrease speed", "Show flights", "Show AirZones", "Show airports", "Pause", "Show Pause", "...", "...", "...", "..."
    };

    private TimeCounter time;

    private JLabel timeLabel;

    private final ArrayList<JButton> buttons;

    private ArrayList<JButton> presetButtons;

    private final int VERTICAL_STRUT_SIZE = (int) (BLOCK_SIZE * 0.6);

    public ButtonsPanel(int width, Simulation simulation, AerialTrafficPanel aerialTrafficPanel, TimeCounter time, SimulationPanel simulationPanel) {

        initTimeLabel(time);

        buttons = new ArrayList<>();

        add(Box.createVerticalStrut(VERTICAL_STRUT_SIZE));
        for (String buttonName : BUTTONS_NAMES) {
            JButton button = new JButton(buttonName);

            initButtonStyle(button);

            addButtonActionListener(button, buttonName, aerialTrafficPanel, simulation, simulationPanel);

            buttons.add(button);

            add(button);
            add(Box.createVerticalStrut(VERTICAL_STRUT_SIZE));

//            for (JButton button1 : buttons) {
//                button.setVisible(false);
//            }
        }

        setPreferredSize(new Dimension((int) (SCREEN_DIMENSION.getWidth() / 15), (int) (SCREEN_DIMENSION.getHeight())));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    private void addButtonActionListener(JButton button, String buttonName, AerialTrafficPanel aerialTrafficPanel,
                                         Simulation simulation, SimulationPanel simulationPanel) {
        switch (buttonName) {
            case "...":
                button.setEnabled(false);
                break;

            case "Show grid":
                button.addActionListener(new ShowGridButtonAction(button, aerialTrafficPanel));
                break;

            case "Show coords":
                button.addActionListener(new ShowCoordsButtonAction(button, aerialTrafficPanel));
                break;

            case "Show background":
                button.addActionListener(new ShowBackgroundButtonAction(button, aerialTrafficPanel));
                break;

            case "Show style":
                button.addActionListener(new ShowStyleButtonAction(button, aerialTrafficPanel));
                button.doClick();
                break;

            case "Show trajects":
                button.addActionListener(new ShowTrajectsButtonAction(button, aerialTrafficPanel));
                button.doClick();
                break;

            case "Increase speed":
                button.addActionListener(new IncreaseSpeedButtonAction(button, simulation, simulationPanel));
                break;

            case "Decrease speed":
                button.addActionListener(new DecreaseSpeedButtonAction(button, simulation, simulationPanel));
                break;

            case "Show flights":
                button.addActionListener(new ShowFlightsButtonAction(button, aerialTrafficPanel));
                button.doClick();
                break;

            case "Show AirZones":
                button.addActionListener(new ShowAirZonesButtonAction(button, aerialTrafficPanel));
                button.doClick();
                break;

            case "Pause":
                button.addActionListener(new PauseButtonAction(button, simulation, time));
                break;

            case "Show Pause":
                button.addActionListener(new ShowStylePauseButtonAction(button, aerialTrafficPanel));
                break;

            case "Show airports":
                button.addActionListener(new ShowAirportsButtonAction(button, aerialTrafficPanel));
                button.doClick();
                break;

        }
    }

    private void initTimeLabel(TimeCounter time) {
        this.time = time;
        timeLabel = new JLabel("     " + time.toString());
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, (int) (BLOCK_SIZE * 0.4)));
        add(timeLabel);
    }

    public void initButtonStyle(JButton button) {
        if (button != null) {
            Dimension buttonSize = new Dimension(120, ((BLOCK_SIZE * 3) / 4) - 3);
            button.setFocusPainted(false);
            button.setMaximumSize(buttonSize);
            button.setForeground(Color.WHITE);
            button.setMinimumSize(buttonSize);
            button.setPreferredSize(buttonSize);
            button.setBackground(new Color(50, 50, 50));
            button.addMouseListener(new ButtonHoverEffect(button));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            button.setFont(new Font("Arial", Font.BOLD, (int) (BLOCK_SIZE * 0.3)));
        }
    }

    public void refreshTime() {
        time.incrementMinutes();
        timeLabel.setText(time.toString() + " UTC+1");
        timeLabel.setToolTipText("Current Day : " + time.getDaysCount());
    }
}
