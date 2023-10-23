/**
 * Represents a block of terrain in the game world, providing functionalities for rendering the terrain block on the
 * screen. This class extends {@link AbstractEntity} and implements {@link Drawable}.
 */
package pdc.project.entity;

import pdc.project.Drawable;

import java.awt.*;
import java.awt.image.ImageObserver;

public class TerrainBlock extends AbstractEntity implements Drawable {
    public TerrainBlock(double x, double y) {
        super(x, y, 10, 10);
    }

    @Override
    public void draw(Graphics2D g2d, ImageObserver obs) {
        // TODO
    }
}