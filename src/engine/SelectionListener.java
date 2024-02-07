package engine;

import data.Airplane;
import data.Airport;
import ihm.infodisplay.DisplayInfoPanel;
import util.ConversionUtility;

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

    @Override
    public void mousePressed(MouseEvent e) {

        resetSelection();

        int x = e.getX();
        int y = e.getY();

        int column = pixelToBlock(x);
        int row = pixelToBlock(y);

        for (Airport airport : airports) {
            if (airport.getColumn() == column && airport.getRow() == row) {
                selectedAirport = airport;
                displayInfoPanel.updateAirportInfo(flights, airport);
                displayInfoPanel.setDisplayOnlyAirportInfo();
                break;
            }
        }

        if (noElementSelected()) {
            for (Flight flight : flights) {
                Airplane airplane = flight.getAirplane();
                if (airplane != null && airplane.getColumn() == column && airplane.getRow() == row) {
                    selectedFlight = flight;
                    selectedAirplane = airplane;
                    displayInfoPanel.updateFlightInfo(flight);
                    displayInfoPanel.setDisplayOnlyFlightInfo();
                    break;
                }
            }
        }

        if (noElementSelected()) {
            displayInfoPanel.resetLabelsText();
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

    public void setSelectedAirplane(Airplane selectedAirplane) {
        this.selectedAirplane = selectedAirplane;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public void setSelectedAirport(Airport selectedAirport) {
        this.selectedAirport = selectedAirport;
    }

    public ArrayList<Flight> getFlights() {
        return new ArrayList<>(flights);
    }
}
