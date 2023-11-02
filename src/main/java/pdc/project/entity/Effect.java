package pdc.project.entity;

import pdc.project.Universe;

import java.awt.*;

public class Effect extends ImageEntity implements NoPhysicalCollisionEntity {
    public Effect(Universe universe, int x, int y, Image image) {
        super(universe, x, y, image);
    }
}
