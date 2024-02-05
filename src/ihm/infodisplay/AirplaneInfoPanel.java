package ihm.infodisplay;

import config.Config;
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
    private final InfoLabel altitudeLabel = new InfoLabel("Altitude : ", "km");

    private final InfoLabel fuelLabel = new InfoLabel("Fuel : ", "L");
    private final InfoLabel speedlabel = new InfoLabel("Speed : ", "km/h");
    private final InfoLabel angleLabel = new InfoLabel("Angle : ", "°C");

    private final InfoLabel originLabel = new InfoLabel("Origin : ");
    private final InfoLabel destinationLabel = new InfoLabel("Destination : ");

    private final InfoLabel arrivalTime = new InfoLabel("Arrival time :");
    private final InfoLabel departureTime = new InfoLabel("Departure time :");

    private final InfoLabel directionLabel = new InfoLabel("Direction : ");
    private final InfoLabel frequencyLabel = new InfoLabel("Communication frequency : ", "Hz");

    private final InfoLabel distanceFromStartLabel = new InfoLabel("Distance from start : ", "km");
    private final InfoLabel distanceBeforeArrivalLabel = new InfoLabel("Distance before arrival  : ", "km");
    private final InfoLabel totalDistanceLabel = new InfoLabel("Total distance : ", "km");

    public AirplaneInfoPanel(int width) {
        setPreferredSize(new Dimension(width / 6, 0));
        setBackground(Config.BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initLabels();
    }

    private void initLabels() {
        labels = new ArrayList<>();
        labels.add(referenceLabel);
        labels.add(thumbnailLabel);
        labels.add(xLabel);
        labels.add(yLabel);
        labels.add(altitudeLabel);
        labels.add(speedlabel);
        labels.add(fuelLabel);
        labels.add(directionLabel);
        labels.add(angleLabel);
        labels.add(frequencyLabel);
        labels.add(departureTime);
        labels.add(arrivalTime);
        labels.add(originLabel);
        labels.add(destinationLabel);
        labels.add(distanceFromStartLabel);
        labels.add(distanceBeforeArrivalLabel);
        labels.add(totalDistanceLabel);

        for (InfoLabel label : labels) {
            label.setForeground(Color.WHITE);
//            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            label.setFont(new Font("Arial", Font.BOLD, 12));
            add(label);
            add(Box.createVerticalStrut(10));
        }
    }

    public void updateFlightInfo(Flight flight) {
        if (flight != null) {
            Airplane airplane = flight.getAirplane();
            if (airplane != null) {
                thumbnailLabel.setIcon(ImageUtility.getAirplaneImageIcon(airplane,
                        (int) (512 * Config.SCALING_WIDTH_RATIO)/2,
                        (int) (535 * Config.SCALING_WIDTH_RATIO)/2));
                xLabel.updateText(airplane.getX());
                yLabel.updateText(airplane.getY());
                speedlabel.updateText(airplane.getSpeed());
                altitudeLabel.updateText((airplane.getZ()));
                referenceLabel.updateText(airplane.getReference());
                originLabel.updateText(flight.getStartAirport().getName());
                angleLabel.updateText(radiansToDegrees(airplane.getAngle()));
                directionLabel.updateText(angleToCardinal(airplane.getAngle()));
                destinationLabel.updateText(flight.getDestinationAirport().getName());
                totalDistanceLabel.updateText(blockToMeter(flight.getAmountBlockPath()));
                distanceFromStartLabel.updateText(blockToMeter(flight.getCurrentBlockIndex()));
                distanceBeforeArrivalLabel.updateText(blockToMeter(flight.getAmountBlockRemainingPath()));
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
