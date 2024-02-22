package ihm.buttons.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonHoverEffect extends MouseAdapter {
    private JButton button;

    public ButtonHoverEffect(JButton button) {
        this.button = button;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        button.setBackground(new Color(70, 70, 70));
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        button.setBackground(new Color(50, 50, 50));
    }
}
