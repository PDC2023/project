package pdc.project;

import java.awt.*;
/**
 * The {@code Drawable} interface represents an object that can be drawn using
 * a {@link Graphics2D} object. Classes implementing this interface are expected
 * to provide a method to draw their contents on a graphics context.
 */
public interface Drawable {

    /**
     * Draws the contents of the implementing object on the specified {@link Graphics2D} context.
     *
     * @param g2d The {@link Graphics2D} context used for drawing.
     */
    void draw(Graphics2D g2d);
}
