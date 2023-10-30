/**
 * Represents an abstract entity in the game, providing the basic properties and functionalities
 * that all entities should have. This class implements the {@link Entity} interface.
 * <p>
 * An entity in this context could be a player, an enemy, a bonus item, or any other object that
 * interacts with the game world.
 */
package pdc.project.entity;

import pdc.project.Universe;

public abstract class AbstractEntity implements Entity {
    protected int x, y;
    protected CollisionBox collisionBox;

    protected boolean death = false;

    Universe universe;

    public AbstractEntity(Universe universe, int x, int y) {
        this.universe = universe;
        this.x = x;
        this.y = y;
        this.collisionBox = null;
    }

    public AbstractEntity(Universe universe, int x, int y, CollisionBox collisionBox) {
        this.universe = universe;
        this.x = x;
        this.y = y;
        this.collisionBox = collisionBox;
    }

    public AbstractEntity(Universe universe, int x, int y, int width, int height) {
        this.universe = universe;
        this.x = x;
        this.y = y;
        this.collisionBox = new CollisionBox(width, height);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public Universe getUniverse() {
        return universe;
    }

    @Override
    public void die() {
        death = true;
    }

    @Override
    public boolean dead() {
        return death;
    }
}
