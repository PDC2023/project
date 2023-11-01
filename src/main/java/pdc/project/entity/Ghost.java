package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

public class Ghost extends AbstractMovingEntity implements Enemy {
    private final static double SIZE_RATIO = 1.0;

    private final static double SPEED = 0.5;

    public Ghost(Universe universe, int x, int y) {
        super(universe, x, y, (int) (32 * SIZE_RATIO), (int) (32 * SIZE_RATIO), Utils.loadImage("/ghost.png", SIZE_RATIO));
        imageFacingRight = false;
        horizontalVelocity = SPEED;
    }

    @Override
    public void tick() {
        var collision = this.getCollision(getUniverse().player);
        if (collision.getDirection() == CollisionDirection.UP) {
            die();
            return;
        } else if (collision.getState() != CollisionState.NONE) {
            getUniverse().player.lose();
        }
        if (horizontalVelocity > 0) {
            if (x >= 600 - getCollisionBox().getWidth()) {
                horizontalVelocity = -SPEED;
            }
        } else {
            if (x <= 0) {
                horizontalVelocity = SPEED;
            }
        }
        super.tick();
    }
}

