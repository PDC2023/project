package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Mushroom extends ImageEntity {
    private boolean movingRight = true;
    private int tickCounter = 0;

    public Mushroom(Universe universe, int x, int y) {
        super(universe, x, y, 30, 30);
        this.image = Utils.loadImage("/mushroom.gif");
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

