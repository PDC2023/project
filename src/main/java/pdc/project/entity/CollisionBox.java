/**
 * Represents a rectangular collision box used for detecting collisions between entities in the game.
 * This class provides functionalities to set and retrieve the dimensions of the collision box.
 */
package pdc.project.entity;

public class CollisionBox {
    private double width;
    private double height;

    public CollisionBox(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
