import config.Config;
import engine.Simulation;
import ihm.SimulationPanel;

import javax.swing.*;

/**
 * This class represents the Main Windows that contains all Panel of the Simulation.
 */
public class MainWindow extends JFrame {

    // This is a Thread for the Visual part of the Simulation
    private final SimulationPanel simulationIHM;

    // This is a Thread for the Engine part of the Simulation
    private final Simulation simulationEngine;

    public MainWindow() {
        super();

        setSize(Config.WINDOW_DIMENSION);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        simulationEngine = new Simulation();
        simulationIHM = new SimulationPanel(simulationEngine, getWidth(), getHeight());

        add(simulationIHM);

        setVisible(true);
        setResizable(false);

        start();
    }

    /**
     * This is the method to start the Simulation.
     * Starts Engine Thread first.
     * Then starts the IHM Thread.
     */
    public void start() {
        Thread thread = new Thread(simulationIHM);
        simulationEngine.start();
        thread.start();
    }
}
