package ihm.infodisplay;

import javax.swing.*;

public class InfoLabel extends JLabel {

    private String initialText;

    private String unit = "";

    public InfoLabel(String initialText) {
        super(initialText);
        this.initialText = initialText;
    }

    public InfoLabel(String initialText, String unit) {
        super(initialText);
        this.unit = unit;
        this.initialText = initialText;
    }

    public InfoLabel() {
        super("");
        initialText = "";
    }

    public void updateText(String text) {
        setText(initialText + " " + text + " " + unit);
    }

    public void updateText(double text) {
        updateText(Double.toString(text));
    }

    public void updateText(int text) {
        updateText(Integer.toString(text));
    }


    public String getInitialText() {
        return initialText;
    }
}
