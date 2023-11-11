package pdc.project.ui;

import java.sql.SQLException;

public class MockMain extends Main {
    String currentScreen = "Welcome";

    public MockMain() throws SQLException {
        db = null;
    }


    @Override
    public void switchToWinScreen() {
        currentScreen = "Win";
    }

    @Override
    public void switchToLevel1() {
        currentScreen = "Game";
    }

    @Override
    public void switchToWelcomeScreen() {
        currentScreen = "Welcome";
    }

    public String getCurrentScreen() {
        return currentScreen;
    }
}
