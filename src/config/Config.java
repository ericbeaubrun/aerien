package config;

import util.ConversionUtility;

import java.awt.*;

public class Config {

    public static Dimension SCREEN_DIMENSION = ConversionUtility.convertToSimulationDimension(Toolkit.getDefaultToolkit().getScreenSize());
//    public static Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

    public static final int BLOCK_SIZE = (int) ((SCREEN_DIMENSION.getHeight() * 40) / 1080);

    public static final int DEFAULT_SIMULATION_SPEED = 800;

    public static int COLUMNS = 30;

    public static int ROWS = 20;

    public static int AIR_ZONE_HEIGHT = 5;
    public static int AIR_ZONE_WIDTH = 5;
//    public static int AIR_ZONE_HEIGHT = 2;
//    public static int AIR_ZONE_WIDTH = 3;

    public static int AIR_ZONE_GAP = COLUMNS / AIR_ZONE_WIDTH;

    public static double SCALING_WIDTH_RATIO =  SCREEN_DIMENSION.getWidth() / 1920;

    public static double SCALING_HEIGHT_RATIO =  SCREEN_DIMENSION.getHeight() / 1080;

    public static final boolean ALLOW_GRID_DISPLAY = true;

    public static final boolean ALLOW_COORD_DISPLAY = true;

    public static final boolean ALLOW_FRONTIER_DISPLAY = false;

    public static final boolean ALLOW_COUNTRIES_NAME_DISPLAY = false;

    public static final int SIMULATION_SPEED = 50;

    public static final Dimension WINDOW_DIMENSION = new Dimension((int) (SCREEN_DIMENSION.getWidth() * 0.841), (int) (SCREEN_DIMENSION.getHeight() * 0.778));

    public static final Color BACKGROUND_COLOR = new Color(30, 30, 30);

    public static String RESSOURCES_PATH = "src/resources/";


}
