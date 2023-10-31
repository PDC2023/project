package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;
import java.util.function.Supplier;

public class Player extends ImageEntity implements MoveableEntity, EntityWithVelocity {
    private static final int JUMP_SPEED = -15;
    private static final int GRAVITY = 1;
    private static final int WALK_SPEED_MAX = 5;
    private static final int FLYING_HORIZONTAL_SPEED = 5;
    private static final double WALK_SPEED_DELTA = 0.5;

    private final Image standingImage;
    private final Image walkingImage;
    private final Image jumpingImage;
    private final Image squattingImage;

    private double verticalVelocity = 0;
    private double horizontalVelocity = 0;

    private final static double SIZE_RATIO = 0.7;

    public boolean facingLeft = false;

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
    public double getVelocityX() {
        return horizontalVelocity;
    }

    @Override
    public double getVelocityY() {
        return verticalVelocity;
    }

    @Override
    public void setVelocityX(double velocityX) {
        horizontalVelocity = velocityX;
    }

    @Override
    public void setVelocityY(double velocityY) {
        verticalVelocity = velocityY;
    }

    interface State {
        default void enter(Player player) {

        }

        interface OnGround extends State {
        }

        final class Jump implements State {
            private Jump() {
            }
        }

        final class Walk implements State, OnGround {
            private Walk() {
            }
        }

        final class Stand implements State, OnGround {
            private Stand() {
            }

            @Override
            public void enter(Player player) {
                player.horizontalVelocity = 0;
            }
        }

        final class Squat implements State, OnGround {
        }

        abstract class Climb implements State {

        }

        final class ClimbRight extends Climb {

        }

        final class ClimbLeft extends Climb {

        }
    }

    private State state = new State.Stand();

    private void gotoState(State newState) {
        if (!newState.getClass().equals(this.state.getClass())) {
            this.state = newState;
            this.state.enter(this);
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
        } else if (state instanceof State.Climb) {
            this.image = squattingImage; // TODO: correct image
        }
    }

    @Override
    public void tick() {
        verticalVelocity += GRAVITY;
        var collisions = universe.fixOverlappingAndGetCollisionEntities(this);

        Supplier<Boolean> onGround = () -> {
            for (var info : collisions) {
                if (info.getEntity() instanceof GroundBlock && info.getCollisionInfo().getDirection() == CollisionDirection.DOWN) {
                    return true;
                }
            }
            return false;
        };

        Supplier<Boolean> rightClimbable = () -> {
            for (var info : collisions) {
                if (info.getEntity() instanceof GroundBlock && info.getCollisionInfo().getDirection() == CollisionDirection.RIGHT) {
                    return true;
                }
            }
            return false;
        };

        if (!(state instanceof State.Climb)) {
            if (universe.upPressed() && horizontalVelocity >= 0 && rightClimbable.get()) {
                y -= 1;
                gotoState(new State.ClimbRight());
                return;
            }
        }

        if (state instanceof State.OnGround) {
            if (!onGround.get()) {
                gotoState(new State.Jump());
                return;
            }
            if (universe.spacePressed()) {
                verticalVelocity = JUMP_SPEED;
                horizontalVelocity = FLYING_HORIZONTAL_SPEED * Math.signum(horizontalVelocity);
                y -= 1;
                gotoState(new State.Jump());
                return;
            }
            if (!onGround.get()) {
                gotoState(new State.Jump());
                return;
            } else {
                verticalVelocity = 0;
            }
        }

        if (horizontalVelocity > 0) {
            facingLeft = false;
        } else if (horizontalVelocity < 0) {
            facingLeft = true;
        }

        if (state instanceof State.Stand) {
            if (universe.leftPressed()) {
                gotoState(new State.Walk());
                return;
            } else if (universe.rightPressed()) {
                gotoState(new State.Walk());
                return;
            } else if (universe.downPressed()) {
                gotoState(new State.Squat());
                return;
            }
        } else if (state instanceof State.Walk) {
            if (universe.leftPressed()) {
                if (horizontalVelocity > 0) horizontalVelocity = 0;
                horizontalVelocity -= WALK_SPEED_DELTA;
            } else if (universe.rightPressed()) {
                if (horizontalVelocity < 0) horizontalVelocity = 0;
                horizontalVelocity += WALK_SPEED_DELTA;
            } else {
                gotoState(new State.Stand());
                return;
            }
            // Limit the horizontal velocity to the maximum walking speed
            if (Math.abs(horizontalVelocity) > WALK_SPEED_MAX) {
                horizontalVelocity = WALK_SPEED_MAX * Math.signum(horizontalVelocity);
            }
        } else if (state instanceof State.Jump) {
            if (onGround.get()) {
                verticalVelocity = 0;
                gotoState(new State.Stand());
                return;
            }
        } else if (state instanceof State.Squat) {
            if (!universe.downPressed()) {
                gotoState(new State.Stand());
                return;
            }
        } else if (state instanceof State.Climb) {
            horizontalVelocity = 0;
            if (onGround.get()) {
                gotoState(new State.Stand());
                return;
            } else {
                if (state instanceof State.ClimbRight) {
                    if (universe.leftPressed()) {
                        horizontalVelocity = -FLYING_HORIZONTAL_SPEED;
                        gotoState(new State.Jump());
                        return;
                    } else if (universe.upPressed() && rightClimbable.get()) {
                        verticalVelocity = -1;
                    } else {
                        gotoState(new State.Jump());
                        return;
                    }
                }
            }
        }

        y += (int) verticalVelocity;
        x += (int) horizontalVelocity;
    }


    @Override
    public void draw(Graphics2D g2d) {
        if (facingLeft) {
            Utils.drawImageFlipX(g2d, image, x, y);
        } else {
            Utils.drawImage(g2d, image, x, y);
        }
    }

}
