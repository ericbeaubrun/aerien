import config.IHMConfig;
import engine.Simulation;
import ihm.Dashboard;

import javax.swing.*;

/**
 * This class represents the Main Windows that contains all Panel of the Simulation.
 */
public class MainWindow extends JFrame {

    // This is a Thread for the Visual part of the Simulation
    private final Dashboard simulationIHM;

    // This is a Thread for the Engine part of the Simulation
    private final Simulation simulationEngine;

    public MainWindow() {
        super();

        setSize(IHMConfig.WINDOW_DIMENSION);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        simulationEngine = new Simulation();
        simulationIHM = new Dashboard(simulationEngine, getWidth(), getHeight());

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
