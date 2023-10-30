package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.Universe;
import pdc.project.Utils;

/**
 * Represents a block of terrain in the game world, providing functionalities for rendering the terrain block on the
 * screen.
 */
public class GroundBlock extends ImageEntity {

    public GroundBlock(Universe universe, int x, int y) {
        super(universe, x, y, Utils.loadImage("/ground.gif"));
    }
}
