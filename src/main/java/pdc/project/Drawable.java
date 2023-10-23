package pdc.project;

import java.awt.*;
import java.awt.image.ImageObserver;

public interface Drawable {
    void draw(Graphics2D g2d, ImageObserver obs);
}
