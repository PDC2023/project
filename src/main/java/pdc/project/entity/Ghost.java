package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

public class Ghost extends ImageEntity implements Enemy {
    private boolean movingRight = true;
    private int tickCounter = 0;

    private final static double SIZE_RATIO = 1.0;

    public Ghost(Universe universe, int x, int y) {
        super(universe, x, y, (int) (32 * SIZE_RATIO), (int) (32 * SIZE_RATIO), Utils.loadImage("/ghost.png", SIZE_RATIO));
    }

    @Override
    public void tick() {
        tickCounter++;
        if (tickCounter % 4 != 0) {
            return;
        }

        if (movingRight) {
            x += 2;
            if (x >= 600 - getCollisionBox().getWidth()) {
                movingRight = false;
            }
        } else {
            x -= 2;
            if (x <= 0) {
                movingRight = true;
            }
        }
    }
}

