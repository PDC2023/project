/**
 * Represents a block of terrain in the game world, providing functionalities for rendering the terrain block on the
 * screen. This class extends {@link AbstractEntity} and implements {@link Drawable}.
 */
package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.Utils;

import java.awt.*;
import java.awt.image.ImageObserver;

public class GroundBlock extends ImageEntity {
    public GroundBlock(double x, double y) {
        super(x, y, Utils.loadImage("/ground.gif"));
    }
}