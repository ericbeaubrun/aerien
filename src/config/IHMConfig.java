package config;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static config.Config.*;

public class IHMConfig {

    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, (int) (BLOCK_SIZE * 0.3));

    public static final Font LABEL_FONT_1 = new Font("Arial", Font.BOLD, (int) (BLOCK_SIZE * 0.4));

    public static final Color BACKGROUND_COLOR = new Color(30, 30, 30);

    public static final Dimension WINDOW_DIMENSION = new Dimension((int) (SCREEN_DIMENSION.getWidth() * 0.841),
            (int) (SCREEN_DIMENSION.getHeight() * 0.778));


    // Aerial Traffic Panel
    public static final Stroke HIGHLIGHT_STROKE = new BasicStroke(4.0f);

    public static final String BACKGROUND_IMAGE_PATH = RESSOURCES_PATH + "map.png";

    public static final String BACKGROUND_RELIEF_IMAGE_PATH = RESSOURCES_PATH + "map_relief.png";

    public static final Font BASIC_FONT = new Font("Arial", Font.PLAIN, BLOCK_SIZE / 4);

    public static final Color TRAVELED_FLIGHT_COLOR = new Color(110, 110, 110);

    public static final float[] DASH_PATTERN = {5.0f, 5.0f};

    public static final Stroke FLIGHT_STROKE_1 = new BasicStroke(2.0f);

    public static final Stroke FLIGHT_STROKE_2 = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, DASH_PATTERN, 0.0f);

    public static final Color HIGH_ALTITUDE_COLOR = new Color(171, 126, 82);

    public static final Color MEDIUM_ALTITUDE_COLOR = new Color(201, 175, 107);

    public static final Color LOW_ALTITUDE_COLOR = new Color(201, 175, 107);

    public static final Color BACKGROUND_COLOR_MAP = java.awt.Color.BLACK;

    public static final Color BASIC_AIRPORT_COLOR = java.awt.Color.RED;

    public static final Color PAUSED_FLIGHT_COLOR = java.awt.Color.YELLOW;

    public static final Color DELAYED_FLIGHT_COLOR = java.awt.Color.RED;

    public static final Color UNPLANNED_FLIGHT_COLOR = java.awt.Color.YELLOW;

    public static final Color BASIC_FLIGHT_COLOR = java.awt.Color.WHITE;

    public static final Color AIRZONE_1_COLOR = new Color(168, 168, 168);

    public static final Color AIRZONE_2_COLOR = java.awt.Color.WHITE;

    public static final Color OCCUPIED_AIRZONE_COLOR = java.awt.Color.RED;

    public static final Color RUNNING_FLIGHT_COLOR = java.awt.Color.GREEN;

    public static final Color HIGHLIGHT_ENTITY_COLOR = Color.WHITE;

    public static final Color GRID_COLOR = new Color(145, 145, 145);

    public static final Color BASIC_TEXT_COLOR = Color.WHITE;

    // Airport Panel
    public static final Font HEADER_FONT = new Font("Arial", Font.BOLD, BLOCK_SIZE / 2);

    public static final Border BUTTON_PANEL_BORDER = BorderFactory.createLineBorder(new Color(110, 110, 110), 2);

    // Buttons Panel
    public static final Color ACTIVATED_COLOR = new Color(28, 77, 41);

    public static final Color PAUSE_BUTTON_COLOR = new Color(117, 35, 35);

    public static final Color DISABLED_COLOR = Color.BLACK;


}
