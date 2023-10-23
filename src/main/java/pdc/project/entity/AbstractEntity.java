/**
 * Represents an abstract entity in the game, providing the basic properties and functionalities
 * that all entities should have. This class implements the {@link Entity} interface.
 * <p>
 * An entity in this context could be a player, an enemy, a bonus item, or any other object that
 * interacts with the game world.
 */
package pdc.project.entity;

public abstract class AbstractEntity implements Entity {
    protected int x, y;
    protected CollisionBox collisionBox;

    public AbstractEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.collisionBox = null;
    }

    public AbstractEntity(int x, int y, CollisionBox collisionBox) {
        this.x = x;
        this.y = y;
        this.collisionBox = collisionBox;
    }

    public AbstractEntity(int x, int y, int width, int height) {
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
}
