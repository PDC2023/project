package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;

public class SavePoint extends ImageEntity implements NoPhysicalCollisionEntity {
    private final int nth;

    public SavePoint(int nth, Universe universe, int x, int y) {
        super(universe, x, y, Utils.loadImage("/savePoint.gif", 0.1));
        this.nth = nth;
    }

    public int getNth() {
        return nth;
    }

    @Override
    public void tick() {
        if (Collision.checkCollision(getUniverse().player, this)) {
            if (nth > getUniverse().lastSavePoint.nth) {
                getUniverse().lastSavePoint = this;
            }
        }
    }
}
