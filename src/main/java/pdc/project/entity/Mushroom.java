package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Mushroom extends ImageEntity {
    private boolean movingRight = true;
    private int tickCounter = 0;

    private final static double SIZE_RATIO = 1.0;

    public Mushroom(Universe universe, int x, int y) {
        super(universe, x, y, (int) (30 * SIZE_RATIO), (int) (30 * SIZE_RATIO), Utils.loadImage("/mushroom.gif", SIZE_RATIO));
    }

    @Override
    public void tick() {
        tickCounter++;
        if (tickCounter % 4 != 0) {
            return;
        }

        if (movingRight) {
            x += 1;
            if (x >= 600 - getCollisionBox().getWidth()) {
                movingRight = false;
            }
        } else {
            x -= 1;
            if (x <= 0) {
                movingRight = true;
            }
        }
    }
}

