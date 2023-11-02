package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;


public class Coin extends ImageEntity implements NoPhysicalCollisionEntity {
    private boolean collected = false;
    public Coin(Universe universe, int x, int y, int width, int height) {
        super(universe, x, y, width, height, Utils.loadImage("/bonus.gif"));
    }

    public void onCollision() {
        if (!collected) {
            universe.increaseScore(1);
            universe.collectCoin();
            collected = true;
            die();
        }
    }



    public boolean isCollected() {
        return collected;
    }
}

