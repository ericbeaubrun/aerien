import config.Config;
import engine.Simulation;
import ihm.SimulationPanel;
import util.ConversionUtility;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class MainWindow extends JFrame {

    private final SimulationPanel simulationPanel;

    public void startSimulation() {
        Thread simulationThread = new Thread(simulationPanel);
        simulationThread.start();
    }

    public MainWindow() {
        super();

        setSize(Config.WINDOW_DIMENSION);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Simulation simulation = new Simulation();
        simulationPanel = new SimulationPanel(simulation, getWidth(), getHeight());

        add(simulationPanel);

        setVisible(true);
        setResizable(false);
        startSimulation();
    }
}
