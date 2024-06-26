package config;

import util.ConversionUtility;

import java.awt.*;

public class Config {

    // ******************************************************************************************************************************************************

    public static int zoneHeight = 2;

    public static int zoneWidth = 3;

    public static boolean allowAlwaysEmergency = false;

    public static boolean emergencyEnabled = true;

    public static boolean allowBalancedReverseFlightDirection = false;

    // ******************************************************************************************************************************************************
    public static Dimension SCREEN_DIMENSION = ConversionUtility.convertToSimulationDimension(Toolkit.getDefaultToolkit().getScreenSize());

    public static final int BLOCK_SIZE = (int) ((SCREEN_DIMENSION.getHeight() * 40) / 1080);

    public static final int DEFAULT_SIMULATION_SPEED = 800;

    public static final int MAX_SIMULATION_SPEED = 1600;

    public static final int MIN_SIMULATION_SPEED = 200;

    public static final int STEP_SIMULATION_SPEED = 200;

    public static final int BLOCK_DISTANCE_IN_KM = 170;

    public static final int AMOUNT_FUEL_CONSUMPTION = 400;

    public static final int FUEL_CONSUMED_VARIATION = 100;

    public static final int DEFAULT_MAX_ALTITUDE = 7000;

    public static final int ALTITUDE_INCREASE = 3000;

    public static final int ALTITUDE_DECREASE = 3000;

    public static final int DEFAULT_ALTITUDE_VARIATION = 600;

    public static final int SPEED_DECREASE = 400;

    public static final int SPEED_INCREASE = 400;

    public static final int DEFAULT_SPEED_VARIATION = 20;

    public static final int COLUMNS = 30;

    public static final int ROWS = 20;

    public static final int MIN_COUNT_BEFORE_TAKEOFF = 5;

    public static final int MAX_COUNT_BEFORE_TAKEOFF = 40;

    public static final int AIR_ZONE_GAP = COLUMNS / zoneWidth;

    public static final double SCALING_WIDTH_RATIO = SCREEN_DIMENSION.getWidth() / 1920;

    public static final double SCALING_HEIGHT_RATIO = SCREEN_DIMENSION.getHeight() / 1080;

    public static final String RESSOURCES_PATH = "src/resources/";

    public static final String CONFIG_FILE_PATH = "src/config/config.xml";

    public static final int HIGH_ALTITUDE = 2000;

    public static final int MEDIUM_ALTITUDE = 1000;

    public static final int LOW_ALTITUDE = 500;


}
