package ihm.buttons.listeners;

import ihm.AerialTrafficPanel;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowFlightsButtonAction extends ButtonAction implements ActionListener {

    public ShowFlightsButtonAction(JButton button, AerialTrafficPanel aerialTrafficPanel) {
        super(button, aerialTrafficPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getAerialTrafficPanel().toggleShowFlights();
        replaceToggleButtonText(getButton());
    }
}
