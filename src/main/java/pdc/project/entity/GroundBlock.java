/**
 * Represents a block of terrain in the game world, providing functionalities for rendering the terrain block on the
 * screen. This class extends {@link AbstractEntity} and implements {@link Drawable}.
 */
package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

public class GroundBlock extends ImageEntity {

    public GroundBlock(Universe universe, int x, int y) {
        super(universe, x, y, Utils.loadImage("/ground.gif"));
    }
}
