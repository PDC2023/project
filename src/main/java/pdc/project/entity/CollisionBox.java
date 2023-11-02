package pdc.project.entity;

import java.awt.*;
/**
 * Represents a rectangular collision box used for detecting collisions between entities in the game.
 * This class provides functionalities to set and retrieve the dimensions of the collision box.
 */
public class CollisionBox {
    private int width;
    private int height;

    /**
     * Constructs a collision box with the specified width and height.
     *
     * @param width  The width of the collision box.
     * @param height The height of the collision box.
     */
    public CollisionBox(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of this collision box.
     *
     * @return The width of the collision box.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of this collision box.
     *
     * @return The height of the collision box.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Creates a collision box based on the dimensions of the provided image.
     *
     * @param image The image used to determine the dimensions of the collision box.
     * @return A new collision box with dimensions matching those of the image.
     */
    public static CollisionBox of(Image image){
        // TODO: This method might have some bug for gif. working fine for ground.png
        return new CollisionBox(image.getWidth(null), image.getHeight(null));
    }
}
