package ihm.buttons;

import engine.Simulation;
import engine.TimeCounter;
import ihm.AerialTrafficPanel;
import ihm.buttons.listeners.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static config.Config.*;

public class ButtonsPanel extends JPanel {

    private final String[] BUTTONS_NAMES = {
            "Show grid", "Show trajects", "Show style", "Show coords", "Show background", "Increase speed",
            "Decrease speed", "Show flights", "Show AirZones", "Show airports", "...", "...", "...", "...", "...", "..."
    };

    private TimeCounter time;

    private JLabel timeLabel;

    private final ArrayList<JButton> buttons;

    private ArrayList<JButton> presetButtons;

    private final int VERTICAL_STRUT_SIZE = (int) (BLOCK_SIZE * 0.6);

    public ButtonsPanel(int width, Simulation simulation, AerialTrafficPanel aerialTrafficPanel, TimeCounter time) {

        initTimeLabel(time);

        setPreferredSize(new Dimension((int) (SCREEN_DIMENSION.getWidth() / 15), (int) (SCREEN_DIMENSION.getHeight())));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        buttons = new ArrayList<>();

        add(Box.createVerticalStrut(VERTICAL_STRUT_SIZE));
        for (String name : BUTTONS_NAMES) {
            JButton button = new JButton(name);
            initButtonStyle(button);
            switch (name) {
                case "...":
                    button.setEnabled(false);
                    break;

                case "Show grid":
                    button.addActionListener(new ShowGridButtonAction(button, aerialTrafficPanel));
//                    button.doClick();
                    break;

                case "Show coords":
                    button.addActionListener(new ShowCoordsButtonAction(button, aerialTrafficPanel));
//                    if (ALLOW_COORD_DISPLAY) button.doClick();
                    break;

                case "Show background":
                    button.addActionListener(new ShowBackgroundButtonAction(button, aerialTrafficPanel));
                    button.doClick();
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
                    button.addActionListener(new IncreaseSpeedButtonAction(button, simulation));
                    break;

                case "Decrease speed":
                    button.addActionListener(new DecreaseSpeedButtonAction(button, simulation));
                    break;

                case "Show flights":
                    //add action listenner
                    break;

                case "Show AirZones":
                    button.addActionListener(new ShowAirZonesButtonAction(button, aerialTrafficPanel));
                    break;
            }
            buttons.add(button);
            add(button);
            add(Box.createVerticalStrut(VERTICAL_STRUT_SIZE));

//            for (JButton button1 : buttons) {
//                button.setVisible(false);
//            }
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
            Dimension buttonSize = new Dimension(120, 30);
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setFocusPainted(false);
            button.setBackground(new Color(50, 50, 50));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, (int) (BLOCK_SIZE*0.3)));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            button.addMouseListener(new ButtonHoverEffect(button));
        }
    }

    public void refreshTime() {
        time.incrementMinuts();
//        timeLabel.setText("     " + time.toString());
        timeLabel.setText(time.toString() + " UTC+1");
    }
}
