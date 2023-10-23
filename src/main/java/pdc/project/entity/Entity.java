/**
 * Represents a generic entity in the game world, providing essential methods for retrieving the entity's position,
 * collision box, and checking for collisions with other entities.
 */
package pdc.project.entity;

import pdc.project.Drawable;

public interface Entity extends Drawable {
    int getX();

    int getY();

    CollisionBox getCollisionBox();

    default boolean checkCollision(Entity other) {
        int otherX = other.getX();
        int otherY = other.getY();
        CollisionBox otherBox = other.getCollisionBox();
        CollisionBox thisBox = this.getCollisionBox();

        return this.getX() < otherX + otherBox.getWidth() &&
                this.getX() + thisBox.getWidth() > otherX &&
                this.getY() < otherY + otherBox.getHeight() &&
                this.getY() + thisBox.getHeight() > otherY;
    }
}
