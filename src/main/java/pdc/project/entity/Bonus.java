package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.entity.AbstractEntity;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Bonus extends AbstractEntity implements Drawable {
    public Bonus(double x, double y) {
        super(x, y, 10, 10);
    }

    @Override
    public void draw(Graphics2D g2d, ImageObserver obs) {
        // TODO
    }
}
