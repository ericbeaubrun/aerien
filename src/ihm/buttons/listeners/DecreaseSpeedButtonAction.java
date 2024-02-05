package ihm.buttons.listeners;

import engine.Simulation;
import ihm.SimulationPanel;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecreaseSpeedButtonAction extends ButtonAction implements ActionListener {

    private final Simulation simulation;
    private final SimulationPanel simulationPanel;

    public DecreaseSpeedButtonAction(JButton button, Simulation simulation, SimulationPanel simulationPanel) {
        super(button);
        this.simulation = simulation;
        this.simulationPanel = simulationPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.decreaseSpeed();
        simulationPanel.decreaseSpeed();
    }
}

