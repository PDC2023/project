package pdc.project;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class Utils {
    public static Image loadImage(String path) {
        return new ImageIcon(Objects.requireNonNull(Utils.class.getResource(path))).getImage();
    }

    public static Image loadImage(String path, double ratio) {
        return scaleImageByRatio(loadImage(path), ratio);
    }

    public static Image scaleImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }


    public static Image scaleImageByRatio(Image image, double ratio) {
        int newWidth = (int) (image.getWidth(null) * ratio);
        int newHeight = (int) (image.getHeight(null) * ratio);
        return scaleImage(image, newWidth, newHeight);
    }


    public static void drawImage(Graphics2D g2d, Image image, int x, int y) {
        g2d.drawImage(image, (int) x - image.getWidth(null) / 2, (int) y - image.getHeight(null) / 2, null);
    }

    public static void drawImageFlipX(Graphics2D g2d, Image image, int x, int y) {
        g2d.drawImage(image, (int) x + image.getWidth(null) / 2, (int) y - image.getHeight(null) / 2, -image.getWidth(null), image.getHeight(null), null);
    }
}
