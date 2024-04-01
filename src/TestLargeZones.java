import config.Config;

public class TestLargeZones {
    private static MainWindow frame;

    public static void main(String[] args) {
        Config.zoneHeight = 5;
        Config.zoneWidth = 5;
        Config.allowAlwaysEmergency = false;
        Config.emergencyEnabled = false;
        Config.allowBalancedReverseFlightDirection = false;
        frame = new MainWindow();
    }
}
