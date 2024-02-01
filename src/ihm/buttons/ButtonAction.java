package ihm.buttons;

import ihm.AerialTrafficPanel;

import javax.swing.*;

public class ButtonAction {

    private final JButton button;
    private AerialTrafficPanel aerialTrafficPanel;

    public ButtonAction(JButton button, AerialTrafficPanel aerialTrafficPanel) {
        this.button = button;
        this.aerialTrafficPanel = aerialTrafficPanel;
    }

    public ButtonAction(JButton button) {
        this.button = button;
        this.aerialTrafficPanel = null;
    }

    public JButton getButton() {
        return button;
    }

    public AerialTrafficPanel getAerialTrafficPanel() {
        return aerialTrafficPanel;
    }

    public void replaceToggleButtonText(JButton button) {
        if (button.getText().contains("Show")) {
            button.setText(button.getText().replace("Show", "Hide"));
        } else {
            button.setText(button.getText().replace("Hide", "Show"));
        }
    }
}