package ihm.buttons.listeners;

import engine.Simulation;
import engine.TimeCounter;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TogglePauseAction extends ButtonAction implements ActionListener {

    private final Simulation simulation;
    private final TimeCounter time;

    public TogglePauseAction(JButton button, Simulation simulation, TimeCounter time) {
        super(button);
        this.simulation = simulation;
        this.time = time;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.togglePause();
        time.togglePause();
    }
}
