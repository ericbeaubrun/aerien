package ihm.buttons.listeners;

import engine.Simulation;
import ihm.Dashboard;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncreaseSpeedAction extends ButtonAction implements ActionListener {

    private final Simulation simulation;
    private final Dashboard dashboard;

    public IncreaseSpeedAction(JButton button, Simulation simulation, Dashboard dashboard) {
        super(button);
        this.simulation = simulation;
        this.dashboard = dashboard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.increaseSpeed();
        dashboard.increaseSpeed();
    }
}
