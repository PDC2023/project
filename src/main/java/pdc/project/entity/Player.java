package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Player extends ImageEntity {
    private static final int JUMP_SPEED = -15;
    private static final int GRAVITY = 1;
    private static final int WALK_SPEED_MAX = 5;

    private final Image standingImage;
    private final Image walkingImage;
    private final Image jumpingImage;
    private final Image squattingImage;

    private int verticalVelocity = 0;
    private int horizontalVelocity = 0;
    private boolean canJump = true;

    public Player(Universe universe, int x, int y) {
        super(universe, x, y, 40, 70);
        standingImage = Utils.loadImage("/standing.gif");
        walkingImage = Utils.loadImage("/walk.gif");
        jumpingImage = Utils.loadImage("/jumpup.gif");
        squattingImage = Utils.loadImage("/down.gif");
        this.image = standingImage;
    }

    interface State {
        final class Jump implements State {
            private Jump() {
            }
        }

        final class Walk implements State {
            private Walk() {
            }
        }

        final class Stand implements State {
            private Stand() {
            }
        }

        final class Squat implements State {
        }
    }

    private State state = new State.Stand();

    public void gotoState(State newState) {
        if (!newState.getClass().equals(this.state.getClass())) {
            this.state = newState;
            updateImageBasedOnState();
        }
    }

    private void updateImageBasedOnState() {
        if (state instanceof State.Stand) {
            this.image = standingImage;
        } else if (state instanceof State.Walk) {
            this.image = walkingImage;
        } else if (state instanceof State.Jump) {
            this.image = jumpingImage;
        } else if (state instanceof State.Squat) {
            this.image = squattingImage;
        }
    }

    @Override
    public void tick() {
        verticalVelocity += GRAVITY;
        if (onGround()) {
            verticalVelocity = 0;
            gotoState(new State.Stand());
            canJump = true;
        }

        if (universe.leftPressed() && state instanceof State.Stand) {
            horizontalVelocity -= 1;
            gotoState(new State.Walk());
        } else if (universe.rightPressed() && state instanceof State.Stand) {
            horizontalVelocity += 1;
            gotoState(new State.Walk());
        } else {
            horizontalVelocity = 0;
        }

        // Limit the horizontal velocity to the maximum walking speed
        if (Math.abs(horizontalVelocity) > WALK_SPEED_MAX) {
            horizontalVelocity = WALK_SPEED_MAX * Integer.signum(horizontalVelocity);
        }

        if (universe.spacePressed() && canJump && state instanceof State.Stand) {
            verticalVelocity = JUMP_SPEED;
            gotoState(new State.Jump());
            canJump = false;
        }

        if (universe.downPressed() && state instanceof State.Stand) {
            gotoState(new State.Squat());
        }

        y += verticalVelocity;
        x += horizontalVelocity;
    }

    public boolean onGround() {
        var collisions = universe.getCollisionEntities(this);
        for (Entity entity : collisions) {
            if (entity instanceof GroundBlock) {
                GroundBlock groundBlock = (GroundBlock) entity;
                y = groundBlock.getTopY() - this.getCollisionBox().getHeight() / 2 + 1;
                return true;
            }
        }
        return false;
    }

}
