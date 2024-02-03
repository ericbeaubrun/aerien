package ihm.buttons.listeners;

import engine.Flight;
import engine.Simulation;
import engine.TimeCounter;
import ihm.SimulationPanel;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TogglePauseButtonAction extends ButtonAction implements ActionListener {

    private final Simulation simulation;
    private final TimeCounter time;

    public TogglePauseButtonAction(JButton button, Simulation simulation, TimeCounter time) {
        super(button);
        this.simulation = simulation;
        this.time = time;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.togglePause();
        time.togglerPaused();
    }
}
