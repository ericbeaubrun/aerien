import config.Config;

public class TestEmergencies {

    private static MainWindow frame;

    public static void main(String[] args) {
        Config.zoneHeight = 2;
        Config.zoneWidth = 3;
        Config.allowAlwaysEmergency = true;
        Config.emergencyEnabled = true;
        Config.allowBalancedReverseFlightDirection = true;
        frame = new MainWindow();
    }
}
