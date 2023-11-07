package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.Graphics2D;

public class Flag1 extends ImageEntity implements NoPhysicalCollisionEntity {

    private final static double SIZE_RATIO = 1.0;

    public Flag1(Universe universe, int x, int y) {
        super(universe, x, y,
                (int) (32 * SIZE_RATIO),
                (int) (32 * SIZE_RATIO),
                Utils.loadImage("/flag.gif", SIZE_RATIO));
    }

    @Override
    public void tick() {
        var collision = Collision.checkCollision(this, getUniverse().player);
        if (collision) {
            getUniverse().player.nextLevel();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);
    }
}




