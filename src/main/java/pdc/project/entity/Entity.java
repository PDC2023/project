/**
 * Represents a generic entity in the game world, providing essential methods for retrieving the entity's position,
 * collision box, and checking for collisions with other entities.
 */
package pdc.project.entity;

import pdc.project.Drawable;

public interface Entity extends Drawable {
    double getX();

    double getY();

    CollisionBox getCollisionBox();

    default boolean checkCollision(Entity other) {
        double otherX = other.getX();
        double otherY = other.getY();
        CollisionBox otherBox = other.getCollisionBox();
        CollisionBox thisBox = this.getCollisionBox();

        return this.getX() < otherX + otherBox.getWidth() &&
                this.getX() + thisBox.getWidth() > otherX &&
                this.getY() < otherY + otherBox.getHeight() &&
                this.getY() + thisBox.getHeight() > otherY;
    }
}
