package ihm.buttons.listeners;

import ihm.buttons.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShowPresetAction extends ButtonAction implements ActionListener {

    private final ArrayList<ButtonAction> buttonActions;
    private final String[] buttonsName;

    public ShowPresetAction(JButton button, ArrayList<ButtonAction> buttons, String[] buttonsName) {
        super(button);
        this.buttonActions = buttons;
        this.buttonsName = buttonsName;
    }

    private void disableAll() {
        for (ButtonAction buttonAction : buttonActions) {
            JButton button = buttonAction.getButton();
            if (button != null) {
                if (buttonAction.isActivated() && !button.getText().equals("Pause")
                        && !button.getText().equals("+ speed") && !button.getText().equals("- speed")
                        && !button.getText().startsWith("Preset")) {
                    buttonAction.getButton().doClick();
                }
            }
        }
    }

    private void enablePreset() {
        for (ButtonAction buttonAction : buttonActions) {
            for (String name : buttonsName) {
                JButton button = buttonAction.getButton();
                if (button != null && name.equals(button.getText()) && !buttonAction.isActivated()) {
                    button.doClick();
                    break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        disableAll();
        enablePreset();
    }
}
