package ihm.buttons.listeners;

import engine.Simulation;
import ihm.Dashboard;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecreaseSpeedAction extends ButtonAction implements ActionListener {

    private final Simulation simulation;
    private final Dashboard dashboard;

    public DecreaseSpeedAction(JButton button, Simulation simulation, Dashboard dashboard) {
        super(button);
        this.simulation = simulation;
        this.dashboard = dashboard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.decreaseSpeed();
        dashboard.decreaseSpeed();
    }
}

