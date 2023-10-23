package pdc.project.entity;

import pdc.project.Utils;

import java.awt.*;

public abstract class ImageEntity extends AbstractEntity {
    Image image;

    public ImageEntity(double x, double y, Image image) {
        super(x, y, CollisionBox.of(image));
        this.image = image;
    }

    public ImageEntity(double x, double y, double width, double height, Image image) {
        super(x, y, width, height);
        this.image = image;
    }

    public ImageEntity(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.image = null;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Utils.drawImage(g2d, image, x, y);
    }
}
