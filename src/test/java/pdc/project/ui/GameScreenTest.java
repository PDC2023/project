package pdc.project.ui;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameScreenTest {

    private MockGameScreen mockGameScreen;

    @Before
    public void setUp() {
        Main main = new Main();
        mockGameScreen = new MockGameScreen(main);
    }
    @Test
    public void whenGameResumed_thenGameShouldNotBePausedAndMusicShouldPlay() {
        mockGameScreen.resumeGame();
        assertFalse(mockGameScreen.isGamePaused);
        assertTrue(mockGameScreen.isBGMMusicPlaying);
    }

    @Test
    public void whenGamePaused_thenGameShouldBePausedAndMusicShouldStop() {
        mockGameScreen.pauseGame();
        assertTrue(mockGameScreen.isGamePaused);
        assertFalse(mockGameScreen.isBGMMusicPlaying);
    }

    @Test
    public void whenLevelSet_thenGameShouldResumeAndMusicShouldPlay() {
        assertFalse(mockGameScreen.isGamePaused);
        assertTrue(mockGameScreen.isBGMMusicPlaying);
    }
}
