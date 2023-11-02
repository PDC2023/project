package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

public class Coin extends ImageEntity implements NoPhysicalCollisionEntity {
    public Coin(Universe universe, int x, int y, int width, int height) {
        super(universe, x, y, width, height, Utils.loadImage("/bonus.gif"));
    }

    public void onCollision() {
        if (!dead()) {
            die();
        }
    }
}



