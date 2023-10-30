package pdc.project.entity;

/**
 * The {@code EntityWithVelocity} interface represents an entity that has a velocity
 * in a 2D space, with both X and Y components. Entities implementing this interface
 * can move with a certain velocity in the X and Y directions.
 */
public interface EntityWithVelocity extends MoveableEntity {

    /**
     * Gets the velocity in the X direction for this entity.
     *
     * @return The velocity in the X direction.
     */
    double getVelocityX();

    /**
     * Gets the velocity in the Y direction for this entity.
     *
     * @return The velocity in the Y direction.
     */
    double getVelocityY();

    /**
     * Sets the velocity in the X direction for this entity.
     *
     * @param velocityX The new velocity in the X direction to set.
     */
    void setVelocityX(double velocityX);

    /**
     * Sets the velocity in the Y direction for this entity.
     *
     * @param velocityY The new velocity in the Y direction to set.
     */
    void setVelocityY(double velocityY);
}
