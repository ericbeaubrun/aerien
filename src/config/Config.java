package config;

import util.ConversionUtility;

import java.awt.*;

public class Config {

    public static Dimension SCREEN_DIMENSION = ConversionUtility.convertToGameDimension(Toolkit.getDefaultToolkit().getScreenSize());
//    public static Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

    public static final int BLOCK_SIZE = (int) ((SCREEN_DIMENSION.getHeight() * 40) / 1080);


    public static int COLUMNS = 30;

    public static int ROWS = 20;

//    public static int AIR_SECTOR_HEIGHT = 5;
//    public static int AIR_SECTOR_WIDTH = 5;

    public static int AIR_SECTOR_HEIGHT = 2;
    public static int AIR_SECTOR_WIDTH = 3;


    public static int GAP = COLUMNS / AIR_SECTOR_WIDTH;

    public static final boolean ALLOW_GRID_DISPLAY = true;

    public static final boolean ALLOW_COORD_DISPLAY = true;

    public static final boolean ALLOW_FRONTIER_DISPLAY = false;

    public static final boolean ALLOW_COUNTRIES_NAME_DISPLAY = false;

    public static final int SIMULATION_SPEED = 50;

    public static final Dimension WINDOW_DIMENSION = new Dimension((int) (SCREEN_DIMENSION.getWidth() * 0.841), (int) (SCREEN_DIMENSION.getHeight() * 0.778));

    public static final Color BACKGROUND_COLOR = new Color(30, 30, 30);

    public static String RESSOURCES_PATH = "src/resources/";


}
