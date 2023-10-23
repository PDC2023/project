package pdc.project;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Player extends AbstractEntity implements Drawable {
    public Player(double x, double y) {
        super(x, y, 10, 10);
    }

    private Image gifImage = new ImageIcon(getClass().getResource("/mario.gif")).getImage();
    @Override
    public void draw(Graphics2D g2d, ImageObserver obs) {
        g2d.drawImage(gifImage, (int) x, (int) y, obs);
    }
}
