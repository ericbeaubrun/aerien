package engine;

import config.Config;
import data.Airplane;
import data.Position;
import util.ConversionUtility;

import java.util.Random;

public class AirplaneDataCalculator {

    private final Random randomVariation = new Random();

    public AirplaneDataCalculator() {
    }

    public void recalculateAirplaneData(Flight flight) {
        if (flight != null) {

            Airplane airplane = flight.getAirplane();
            Position currentPosition = flight.getCurrentPosition();

            if (airplane != null) {

                airplane.incrementTotalDistanceTraveled(Config.BLOCK_DISTANCE_IN_KM);

                calculateAirplaneRotation(flight);

                //Speed processes
                if (flight.getCurrentPositionIndex() < 3) {
                    // Take off
                    increaseSpeed(airplane);
                } else if (flight.getAmountRemainingBlocks() < 3) {
                    // Landing
                    decreaseSpeed(airplane);
                }

                if (flight.isPaused()) {
                    applyTrafficSpeedVariation(airplane);
                }

                applyAltitudeSpeedVariation(airplane, currentPosition);

                applyRandomSpeedVariation(airplane);

                // Altitude processes
                if (flight.getCurrentPositionIndex() < 3) {
                    // Take off
                    increaseAltitude(airplane);
                } else if (flight.getAmountRemainingBlocks() < 3) {
                    // Landing
                    decreaseAltitude(airplane);
                }
                applyReliefAltitudeVariation(airplane, currentPosition);
                applyRandomAltitudeVariation(airplane);

                // Fuels processes
                consumeFuel(airplane);
                applyRandomFuelVariation(airplane);
            }
        }
    }

    public void calculateAirplaneRotation(Flight flight) {
        if (flight != null) {

            Airplane airplane = flight.getAirplane();
            Position currentPosition = flight.getCurrentPosition();

            int currentBlockIndex = flight.getCurrentPositionIndex();

            if (airplane != null && flight.getCurrentPosition() != null) {
                Position nextPosition;
                if (currentBlockIndex < flight.getAmountBlockPath() - 4) {
                    nextPosition = flight.getPath().get(currentBlockIndex + 4);
                } else {
                    nextPosition = flight.getDestinationAirport().getPosition();
                }

                double angle = ConversionUtility.calculateAngle(currentPosition, nextPosition);
                airplane.setAngle(angle);
            }
        }
    }

    public void consumeFuel(Airplane airplane) {
        if (airplane != null) {
            airplane.setFuel(airplane.getFuel() - Config.AMOUNT_FUEL_CONSUMPTION);
            if (airplane.getFuel() < 0) {
                airplane.setFuel(0);
            }
        }
    }

    public void applyRandomFuelVariation(Airplane airplane) {
        if (airplane != null) {
            int variation = randomVariation.nextInt(Config.FUEL_CONSUMED_VARIATION);
            airplane.setFuel(airplane.getFuel() - variation);
        }
    }

    public void increaseSpeed(Airplane airplane) {
        if (airplane != null) {
            airplane.setSpeed(airplane.getSpeed() + Config.SPEED_INCREASE);
            if (airplane.getSpeed() > airplane.getMaxSpeed()) {
                airplane.setSpeed(airplane.getMaxSpeed());
            }
        }
    }

    public void decreaseSpeed(Airplane airplane) {
        if (airplane != null) {
            airplane.setSpeed(airplane.getSpeed() + Config.SPEED_DECREASE);
            if (airplane.getSpeed() < 0) {
                airplane.setSpeed(0);
            }
        }
    }

    public void applyRandomSpeedVariation(Airplane airplane) {
        if (airplane != null) {
            int variation = randomVariation.nextInt(Config.DEFAULT_SPEED_VARIATION);
            airplane.setSpeed(airplane.getSpeed() < airplane.getMaxSpeed() ? airplane.getSpeed() + variation : airplane.getSpeed() - variation);
        }
    }

    public void applyTrafficSpeedVariation(Airplane airplane) {
        if (airplane != null) {

        }
    }

    public void applyAltitudeSpeedVariation(Airplane airplane, Position block) {
        if (airplane != null && block != null) {

        }
    }

    public void increaseAltitude(Airplane airplane) {
        if (airplane != null) {
            airplane.setZ(airplane.getZ() + Config.ALTITUDE_INCREASE);
            if (airplane.getZ() > Config.DEFAULT_MAX_ALTITUDE) {
                airplane.setZ(Config.DEFAULT_MAX_ALTITUDE);
            }
        }
    }

    public void decreaseAltitude(Airplane airplane) {
        if (airplane != null) {
            airplane.setZ(airplane.getZ() - Config.ALTITUDE_DECREASE);
            if (airplane.getZ() < 0) {
                airplane.setZ(0);
            }
        }
    }

    public void applyRandomAltitudeVariation(Airplane airplane) {
        if (airplane != null) {
            int variation = randomVariation.nextInt(Config.DEFAULT_ALTITUDE_VARIATION);
            airplane.setZ(airplane.getZ() < Config.DEFAULT_MAX_ALTITUDE ? airplane.getZ() + variation : airplane.getZ() - variation);
        }
    }

    public void applyReliefAltitudeVariation(Airplane airplane, Position block) {
        if (airplane != null && block != null) {
            if (block.getZ() == Config.HIGH_ALTITUDE) {
                airplane.setZ(airplane.getZ() + Config.HIGH_ALTITUDE);

            } else if (block.getZ() == Config.MEDIUM_ALTITUDE) {
                airplane.setZ(airplane.getZ() + Config.MEDIUM_ALTITUDE);

            } else if (block.getZ() == Config.LOW_ALTITUDE) {
                airplane.setZ(airplane.getZ() + Config.LOW_ALTITUDE);
            }
        }
    }

}
