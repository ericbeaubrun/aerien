package ihm.buttons.listeners;

import ihm.buttons.DisplayToggle;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowAltitudeAction extends ButtonAction implements ActionListener {

    public ShowAltitudeAction(JButton button, DisplayToggle displayToggle) {
        super(button, displayToggle);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getDisplayToggle().toggleShowAltitude();
        toggleActivated();
        refreshButtonStyle();
    }
}
