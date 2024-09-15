import config.Config;

public class Main {

    private static MainWindow main;

    public static void main(String[] args) {
        Config.emergencyEnabled = false;
        Config.allowBalancedReverseFlightDirection = true;
        Config.zoneHeight = 2;
        Config.zoneWidth = 3;
        main = new MainWindow();
    }
}
