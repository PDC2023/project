package pdc.project.ui;

import pdc.project.level.Level1;

public class MockGameScreen extends GameScreen {

    public boolean isGamePaused;
    public boolean isBGMMusicPlaying;

    public MockGameScreen(Main main) {
        super(main);
        this.universe = null;
    }

    @Override
    public void resumeGame() {
        isGamePaused = false;
        isBGMMusicPlaying = true;
    }

    @Override
    public void pauseGame() {
        isGamePaused = true;
        isBGMMusicPlaying = false;
    }

    @Override
    public void setLevel(Level1 level1) {
        isGamePaused = false;
        isBGMMusicPlaying = true;
    }
}


