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
     * Checks for collision between this entity and another entity.
     *
     * @param other The other entity to check for collision with.
     * @return {@code true} if a collision occurs, otherwise {@code false}.
     */
    default boolean checkCollision(Entity other) {
        int otherX = other.getX();
        int otherY = other.getY();
        CollisionBox otherBox = other.getCollisionBox();
        CollisionBox thisBox = this.getCollisionBox();

        int thisHalfWidth = thisBox.getWidth() / 2;
        int thisHalfHeight = thisBox.getHeight() / 2;
        int otherHalfWidth = otherBox.getWidth() / 2;
        int otherHalfHeight = otherBox.getHeight() / 2;

        int deltaX = Math.abs(this.getX() - otherX);
        int deltaY = Math.abs(this.getY() - otherY);

        boolean isTouchingX = deltaX == (thisHalfWidth + otherHalfWidth);
        boolean isTouchingY = deltaY == (thisHalfHeight + otherHalfHeight);

        if (isTouchingX && isTouchingY) return false;

        return deltaX <= (thisHalfWidth + otherHalfWidth) && deltaY <= (thisHalfHeight + otherHalfHeight);
    }

    /**
     * Gets collision information between this entity and another entity.
     *
     * @param other The other entity to check for collision with.
     * @return Collision information indicating the state and direction of the collision.
     */
    default CollisionInfo getCollision(Entity other) {
        int otherX = other.getX();
        int otherY = other.getY();
        CollisionBox otherBox = other.getCollisionBox();
        CollisionBox thisBox = this.getCollisionBox();

        int thisHalfWidth = thisBox.getWidth() / 2;
        int thisHalfHeight = thisBox.getHeight() / 2;
        int otherHalfWidth = otherBox.getWidth() / 2;
        int otherHalfHeight = otherBox.getHeight() / 2;

        int deltaX = Math.abs(this.getX() - otherX);
        int deltaY = Math.abs(this.getY() - otherY);

        boolean isTouchingX = deltaX == (thisHalfWidth + otherHalfWidth);
        boolean isTouchingY = deltaY == (thisHalfHeight + otherHalfHeight);
        boolean isOverlappingX = deltaX < (thisHalfWidth + otherHalfWidth);
        boolean isOverlappingY = deltaY < (thisHalfHeight + otherHalfHeight);

        boolean isCollisionX = isTouchingX || isOverlappingX;
        boolean isCollisionY = isTouchingY || isOverlappingY;

        if (isTouchingX && isTouchingY) return new CollisionInfo(CollisionState.NONE, CollisionDirection.NONE);

        if (isCollisionX && isCollisionY) {
            int overlappingByX = (thisHalfWidth + otherHalfWidth) - deltaX;
            int overlappingByY = (thisHalfHeight + otherHalfHeight) - deltaY;

            CollisionState state;
            CollisionDirection direction;

            if (overlappingByX > overlappingByY) {
                direction = (this.getY() < otherY) ? CollisionDirection.DOWN : CollisionDirection.UP;
                state = isTouchingX ? CollisionState.TOUCHING : CollisionState.OVERLAPPING;
            } else {
                direction = (this.getX() < otherX) ? CollisionDirection.RIGHT : CollisionDirection.LEFT;
                state = isTouchingY ? CollisionState.TOUCHING : CollisionState.OVERLAPPING;
            }
            return new CollisionInfo(state, direction);
        } else {
            return new CollisionInfo(CollisionState.NONE, CollisionDirection.NONE);
        }
    }

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
