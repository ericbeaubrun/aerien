package util;

import config.Config;
import data.Position;

public class ConversionUtility {

    private static final String[] DIRECTIONS = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
            "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW", "N"};

    public static int blockToPixel(int coord) {
        return coord * Config.BLOCK_SIZE;
    }

    public static int pixelToBlock(int coord) {
        return coord / Config.BLOCK_SIZE;
    }


    public static int blockToPixel(String coord) {
        return Integer.parseInt(coord) * Config.BLOCK_SIZE;
    }

    public static int pixelToBlock(String coord) {
        return Integer.parseInt(coord) / Config.BLOCK_SIZE;
    }

    public static double calculateAngle(Position posA, Position posB) {
        int vectorX = posB.getX() - posA.getX();
        int vectorY = posB.getY() - posA.getY();
        return Math.atan2(vectorY, vectorX);
    }

    public static int radiansToDegrees(double angleRad) {
        return (int) Math.toDegrees(angleRad);
    }

    public static String angleToCardinal(double bearing) {

        bearing = radiansToDegrees(bearing) + 90;

        if (bearing < 0 && bearing > -180) {
            bearing = 360.0 + bearing;
        }

        if (!(bearing > 360) && !(bearing < -180)) {
            int i = (int) Math.floor(((bearing + 11.25) % 360) / 22.5);
            return DIRECTIONS[i];
        }
        return "Unknown";
    }



    public static int blockToMeter(int amountBlocks){
        return 170 * amountBlocks;
    }
}


