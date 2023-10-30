package pdc.project.entity;

/**
 * The {@code MoveableEntity} interface represents an entity that can be moved by
 * setting its X and Y coordinates. Entities implementing this interface are expected
 * to have methods for changing their positions in a 2D space.
 */
public interface MoveableEntity extends Entity {

    /**
     * Sets the X-coordinate of this movable entity to the specified value.
     *
     * @param x The new X-coordinate for the entity's position.
     */
    void setX(int x);

    /**
     * Sets the Y-coordinate of this movable entity to the specified value.
     *
     * @param y The new Y-coordinate for the entity's position.
     */
    void setY(int y);
}
