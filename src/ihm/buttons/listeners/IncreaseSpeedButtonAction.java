package ihm.buttons.listeners;

import engine.Simulation;
import ihm.AerialTrafficPanel;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncreaseSpeedButtonAction extends ButtonAction implements ActionListener {

    private final Simulation simulation;

    public IncreaseSpeedButtonAction(JButton button, Simulation simulation) {
        super(button);
        this.simulation = simulation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
     simulation.increaseSimulationSpeed();
    }
}
