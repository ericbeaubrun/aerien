package util;

import config.Config;
import data.Airplane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static config.Config.RESSOURCES_PATH;

public class ImageUtility {

    public static BufferedImage rotateImage(BufferedImage image, double angle) {
        AffineTransform rotation = AffineTransform.getRotateInstance(angle, image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(rotation, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }

    public static BufferedImage readImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static String getPlaneImageName(Airplane airplane) {
        String imageName = null;
        String ref = airplane.getReference();

        if (ref.startsWith("Airbus A320".strip())) {
            imageName = "plane1";

        } else if (ref.startsWith("Tecnam FHCSK".strip())) {
            imageName = "plane2";

        } else if (ref.startsWith("Piper J3".strip())) {
            imageName = "plane3";

        } else if (ref.startsWith("Boeing 737".strip())) {
            imageName = "plane4";

        } else if (ref.startsWith("Antonov AN225".strip())) {
            imageName = "plane5";

        } else if (ref.startsWith("Aero L39 Albatros".strip())) {
            imageName = "plane6";

        } else if (ref.startsWith("Piper PA28".strip())) {
            imageName = "plane7";

        } else if (ref.startsWith("American XB68".strip())) {
            imageName = "plane8";

        } else if (ref.startsWith("Global 7500".strip())) {
            imageName = "plane9";
        }

        return imageName;
    }

    public static BufferedImage getAirplaneImage(Airplane airplane) {

        BufferedImage image = null;

        if (airplane != null) {
            String color = airplane.isWaiting() ? "yellow" : "green";
            String path = RESSOURCES_PATH + "planes/";
            String imageName = getPlaneImageName(airplane);

            if (imageName != null) {
                path += imageName + "_" + color + ".png";
                image = readImage(path);
            }
        }
        return image;
    }

    public static ImageIcon getAirplaneImageIcon(Airplane airplane, int width, int height) {

        ImageIcon resizedIcon = null;
        String path = RESSOURCES_PATH + "planes/";
        String imageName = getPlaneImageName(airplane);
        path += imageName + ".png";

        try {
            File file = new File(path);
            Image originalImage = ImageIO.read(file);
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            resizedIcon = new ImageIcon(resizedImage);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resizedIcon;
    }

    public static BufferedImage getEmergencyImage1() {
        return readImage(RESSOURCES_PATH + "planes/emergency1.png");
    }

    public static BufferedImage getEmergencyImage2() {
        return readImage(RESSOURCES_PATH + "planes/emergency2.png");
    }
}
