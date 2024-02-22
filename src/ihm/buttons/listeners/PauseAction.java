package ihm.buttons.listeners;

import config.IHMConfig;
import engine.Simulation;
import engine.TimeCounter;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseAction extends ButtonAction implements ActionListener {

    private final Simulation simulation;
    private final TimeCounter time;

    public PauseAction(JButton button, Simulation simulation, TimeCounter time) {
        super(button);
        this.simulation = simulation;
        this.time = time;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.togglePause();
        time.togglePause();
        if (isActivated()) {
            getButton().setBackground(IHMConfig.PAUSE_BUTTON_COLOR);
        } else {
            refreshButtonStyle();
        }
        toggleActivated();
    }
}
