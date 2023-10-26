package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;

public class Coin extends AbstractEntity {
    private boolean collected = false;
    private Image coinImage;

    public Coin(Universe universe, int x, int y, int width, int height) {
        super(universe, x, y, width, height);
        this.coinImage = Utils.loadImage("/coin.gif");
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!collected) {
            Utils.drawImage(g2d, coinImage, x, y);
        }
    }

    public void collect() {
        collected = true;
        universe.increaseScore(1);
    }

    public boolean isCollected() {
        return collected;
    }
}

