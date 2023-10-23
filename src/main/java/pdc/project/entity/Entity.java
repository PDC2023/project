/**
 * Represents a generic entity in the game world, providing essential methods for retrieving the entity's position,
 * collision box, and checking for collisions with other entities.
 */
package pdc.project.entity;

public interface Entity {
    double getX();

    double getY();

    CollisionBox getCollisionBox();

    boolean checkCollision(Entity other);
}
