package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;

public class Coin extends ImageEntity implements NoSpaceEntity {
    private boolean collected = false;

    public Coin(Universe universe, int x, int y, int width, int height) {
        super(universe, x, y, width, height, Utils.loadImage("/bonus.gif"));
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!collected) {
            super.draw(g2d);
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

