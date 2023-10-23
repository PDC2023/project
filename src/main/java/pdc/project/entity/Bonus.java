/**
 * Represents a bonus item in the game, which can be collected by the player to gain additional points or abilities.
 * This class extends {@link AbstractEntity} and implements {@link Drawable} for rendering the bonus item on the screen.
 */
package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.Universe;
import pdc.project.entity.AbstractEntity;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Bonus extends AbstractEntity {
    public Bonus(Universe universe, int x, int y) {
        super(universe, x, y, 10, 10);
    }

    @Override
    public void draw(Graphics2D g2d) {
        // TODO
    }
}
