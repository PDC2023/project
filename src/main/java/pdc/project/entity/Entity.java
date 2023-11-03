package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.Universe;

/**
 * Represents a generic entity in the game world, providing essential methods for retrieving the entity's position,
 * collision box, and checking for collisions with other entities.
 */
public interface Entity extends Drawable {
    /**
     * Gets the X-coordinate of the entity's position.
     *
     * @return The X-coordinate of the entity.
     */
    int getX();

    /**
     * Gets the Y-coordinate of the entity's position.
     *
     * @return The Y-coordinate of the entity.
     */
    int getY();

    /**
     * Gets the collision box associated with the entity, which is used for collision detection.
     *
     * @return The collision box of the entity.
     */
    CollisionBox getCollisionBox();

    /**
     * Updates the state of the entity for one frame.
     */
    default void tick() {

    }

    /**
     * Handles the entity's death or removal from the game.
     */
    default void die() {
    }

    /**
     * Checks if the entity is dead or should be removed from the game.
     *
     * @return {@code true} if the entity is dead, otherwise {@code false}.
     */
    boolean dead();

    /**
     * Gets the game universe to which this entity belongs.
     *
     * @return The game universe associated with the entity.
     */
    Universe getUniverse();
}
