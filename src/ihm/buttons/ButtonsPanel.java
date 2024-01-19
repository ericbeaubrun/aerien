package ihm.buttons;

import config.Config;
import engine.TimeCounter;
import ihm.AerialTrafficPanel;
import ihm.buttons.listeners.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ButtonsPanel extends JPanel {

    private ArrayList<JButton> buttons;

    private String buttonsName[] = {"Show grid", "Show trajects", "Show style", "Show coords", "Show background", "Increase speed", "Decrease speed", "...", "...", "..."
            , "..."};

    private TimeCounter time;
    private JLabel timeLabel;

    public ButtonsPanel(int width, AerialTrafficPanel aerialTrafficPanel, TimeCounter time) {

        initTimeLabel(time);

        setPreferredSize(new Dimension((int) (Config.SCREEN_DIMENSION.getWidth() / 15), (int) (Config.SCREEN_DIMENSION.getHeight())));
        setBackground(new Color(30, 30, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        buttons = new ArrayList<>();

        add(Box.createVerticalStrut(18));
        for (String name : buttonsName) {
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
//                    button.doClick();
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
//                    button.addActionListener(new ShowTrajectsButtonAction(button, aerialTrafficPanel));
                    break;

                case "Decrease speed":
//                    button.addActionListener(new ShowTrajectsButtonAction(button, aerialTrafficPanel));
                    break;
            }
            buttons.add(button);
            add(button);
            add(Box.createVerticalStrut(18));
        }
    }

    private void initTimeLabel(TimeCounter time) {
        this.time = time;
        timeLabel = new JLabel("     " + time.toString());
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
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
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            button.addMouseListener(new ButtonHoverEffect(button));
        }
    }

    public void refreshTime() {
        time.incrementMinuts();
        timeLabel.setText("     " + time.toString());
    }
}
