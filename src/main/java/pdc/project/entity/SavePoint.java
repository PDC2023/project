package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;

public class SavePoint extends ImageEntity implements NoPhysicalCollisionEntity {
    private final int nth;
    public SavePoint(int nth, Universe universe, int x, int y) {
        super(universe, x, y, Utils.loadImage("/ground.png")); // TODO: correct image
        this.nth = nth;
    }

    public int getNth() {
        return nth;
    }
}
