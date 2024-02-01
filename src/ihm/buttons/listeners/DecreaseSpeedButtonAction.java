package ihm.buttons.listeners;

import engine.Simulation;
import ihm.AerialTrafficPanel;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecreaseSpeedButtonAction extends ButtonAction implements ActionListener {

    private final Simulation simulation;

    public DecreaseSpeedButtonAction(JButton button, Simulation simulation) {
        super(button);
        this.simulation = simulation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.decreaseSimulationSpeed();
    }

}

