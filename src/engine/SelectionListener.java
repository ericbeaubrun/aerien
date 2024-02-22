package engine;

import data.Airplane;
import data.Airport;
import data.Position;
import ihm.infodisplay.DisplayInfoPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static util.ConversionUtility.*;

public class SelectionListener implements MouseListener {

    private Flight selectedFlight;

    private Airport selectedAirport;

    private Airplane selectedAirplane;

    private final ArrayList<Flight> flights;

    private final ArrayList<Airport> airports;

    private final DisplayInfoPanel displayInfoPanel;

    public SelectionListener(DisplayInfoPanel displayInfoPanel, ArrayList<Flight> flights, ArrayList<Airport> airports) {
        this.flights = flights;
        this.airports = airports;
        this.displayInfoPanel = displayInfoPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Do nothing
    }

    public void associateSelectedAirplaneToFlight() {
        for (Flight flight : flights) {
            if (flight.getAirplane() != null && flight.getAirplane().equals(selectedAirplane)) {
                selectedFlight = flight;
            }
        }
    }

    public boolean noElementSelected() {
        return selectedFlight == null && selectedAirport == null && selectedAirplane == null;
    }

    public void resetSelection() {
        selectedFlight = null;
        selectedAirport = null;
        selectedAirplane = null;
    }

    private boolean isAirportSelection(int column, int row) {
        for (Airport airport : airports) {
            if (airport.hasSamePosition(column, row)) {
                selectedAirport = airport;
                return true;
            }
        }
        return false;
    }

    private boolean isFlightSelection(int column, int row) {
        for (Flight flight : flights) {
            for (Position position : flight.getPath()) {
                if (position.getColumn() == column && position.getRow() == row) {
                    selectedFlight = flight;
                    Airplane airplane = flight.getAirplane();
                    if (airplane != null) {
                        selectedAirplane = airplane;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAirplaneSelection(int column, int row) {
        for (Flight flight : flights) {
            Airplane airplane = flight.getAirplane();
            if (airplane != null && airplane.hasSamePosition(column, row)) {
                selectedFlight = flight;
                selectedAirplane = airplane;
                return true;
            }
        }
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        resetSelection();

        int x = e.getX();
        int y = e.getY();

        int col = pixelToBlock(x);
        int row = pixelToBlock(y);

        if (isAirportSelection(col, row)) {
            displayInfoPanel.updateAirportInfo(flights, selectedAirport);
            displayInfoPanel.toggleAirportPanelVisible();
        }

        if (noElementSelected() && isAirplaneSelection(col, row)) {
            displayInfoPanel.updateFlightInfo(selectedFlight);
            displayInfoPanel.toggleFlightPanelVisible();
        }

        if (noElementSelected()) {
            displayInfoPanel.hideDisplay();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public Airport getSelectedAirport() {
        return selectedAirport;
    }

    public Airplane getSelectedAirplane() {
        return selectedAirplane;
    }

    public ArrayList<Flight> getFlights() {
        return new ArrayList<>(flights);
    }

    public void setSelectedAirplane(Airplane selectedAirplane) {
        this.selectedAirplane = selectedAirplane;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public void setSelectedAirport(Airport selectedAirport) {
        this.selectedAirport = selectedAirport;
    }
}
