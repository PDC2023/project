package pdc.project.ui;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest {

    private MockMain mockMainScreen;

    @Before
    public void setUp() throws Exception {
        mockMainScreen = new MockMain();
    }
    @Test
    public void testSwitchToWinScreen() {
        mockMainScreen.switchToWinScreen();
        assertEquals("Win", mockMainScreen.getCurrentScreen());
    }

    @Test
    public void testSwitchToLevel1() {
        mockMainScreen.switchToLevel1();
        assertEquals("Game", mockMainScreen.getCurrentScreen());
    }

    @Test
    public void testSwitchToWelcomeScreen() {
        mockMainScreen.switchToWelcomeScreen();
        assertEquals("Welcome", mockMainScreen.getCurrentScreen());
    }
}
