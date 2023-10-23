/**
 * Represents the player character in the game, providing functionalities for rendering the player on the screen
 * and interacting with the game world. This class extends {@link AbstractEntity} and implements {@link Drawable}.
 */
package pdc.project.entity;

import pdc.project.Drawable;

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
