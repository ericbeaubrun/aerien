package ihm.buttons.listeners;

import ihm.buttons.DisplayToggle;
import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowGridAction extends ButtonAction implements ActionListener {

    public ShowGridAction(JButton button, DisplayToggle displayToggle) {
        super(button, displayToggle);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getDisplayToggle().toggleShowGrid();
        toggleActivated();
        refreshButtonStyle();
    }
}
