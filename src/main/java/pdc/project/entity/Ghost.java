package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

public class Ghost extends AbstractMovingEntity implements Enemy {
    private final static double SIZE_RATIO = 1.0;

    private final static double SPEED = 1;

    private final int xEnd;
    private final int xStart;

    public Ghost(int xStart, int xEnd, Universe universe, int y) {
        super(universe, xStart, y, (int) (32 * SIZE_RATIO), (int) (32 * SIZE_RATIO), Utils.loadImage("/ghost.png", SIZE_RATIO));
        imageFacingRight = false;
        horizontalVelocity = SPEED;
        this.xStart = xStart;
        this.xEnd = xEnd;
        if (xEnd <= xStart) {
            throw new IllegalArgumentException("xEnd must be greater than xStart");
        }
    }

    @Override
    public void tick() {
        var collision = this.getCollision(getUniverse().player);
        if (collision.getDirection() == CollisionDirection.UP) {
            die();
            return;
        } else if (collision.getState() != CollisionState.NONE) {
            getUniverse().player.lose();
            return;
        }
        if (horizontalVelocity > 0) {
            if (x >= xEnd - getCollisionBox().getWidth()) {
                horizontalVelocity = -SPEED;
            }
        } else {
            if (x <= xStart) {
                horizontalVelocity = SPEED;
            }
        }
        super.tick();
    }
}

