package pdc.project;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
/**
 * The {@code Utils} class provides utility methods for common operations related to graphics and images.
 * It includes methods for loading, scaling, and drawing images, as well as flipping images horizontally.
 */
public final class Utils {

    /**
     * Loads an image from the specified resource path.
     *
     * @param path The path to the image resource.
     * @return The loaded image.
     */
    public static Image loadImage(String path) {
        return new ImageIcon(Objects.requireNonNull(Utils.class.getResource(path))).getImage();
    }

    /**
     * Loads an image from the specified resource path and scales it by the given ratio.
     *
     * @param path  The path to the image resource.
     * @param ratio The scaling ratio.
     * @return The loaded and scaled image.
     */
    public static Image loadImage(String path, double ratio) {
        return scaleImageByRatio(loadImage(path), ratio);
    }

    /**
     * Scales an image to the specified width and height.
     *
     * @param image  The image to be scaled.
     * @param width  The target width.
     * @param height The target height.
     * @return The scaled image.
     */
    public static Image scaleImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    /**
     * Scales an image by the specified ratio.
     *
     * @param image The image to be scaled.
     * @param ratio The scaling ratio.
     * @return The scaled image.
     */
    public static Image scaleImageByRatio(Image image, double ratio) {
        int newWidth = (int) (image.getWidth(null) * ratio);
        int newHeight = (int) (image.getHeight(null) * ratio);
        return scaleImage(image, newWidth, newHeight);
    }

    /**
     * Draws an image on a {@link Graphics2D} context with the center of the image positioned at (x, y).
     *
     * @param g2d   The {@link Graphics2D} context used for drawing.
     * @param image The image to be drawn.
     * @param x     The x-coordinate of the center of the image.
     * @param y     The y-coordinate of the center of the image.
     */
    public static void drawImage(Graphics2D g2d, Image image, int x, int y) {
        g2d.drawImage(image, (int) x - image.getWidth(null) / 2, (int) y - image.getHeight(null) / 2, null);
    }

    /**
     * Draws an image flipped horizontally on a {@link Graphics2D} context with the center of the image positioned at (x, y).
     *
     * @param g2d   The {@link Graphics2D} context used for drawing.
     * @param image The image to be drawn.
     * @param x     The x-coordinate of the center of the image.
     * @param y     The y-coordinate of the center of the image.
     */
    public static void drawImageFlipX(Graphics2D g2d, Image image, int x, int y) {
        g2d.drawImage(image, (int) x + image.getWidth(null) / 2, (int) y - image.getHeight(null) / 2, -image.getWidth(null), image.getHeight(null), null);
    }
}
