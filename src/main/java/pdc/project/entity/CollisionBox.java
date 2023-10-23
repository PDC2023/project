/**
 * Represents a rectangular collision box used for detecting collisions between entities in the game.
 * This class provides functionalities to set and retrieve the dimensions of the collision box.
 */
package pdc.project.entity;

import java.awt.*;

public class CollisionBox {
    private int width;
    private int height;

    public CollisionBox(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static CollisionBox of(Image image){
        return new CollisionBox(image.getWidth(null), image.getHeight(null));
    }
}
