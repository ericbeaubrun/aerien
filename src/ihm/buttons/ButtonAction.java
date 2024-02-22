package ihm.buttons;

import config.IHMConfig;

import javax.swing.JButton;

public class ButtonAction {

//    private static Color DISABLED_COLOR = new Color(82, 18, 18);

    private boolean isActivated;
    private final JButton button;
    private final DisplayToggle displayToggle;

    public ButtonAction(JButton button, DisplayToggle displayToggle) {
        this.button = button;
        this.displayToggle = displayToggle;
    }

    public ButtonAction(JButton button) {
        this.button = button;
        displayToggle = null;
        isActivated = true;
    }

    public JButton getButton() {
        return button;
    }

    public DisplayToggle getDisplayToggle() {
        return displayToggle;
    }

    public void toggleActivated() {
        isActivated = !isActivated;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void refreshButtonStyle() {
        button.setBackground(isActivated ? IHMConfig.ACTIVATED_COLOR : IHMConfig.DISABLED_COLOR);
    }
}