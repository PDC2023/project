package pdc.project;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class Utils {
    public static Image loadImage(String path){
        return new ImageIcon(Objects.requireNonNull(Utils.class.getResource(path))).getImage();
    }
}
