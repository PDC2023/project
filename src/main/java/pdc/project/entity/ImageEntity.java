package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;

public abstract class ImageEntity extends AbstractEntity {
    Image image;/*111*/

    public ImageEntity(Universe universe, int x, int y, Image image) {
        super(universe, x, y, CollisionBox.of(image));
        this.image = image;
    }

    public ImageEntity(Universe universe,int x, int y, int width, int height, Image image) {
        super(universe, x, y, width, height);
        this.image = image;
    }

    public ImageEntity(Universe universe,int x, int y, int width, int height) {
        super(universe, x, y, width, height);
        this.image = null;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Utils.drawImage(g2d, image, x, y);
    }
}
