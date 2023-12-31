package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.*;
import java.util.function.Supplier;

public class Player extends AbstractMovingEntity {
    //speed and gravity
    private static final int JUMP_SPEED = -14;
    private static final int GRAVITY = 1;
    private static final int MAX_FALLING_SPEED = 16;
    private static final int WALK_SPEED_MAX = 9;
    private static final int FLYING_HORIZONTAL_SPEED = 6;
    private static final int JUMPING_FROM_WALL_SPEED =12 ;
    private static final double WALK_SPEED_DELTA = 0.5;
    //image for different states
    private final Image standingImage = loadImage("/standing.gif");
    private final Image walkingImage = loadImage("/walk.gif");
    private final Image jumpingUpImage = loadImage("/jumpup.gif");
    private final Image jumpingDownImage = loadImage("/jumpdown.gif");
    private final Image squattingImage = loadImage("/down.gif");
    private final Image climbImage = loadImage("/climb.gif");

    private final static double SIZE_RATIO = 0.7;

    public Image loadImage(String image) {
        return Utils.scaleImageByRatio(Utils.loadImage(image), SIZE_RATIO);
    }

    public Player(Universe universe, int x, int y) {
        super(universe, x, y, (int) (34 * SIZE_RATIO), (int) (66 * SIZE_RATIO));
        this.image = standingImage;
    }

    interface State {
        default void enter(Player player) {

        }

        interface OnGround extends State {
        }

        class Jump implements State {
            private Jump() {
            }
        }

        class JumpUp extends Jump {
            boolean firstFrame = true;

            private JumpUp() {
            }

            @Override
            public void enter(Player player) {
                player.verticalVelocity = JUMP_SPEED;
                player.horizontalVelocity = FLYING_HORIZONTAL_SPEED * Math.signum(player.horizontalVelocity);
                player.y -= 1;
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
            @Override
            public void enter(Player player) {
                player.horizontalVelocity = 0;
            }
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

    public void nextLevel() {
        universe.nextLevel();
    }
    //Update images except jump
    private void updateImageBasedOnState() {
        if (state instanceof State.Stand) {
            this.image = standingImage;
        } else if (state instanceof State.Walk) {
            this.image = walkingImage;
        } else if (state instanceof State.Squat) {
            this.image = squattingImage;
        } else if (state instanceof State.Climb) {
            this.image = climbImage;
        }
    }
    private void addGravity(){
        verticalVelocity += GRAVITY;
        if (verticalVelocity > MAX_FALLING_SPEED) {
            verticalVelocity = MAX_FALLING_SPEED;
        }
    }

    @Override
    public void tick() {
        addGravity();
        var collisions = universe.fixOverlappingAndGetCollisionEntities(this);
        //check series of states
        Supplier<Boolean> onGroundCheck = () -> {
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
        Supplier<Boolean> leftClimbable = () -> {
            for (var info : collisions) {
                if (info.getEntity() instanceof GroundBlock && info.getCollisionInfo().getDirection() == CollisionDirection.LEFT) {
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
            if (universe.upPressed() && horizontalVelocity >= 0 && leftClimbable.get()) {
                y -= 1;
                gotoState(new State.ClimbLeft());
                return;
            }
        }

        if (state instanceof State.OnGround) {
            if (!onGroundCheck.get()) {
                gotoState(new State.Jump());
                return;
            }
            if (universe.spacePressed()) {
                gotoState(new State.JumpUp());
                return;
            }
            if (!onGroundCheck.get()) {
                gotoState(new State.Jump());
                return;
            } else {
                verticalVelocity = 0;
            }
            if (!(state instanceof State.Squat) && universe.downPressed()) {
                gotoState(new State.Squat());
                return;
            }
        }

        if (state instanceof State.Stand) {
            if (universe.leftPressed()) {
                gotoState(new State.Walk());
                return;
            } else if (universe.rightPressed()) {
                gotoState(new State.Walk());
                return;
            }
        } else if (state instanceof State.Walk) {
            if (universe.leftPressed()) {
                facingLeft = true;
                if (horizontalVelocity > 0) horizontalVelocity = 0;
                horizontalVelocity -= WALK_SPEED_DELTA;
            } else if (universe.rightPressed()) {
                facingLeft = false;
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
            //up and down with different images
            if (verticalVelocity > 0) {
                this.image = jumpingDownImage;
            } else {
                this.image = jumpingUpImage;
            }
            if (onGroundCheck.get()) {
                verticalVelocity = 0;
                gotoState(new State.Stand());
                return;
            }
            if (state instanceof State.JumpUp) {
                if (((State.JumpUp) state).firstFrame) {
                    if (universe.leftPressed()) {
                        horizontalVelocity = -FLYING_HORIZONTAL_SPEED;
                    } else if (universe.rightPressed()) {
                        horizontalVelocity = FLYING_HORIZONTAL_SPEED;
                    }
                    ((State.JumpUp) state).firstFrame = false;
                }
            }
        } else if (state instanceof State.Squat) {
            if (!universe.downPressed()) {
                gotoState(new State.Stand());
                return;
            }
        } else if (state instanceof State.Climb) {
            horizontalVelocity = 0;
            if (onGroundCheck.get()) {
                gotoState(new State.Stand());
                return;
            } else if (state instanceof State.ClimbRight) {
                facingLeft = false;
                //climb to left
                if (universe.leftPressed()) {
                    horizontalVelocity = -JUMPING_FROM_WALL_SPEED;
                    gotoState(new State.Jump());
                    return;
                } else if (universe.upPressed()) {
                    if (rightClimbable.get()) {
                        //climb to jump
                         if (universe.spacePressed()){
                             verticalVelocity=-15;
                             horizontalVelocity = -6;
                            gotoState(new State.Jump());
                         }else verticalVelocity = -3;
                    } else {
                        horizontalVelocity = 1;
                        gotoState(new State.JumpUp());
                        return;
                    }
                } else {
                    gotoState(new State.Jump());
                    return;
                }
            } else if (state instanceof State.ClimbLeft) {
                facingLeft = true;
                //climb to right
                if (universe.rightPressed()) {
                    horizontalVelocity = JUMPING_FROM_WALL_SPEED;
                    gotoState(new State.Jump());

                } else if (universe.upPressed()) {
                    if (leftClimbable.get()) {
                        //climb to jump back
                        if (universe.spacePressed()){
                            verticalVelocity=-15;
                            horizontalVelocity = 6;
                            gotoState(new State.Jump());
                        }else
                            verticalVelocity = -3;
                    } else {
                        horizontalVelocity = -1;
                        gotoState(new State.JumpUp());
                        return;
                    }
                } else {
                    gotoState(new State.Jump());
                    return;
                }

            }

        }

        super.tick();
    }

    public void goingToSavePoint() {
        universe.goingToSavePoint();
    }

    public void win() {
        universe.win();
    }

    public String getUsername() {
        return universe.main.gameScreen.username;
    }

}
