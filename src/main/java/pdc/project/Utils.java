package pdc.project;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class Utils {
    public static Image loadImage(String path) {
        return new ImageIcon(Objects.requireNonNull(Utils.class.getResource(path))).getImage();
    }
    public static Image scaleImage(Image image, int width, int height) {
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return scaledImage;
    }

    public static void drawImage(Graphics2D g2d, Image image, int x, int y) {
        g2d.drawImage(image, (int) x - image.getWidth(null) / 2, (int) y - image.getHeight(null) / 2, null);
    }
}
