package ihm.buttons;

public class DisplayToggle {

    private boolean showGrid = false;

    private boolean showStyle = false;

    private boolean showCoords = false;

    private boolean showRelief = false;

    private boolean showFlights = false;

    private boolean showAltitude = false;

    private boolean showTrajects = false;

    private boolean showAirports = false;

    private boolean showAirZones = false;

    private boolean showBackground = false;

    private boolean showStylePause = false;

    private boolean showOccupiedZones = false;

    public void toggleShowGrid() {
        showGrid = !showGrid;
    }

    public void toggleShowStyle() {
        showStyle = !showStyle;
    }

    public void toggleShowCoords() {
        showCoords = !showCoords;
    }

    public void toggleShowRelief() {
        showRelief = !showRelief;
    }

    public void toggleShowFlights() {
        showFlights = !showFlights;
    }

    public void toggleShowAirZones() {
        showAirZones = !showAirZones;
    }

    public void toggleShowAltitude() {
        showAltitude = !showAltitude;
    }

    public void toggleShowTrajects() {
        showTrajects = !showTrajects;
    }

    public void toggleShowAirports() {
        showAirports = !showAirports;
    }

    public void toggleShowStylePause() {
        showStylePause = !showStylePause;
    }

    public void toggleShowBackground() {
        showBackground = !showBackground;
    }

    public void toggleShowOccupiedZones() {
        showOccupiedZones = !showOccupiedZones;
    }

    public boolean showGridEnabled() {
        return showGrid;
    }

    public boolean showStyleEnabled() {
        return showStyle;
    }

    public boolean showCoordsEnabled() {
        return showCoords;
    }

    public boolean showReliefEnabled() {
        return showRelief;
    }

    public boolean showFlightsEnabled() {
        return showFlights;
    }

    public boolean showAltitudeEnabled() {
        return showAltitude;
    }

    public boolean showAirportsEnabled() {
        return showAirports;
    }

    public boolean showTrajectsEnabled() {
        return showTrajects;
    }

    public boolean showAirZonesEnabled() {
        return showAirZones;
    }

    public boolean showBackgroundEnabled() {
        return showBackground;
    }

    public boolean showOccupiedZonesEnabled() {
        return showOccupiedZones;
    }
}
