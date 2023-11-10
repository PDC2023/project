package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

public class BackgroundEntity extends ImageEntity implements NoPhysicalCollisionEntity {
    public BackgroundEntity(Universe universe, int x, int y) {
        super(universe, x, y, 0, 0, Utils.loadImage("/background.png", 3));
    }
}
