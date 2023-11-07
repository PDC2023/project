package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;

/**
 * An abstract class representing a moving entity in the universe.
 * <p>
 * This class provides basic functionality for moving entities, such as maintaining
 * position, velocity, and direction. It also provides a default implementation
 * of the `draw()` method, which flips the image horizontally if the entity is
 * facing left.
 */
public abstract class AbstractMovingEntity extends ImageEntity implements MoveableEntity, EntityWithVelocity {

    protected double verticalVelocity = 0;
    protected double horizontalVelocity = 0;

    public AbstractMovingEntity(Universe universe, int x, int y, int width, int height) {
        super(universe, x, y, width, height);
    }

    public AbstractMovingEntity(Universe universe, int x, int y, int width, int height, Image image) {
        super(universe, x, y, width, height, image);
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public double getVelocityX() {
        return horizontalVelocity;
    }

    @Override
    public double getVelocityY() {
        return verticalVelocity;
    }

    @Override
    public void setVelocityX(double velocityX) {
        horizontalVelocity = velocityX;
    }

    @Override
    public void setVelocityY(double velocityY) {
        verticalVelocity = velocityY;
    }

    @Override
    public void tick() {
        if (horizontalVelocity > 0) {
            facingLeft = false;
        } else if (horizontalVelocity < 0) {
            facingLeft = true;
        }


        y += verticalVelocity;
        x += horizontalVelocity;
    }

}
