package ihm;

import config.Config;
import data.Airplane;
import data.Airport;
import engine.Flight;
import engine.SelectionListener;
import engine.Simulation;
import ihm.buttons.ButtonsPanel;
import ihm.infodisplay.DisplayInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulationPanel extends JPanel implements Runnable {

    private boolean isRunning = true;

    private final ButtonsPanel buttonsPanel;

    private final DisplayInfoPanel displayInfoPanel;

    private final AerialTrafficPanel aerialTrafficPanel;

    private final SelectionListener selectionListener;

    public SimulationPanel(Simulation simulation, int width, int height) {

        aerialTrafficPanel = new AerialTrafficPanel(simulation.getMapField(), simulation.getAirplanes(), simulation.getAirports(), simulation.getFlights());
        displayInfoPanel = new DisplayInfoPanel(width);
        selectionListener = new SelectionListener(displayInfoPanel, simulation.getFlights(), simulation.getAirports());
        buttonsPanel = new ButtonsPanel(width, simulation, aerialTrafficPanel, simulation.getTime());

        aerialTrafficPanel.addMouseListener(selectionListener);

        setLayout(new BorderLayout());

        add(buttonsPanel, BorderLayout.WEST);
        add(displayInfoPanel, BorderLayout.EAST);
        add(aerialTrafficPanel, BorderLayout.CENTER);
    }


    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(Config.SIMULATION_SPEED);

                buttonsPanel.refreshTime();

                Flight selectedFlight = selectionListener.getSelectedFlight();
                Airplane selectedAirplane = selectionListener.getSelectedAirplane();
                Airport selectedAirport = selectionListener.getSelectedAirport();

                if (selectedAirport == null && selectedFlight != null && selectedAirplane != null && selectedFlight.getAirplane() == null) {
                    // Cas d'atterissage
                    selectionListener.setSelectedFlight(null);
                    selectionListener.setSelectedAirport(selectedFlight.getDestinationAirport());
                    displayInfoPanel.setDisplayOnlyAirportInfo();
                } else if (selectedAirplane != null && !selectedAirplane.isOnTrail() && selectedFlight == null && selectedAirport != null) {
                    // Cas de décolage
                    selectionListener.associateSelectedAirplaneToFlight();
                    selectionListener.setSelectedAirport(null);
                    displayInfoPanel.setDisplayOnlyFlightInfo();
                }

                ArrayList<Flight> flights = selectionListener.getFlights();
                selectedFlight = selectionListener.getSelectedFlight();
                selectedAirport = selectionListener.getSelectedAirport();

                if (selectedAirport != null) {
                    displayInfoPanel.updateAirportInfo(flights, selectedAirport);
                }

                if (selectedFlight != null) {
                    displayInfoPanel.updateFlightInfo(selectedFlight);
                }

                displayInfoPanel.repaint();
                repaint();
            } catch (InterruptedException e) {
            }
        }
    }
}
