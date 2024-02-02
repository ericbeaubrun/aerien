package util;

import config.Config;
import data.Airplane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            throw new RuntimeException(e);
        }
        return image;
    }

    public static BufferedImage getAirplaneImage(Airplane airplane) {

        BufferedImage image = null;


        if (airplane != null) {

            String ref = airplane.getReference().strip();

            String color = airplane.isWaiting() ? "_yellow" : "_green";

            String path = Config.RESSOURCES_PATH + "planes/";

            if (ref.startsWith("Airbus A320".strip())) {
                path += "plane1" + color + ".png";

            } else if (ref.startsWith("Tecnam FHCSK".strip())) {
                path += "plane2" + color + ".png";

            } else if (ref.startsWith("Piper J3".strip())) {
                path += "plane3" + color + ".png";

            } else if (ref.startsWith("Boeing 737".strip())) {
                path += "plane4" + color + ".png";

            } else if (ref.startsWith("Antonov AN225".strip())) {
                path += "plane5" + color + ".png";

            } else if (ref.startsWith("Aero L39 Albatros".strip())) {
                path += "plane6" + color + ".png";

            } else if (ref.startsWith("Piper PA28".strip())) {
                path += "plane7" + color + ".png";

            } else if (ref.startsWith("American XB68".strip())) {
                path += "plane8" + color + ".png";

            } else if (ref.startsWith("Global 7500".strip())) {
                path += "plane9" + color + ".png";
            }
            image = readImage(path);
        }
        return image;
    }
}
