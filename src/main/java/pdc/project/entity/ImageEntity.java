package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;

/**
 * The {@code ImageEntity} class is an abstract base class for entities that
 * are represented by an image and can be drawn on the screen. It extends the
 * {@link AbstractEntity} class and provides functionality for handling images.
 * Subclasses of this class should implement specific behavior for the entities
 * they represent.
 */
public abstract class ImageEntity extends AbstractEntity {

    /** The image representing this entity. */
    Image image;

    /**
     * Constructs an {@code ImageEntity} with a specified image, position, and collision box.
     *
     * @param universe   The universe to which this entity belongs.
     * @param x          The X-coordinate of the entity's position.
     * @param y          The Y-coordinate of the entity's position.
     * @param image      The image to be associated with this entity.
     */
    public ImageEntity(Universe universe, int x, int y, Image image) {
        super(universe, x, y, CollisionBox.of(image));
        this.image = image;
    }

    /**
     * Constructs an {@code ImageEntity} with a specified image, position, width, and height.
     *
     * @param universe   The universe to which this entity belongs.
     * @param x          The X-coordinate of the entity's position.
     * @param y          The Y-coordinate of the entity's position.
     * @param width      The width of the entity's collision box.
     * @param height     The height of the entity's collision box.
     * @param image      The image to be associated with this entity.
     */
    public ImageEntity(Universe universe, int x, int y, int width, int height, Image image) {
        super(universe, x, y, width, height);
        this.image = image;
    }

    /**
     * Constructs an {@code ImageEntity} with a specified position, width, and height,
     * but without an associated image.
     *
     * @param universe   The universe to which this entity belongs.
     * @param x          The X-coordinate of the entity's position.
     * @param y          The Y-coordinate of the entity's position.
     * @param width      The width of the entity's collision box.
     * @param height     The height of the entity's collision box.
     */
    public ImageEntity(Universe universe, int x, int y, int width, int height) {
        super(universe, x, y, width, height);
        this.image = null;
    }

    /**
     * Draws the entity on the screen using the provided {@code Graphics2D} object.
     *
     * @param g2d The {@code Graphics2D} object used for drawing.
     */
    @Override
    public void draw(Graphics2D g2d) {
        Utils.drawImage(g2d, image, x, y);
    }
}
