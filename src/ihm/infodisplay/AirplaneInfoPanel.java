package ihm.infodisplay;

import config.Config;
import config.IHMConfig;
import data.Airplane;
import engine.Flight;
import util.ImageUtility;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static util.ConversionUtility.*;

public class AirplaneInfoPanel extends JPanel {
    private ArrayList<InfoLabel> labels;

    private final InfoLabel thumbnailLabel = new InfoLabel();
    private final InfoLabel referenceLabel = new InfoLabel();

    private final InfoLabel xLabel = new InfoLabel("x : ");
    private final InfoLabel yLabel = new InfoLabel("y : ");
    private final InfoLabel altitudeLabel = new InfoLabel("Altitude : ", "m");
    private final InfoLabel fuelLabel = new InfoLabel("Fuel : ", "%");
    private final InfoLabel speedlabel = new InfoLabel("Speed : ", "km/h");
    private final InfoLabel angleLabel = new InfoLabel("Direction Angle : ", "°C");
    private final InfoLabel originLabel = new InfoLabel("Start Airport : ");
    private final InfoLabel destinationLabel = new InfoLabel("Destination Airport : ");
    private final InfoLabel arrivalTime = new InfoLabel("Arrival time :", "UTC+1");
    private final InfoLabel departureTime = new InfoLabel("Departure time :", "UTC+1");
    private final InfoLabel directionLabel = new InfoLabel("Cardinal Point : ");
    private final InfoLabel totalDistanceTraveledLabel = new InfoLabel("All Time Distance Traveled : ", "km");
    private final InfoLabel flightProgressLabel = new InfoLabel("Progress : ", "%");
    private final InfoLabel distanceBeforeArrivingLabel = new InfoLabel("Distance before arriving  : ", "km");
    private final InfoLabel totalDistanceLabel = new InfoLabel("Total distance : ", "km");

    public AirplaneInfoPanel(int width) {
        setPreferredSize(new Dimension(width / 6, 0));
        setBackground(IHMConfig.BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initLabels();
    }

    private void initLabels() {
        labels = new ArrayList<>();
        labels.add(referenceLabel);
        labels.add(thumbnailLabel);
        labels.add(new InfoLabel("-------------------------------------------------------------"));
        labels.add(xLabel);
        labels.add(yLabel);
        labels.add(altitudeLabel);
        labels.add(new InfoLabel("-------------------------------------------------------------"));
        labels.add(angleLabel);
        labels.add(directionLabel);
        labels.add(speedlabel);
        labels.add(fuelLabel);
//        labels.add(arrivalTime);
//        labels.add(departureTime);
        labels.add(new InfoLabel("-------------------------------------------------------------"));
        labels.add(originLabel);
        labels.add(destinationLabel);
        labels.add(new InfoLabel("-------------------------------------------------------------"));
        labels.add(totalDistanceLabel);
        labels.add(flightProgressLabel);
        labels.add(distanceBeforeArrivingLabel);
        labels.add(totalDistanceTraveledLabel);


        for (InfoLabel label : labels) {
            label.setForeground(Color.WHITE);
//            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            label.setFont(new Font("Arial", Font.BOLD, Config.BLOCK_SIZE / 3));
            add(label);
            add(Box.createVerticalStrut(10));
        }
        referenceLabel.setFont(new Font("Arial", Font.BOLD, Config.BLOCK_SIZE / 2));
    }

    public void updateFlightInfo(Flight flight) {
        if (flight != null) {
            Airplane airplane = flight.getAirplane();
            if (airplane != null) {
                thumbnailLabel.setIcon(ImageUtility.getAirplaneImageIcon(airplane,
                        (int) (512 * Config.SCALING_WIDTH_RATIO) / 2,
                        (int) (535 * Config.SCALING_WIDTH_RATIO) / 2));
                xLabel.updateText(airplane.getX() + " (" + airplane.getColumn() + ")");
                yLabel.updateText(airplane.getY() + " (" + airplane.getRow() + ")");
                speedlabel.updateText(airplane.getSpeed());
                altitudeLabel.updateText((airplane.getZ()));
                referenceLabel.updateText(airplane.getReference());
                departureTime.updateText(flight.getDepartureTime());
                originLabel.updateText(flight.getStartAirport().getName());
                angleLabel.updateText(radiansToDegrees(airplane.getAngle()));
                directionLabel.updateText(angleToCardinal(airplane.getAngle()));
                destinationLabel.updateText(flight.getDestinationAirport().getName());
                totalDistanceLabel.updateText(blockToMeter(flight.getAmountBlockPath()));
                totalDistanceTraveledLabel.updateText(airplane.getTotalDistanceTraveledCount());
                fuelLabel.updateText(((double) airplane.getFuel() / airplane.getMaxFuel()) * 100);
                distanceBeforeArrivingLabel.updateText(blockToMeter(flight.getAmountRemainingBlocks()));
                flightProgressLabel.updateText(100 * blockToMeter(flight.getCurrentPositionIndex()) / (blockToMeter(flight.getAmountBlockPath()) + 1));
//                arrivalTime.updateText(flight.calculateArrivalTime(time, flight.getSpeed()));
            } else {
                resetLabelsText();
            }
        } else {
            resetLabelsText();
        }
    }

    public void showLabels() {
        for (InfoLabel label : labels) {
            label.setVisible(true);
        }
    }

    public void resetLabelsText() {
        for (InfoLabel label : labels) {
            label.setText(label.getInitialText());
        }
    }
}
