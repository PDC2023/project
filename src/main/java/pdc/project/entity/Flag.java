package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.Graphics2D;

public class Flag extends ImageEntity implements NoPhysicalCollisionEntity {

    private final static double SIZE_RATIO = 1.0;

    public Flag(Universe universe, int x, int y) {
        super(universe, x, y ,
                Utils.loadImage("/flag.gif", SIZE_RATIO));
    }

    @Override
    public void tick() {
        var collision = this.checkCollision(getUniverse().player);
        if (collision) {
            getUniverse().player.win();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);
    }
}


