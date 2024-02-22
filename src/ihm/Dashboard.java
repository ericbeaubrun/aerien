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

/**
 * This class allows to display all graphical Simulation component with buttons, aerial traffic panel and entities information.
 */
public class Dashboard extends JPanel implements Runnable {

    private int speed = Config.DEFAULT_SIMULATION_SPEED / 10;

    private boolean isRunning = true;

    private final ButtonsPanel buttonsPanel;

    private final DisplayInfoPanel displayInfoPanel;

    private final AerialTrafficPanel aerialTrafficPanel;

    private final SelectionListener selectionListener;

    public Dashboard(Simulation simulation, int width, int height) {

        aerialTrafficPanel = new AerialTrafficPanel(simulation.getMap(), simulation.getAirplanes(), simulation.getAirports(), simulation.getFlights());
        displayInfoPanel = new DisplayInfoPanel(width);
        selectionListener = new SelectionListener(displayInfoPanel, simulation.getFlights(), simulation.getAirports());
        displayInfoPanel.setSelectionListener(selectionListener);
        buttonsPanel = new ButtonsPanel(width, simulation, aerialTrafficPanel, simulation.getTime(), this);

        aerialTrafficPanel.addMouseListener(selectionListener);

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        scrollPane.getVerticalScrollBar().setUnitIncrement(22);

        add(scrollPane, BorderLayout.WEST);
        add(displayInfoPanel, BorderLayout.EAST);
        add(aerialTrafficPanel, BorderLayout.CENTER);
    }

    @Override
    public void run() {
        while (isRunning) {
            try {

                Thread.sleep(speed);

                buttonsPanel.refreshTime();
                buttonsPanel.refreshSpeed(speed);

                Flight selectedFlight = selectionListener.getSelectedFlight();
                Airplane selectedAirplane = selectionListener.getSelectedAirplane();
                Airport selectedAirport = selectionListener.getSelectedAirport();

                if (selectedAirport == null && selectedFlight != null && selectedAirplane != null && !selectedFlight.isRunning()) {
                    // When landing
                    selectionListener.setSelectedFlight(null);
                    selectionListener.setSelectedAirport(selectedFlight.getStartAirport()); // destinationAirport()
                    displayInfoPanel.toggleAirportPanelVisible();
                } else if (selectedAirplane != null && !selectedAirplane.isOnRunway() && selectedFlight == null && selectedAirport != null) {
                    // When taking off
                    selectionListener.associateSelectedAirplaneToFlight();
                    selectionListener.setSelectedAirport(null);
                    displayInfoPanel.toggleFlightPanelVisible();
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
                e.printStackTrace();
            }
        }
    }

    public void increaseSpeed() {
        if (speed > Config.MIN_SIMULATION_SPEED / 10) {
            speed -= Config.STEP_SIMULATION_SPEED / 10;
        }
    }

    public void decreaseSpeed() {
        if (speed < Config.MAX_SIMULATION_SPEED / 10) {
            speed += Config.STEP_SIMULATION_SPEED / 10;
        }
    }
}
