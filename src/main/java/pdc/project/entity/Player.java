package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Player extends ImageEntity implements MoveableEntity, EntityWithVelocity {
    private static final int JUMP_SPEED = -15;
    private static final int GRAVITY = 1;
    private static final int WALK_SPEED_MAX = 5;
    private static final int FLYING_HORIZONTAL_SPEED = 5;

    private final Image standingImage;
    private final Image walkingImage;
    private final Image jumpingImage;
    private final Image squattingImage;

    private int verticalVelocity = 0;
    private int horizontalVelocity = 0;

    private final static double SIZE_RATIO = 0.7;

    public Image loadImage(String s) {
        return Utils.scaleImageByRatio(Utils.loadImage(s), SIZE_RATIO);
    }

    public Player(Universe universe, int x, int y) {
        super(universe, x, y, (int) (40 * SIZE_RATIO), (int) (70 * SIZE_RATIO));
        standingImage = loadImage("/standing.gif");
        walkingImage = loadImage("/walk.gif");
        jumpingImage = loadImage("/jumpup.gif");
        squattingImage = loadImage("/down.gif");
        this.image = standingImage;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getVelocityX() {
        return horizontalVelocity;
    }

    @Override
    public int getVelocityY() {
        return verticalVelocity;
    }

    @Override
    public void setVelocityX(int velocityX) {
        horizontalVelocity = velocityX;
    }

    @Override
    public void setVelocityY(int velocityY) {
        verticalVelocity = velocityY;
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
        if (state instanceof State.Stand) {
            horizontalVelocity = 0;
            if (!onGround()) {
                gotoState(new State.Jump());
            } else {
                if (universe.leftPressed()) {
                    horizontalVelocity -= 1;
                    gotoState(new State.Walk());
                } else if (universe.rightPressed()) {
                    horizontalVelocity += 1;
                    gotoState(new State.Walk());
                } else if (universe.spacePressed()) {
                    verticalVelocity = JUMP_SPEED;
                    gotoState(new State.Jump());
                } else {
                    horizontalVelocity = 0;
                }

                if (universe.downPressed()) {
                    gotoState(new State.Squat());
                }
            }
        } else if (state instanceof State.Walk) {
            if (!onGround()) {
                gotoState(new State.Jump());
            } else {
                if (universe.spacePressed()) {
                    verticalVelocity = JUMP_SPEED;
                    horizontalVelocity = FLYING_HORIZONTAL_SPEED * Integer.signum(horizontalVelocity);
                    gotoState(new State.Jump());
                } else {
                    if (universe.leftPressed()) {
                        horizontalVelocity -= 1;
                        gotoState(new State.Walk());
                    } else if (universe.rightPressed()) {
                        horizontalVelocity += 1;
                        gotoState(new State.Walk());
                    } else {
                        gotoState(new State.Stand());
                    }
                    // Limit the horizontal velocity to the maximum walking speed
                    if (Math.abs(horizontalVelocity) > WALK_SPEED_MAX) {
                        horizontalVelocity = WALK_SPEED_MAX * Integer.signum(horizontalVelocity);
                    }
                }
            }
        } else if (state instanceof State.Jump) {
            if (onGround()) {
                verticalVelocity = 0;
                gotoState(new State.Stand());
            }
        } else if (state instanceof State.Squat) {
            if (onGround()) {
                verticalVelocity = 0;
            }
            if (!universe.downPressed()) {
                gotoState(new State.Stand());
            }
        }

        y += verticalVelocity;
        x += horizontalVelocity;
    }

    public boolean onGround() {
        var collisions = universe.fixOverlappingAndGetCollisionEntities(this);
        for (var info : collisions) {
            if (info.getEntity() instanceof GroundBlock && info.getCollisionInfo().getDirection() == CollisionDirection.DOWN) {
                return true;
            }
        }
        return false;
    }

}
