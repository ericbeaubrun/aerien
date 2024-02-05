package ihm.infodisplay;

import data.Airplane;
import engine.SelectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirportInfoButtonListener implements ActionListener {

    private final SelectionListener selectionListener;

    private Airplane airplane;

    public AirportInfoButtonListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectionListener.setSelectedAirplane(airplane);
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
}
