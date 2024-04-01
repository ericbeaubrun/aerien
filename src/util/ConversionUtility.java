package util;

import config.Config;
import data.Position;

import java.awt.*;

public class ConversionUtility {

    private static final String[] DIRECTIONS = {
            "N", "NNE", "NE", "ENE", "E", "ESE",
            "SE", "SSE", "S", "SSW", "SW", "WSW",
            "W", "WNW", "NW", "NNW", "N"};

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
        if (posA != null && posB != null) {
            int vectorX = posB.getX() - posA.getX();
            int vectorY = posB.getY() - posA.getY();
            return Math.atan2(vectorY, vectorX);
        }
        return 0;
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

    public static Dimension convertToSimulationDimension(Dimension dim) {
        return new Dimension((int) Math.round(dim.getHeight() * (16.0 / 9.0)), (int) dim.getHeight());
    }

    public static int blockToMeter(int amountBlocks) {
        return Config.BLOCK_DISTANCE_IN_KM * amountBlocks;
    }

    /**
     * Calculates the vertex points of an arrowhead (oriented triangle) given a line and triangle size.
     *
     * @param triangleSize The size of the triangle to be drawn.
     * @param startX       The x-coordinate of the starting point of the line.
     * @param endX         The x-coordinate of the ending point of the line.
     * @param startY       The y-coordinate of the starting point of the line.
     * @param endY         The y-coordinate of the ending point of the line.
     * @return A 2D array containing the x and y coordinates of the three vertices of the triangle.
     */
    public static int[][]  calculateArrowheadPoints(int triangleSize, int startX, int endX, int startY, int endY) {
        // Calculate the angle of the line with respect to the horizontal axis.
        double angle = Math.atan2(endY - startY, endX - startX);

        // Calculate the x coordinates of the arrowhead's vertices.
        int[] xVertices = {
                endX,
                (int) (endX - triangleSize * Math.cos(angle - Math.PI / 6)),
                (int) (endX - triangleSize * Math.cos(angle + Math.PI / 6))
        };

        // Calculate the y coordinates of the arrowhead's vertices.
        int[] yVertices = {
                endY,
                (int) (endY - triangleSize * Math.sin(angle - Math.PI / 6)),
                (int) (endY - triangleSize * Math.sin(angle + Math.PI / 6))
        };

        // Combine the x and y coordinates of the vertices.
        return new int[][]{xVertices, yVertices};
    }


    public static String toTime(int i) {
        if (i < 60) {
            return i + "min";
        } else {
            return i % 60 == 0 ? i / 60 + "H00" : i / 60 + "H" + i % 60;
        }
    }
}


