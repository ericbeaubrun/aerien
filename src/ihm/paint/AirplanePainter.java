package ihm.paint;

import data.Airplane;
import ihm.buttons.DisplayToggle;
import util.ImageUtility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static config.Config.*;
import static config.IHMConfig.PAUSED_FLIGHT_COLOR;
import static config.IHMConfig.RUNNING_FLIGHT_COLOR;
import static util.ImageUtility.getAirplaneImage;
import static util.ImageUtility.rotateImage;

public class AirplanePainter {

    private boolean animationBool = true;

    private final DisplayToggle displayToggle;

    public AirplanePainter(DisplayToggle displayToggle) {
        this.displayToggle = displayToggle;
    }

    public void paintAirplanes(Graphics g, ArrayList<Airplane> airplanes) {
        if (airplanes != null) {
            for (Airplane airplane : airplanes) {
                if (!airplane.isOnRunway()) {
                    g.setColor(airplane.isWaiting() ? PAUSED_FLIGHT_COLOR : RUNNING_FLIGHT_COLOR);

                    if (displayToggle.showStyleEnabled()) {
                        drawAirplaneImage(g, airplane);
                    } else {
                        drawAirplaneCircle(g, airplane);
                    }

                    if (airplane.isEmergency()) {
                        drawAirplaneEmergencyImage(g, airplane);
                    }

                    if (displayToggle.showAltitudeEnabled()) {
                        drawAirplaneAltitude(g, airplane);
                    }
                }
            }
        }
    }

    private void drawAirplaneCircle(Graphics g, Airplane airplane) {
        if (airplane != null) {
            int x = airplane.getX() + BLOCK_SIZE / 4;
            int y = airplane.getY() + BLOCK_SIZE / 4;
            int width = (BLOCK_SIZE - 5) / 2;
            int height = (BLOCK_SIZE - 5) / 2;

            g.fillOval(x, y, width, height);

            x -= BLOCK_SIZE / 2 + (BLOCK_SIZE / 4);
            y -= BLOCK_SIZE / 2 - (BLOCK_SIZE / 4);

            g.drawString(airplane.getReference(), x, y);
        }
    }

    private void drawAirplaneImage(Graphics g, Airplane airplane) {
        BufferedImage image = getAirplaneImage(airplane);
        if (image != null) {
            image = rotateImage(image, airplane.getAngle());
            int x = airplane.getX() + BLOCK_SIZE / 12;
            int y = airplane.getY() + BLOCK_SIZE / 12;
            int width = (int) (38 * SCALING_WIDTH_RATIO);
            int height = (int) (40 * SCALING_HEIGHT_RATIO);
            g.drawImage(image, x, y, width, height, null);
        }
    }

    private void drawAirplaneAltitude(Graphics g, Airplane airplane) {
        if (airplane != null) {
            int x = airplane.getX() + BLOCK_SIZE - (BLOCK_SIZE / 2);
            int y = airplane.getY() + BLOCK_SIZE + BLOCK_SIZE / 4 - (BLOCK_SIZE / 6);
            g.drawString(airplane.getZ() + "m", x, y);
        }
    }

    private void drawAirplaneEmergencyImage(Graphics g, Airplane airplane) {
        if (airplane != null) {

            int x = airplane.getX() + BLOCK_SIZE / 12;
            int y = airplane.getY() + BLOCK_SIZE / 12;
            int width = (int) (35 * SCALING_WIDTH_RATIO);
            int height = (int) (35 * SCALING_HEIGHT_RATIO);

            if (animationBool) {
                g.drawImage(ImageUtility.getEmergencyImage1(), x, y, width, height, null);
            } else {
                g.drawImage(ImageUtility.getEmergencyImage2(), x, y, width, height, null);
            }

            animationBool = !animationBool;

        }
    }
}
