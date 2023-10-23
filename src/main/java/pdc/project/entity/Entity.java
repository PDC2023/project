/**
 * Represents a generic entity in the game world, providing essential methods for retrieving the entity's position,
 * collision box, and checking for collisions with other entities.
 */
package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.Universe;

public interface Entity extends Drawable {
    int getX();

    int getY();

    CollisionBox getCollisionBox();
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

        return deltaX < (thisHalfWidth + otherHalfWidth) && deltaY < (thisHalfHeight + otherHalfHeight);
    }

    default void tick() {

    }

    Universe getUniverse();
}
