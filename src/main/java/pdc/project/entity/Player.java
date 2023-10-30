/**
 * Represents the player character in the game, providing functionalities for rendering the player on the screen
 * and interacting with the game world. This class extends {@link AbstractEntity} and implements {@link Drawable}.
 */
package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.Universe;
import pdc.project.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Player extends ImageEntity {
    private static final int JUMP_SPEED = -15;
    private static final int GRAVITY = 1;
    private static final int Walk_SPEED_MAX = 5;
    private int verticalVelocity = 0;
    private int horizontalVelocity = 0;
    private boolean canJump = true;

    public Player(Universe universe, int x, int y) {
        super(universe, x, y, 40 , 70 );
        this.image = Utils.loadImage("/standing.gif");
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
        final class Squat implements State{

        }
    }

    private State state = new State.Stand();

    @Override
    public void tick() {
        verticalVelocity += GRAVITY;
        if (onGround()) {
            verticalVelocity = 0;
            state = new State.Stand();

            this.image = Utils.loadImage("/standing.gif");
            canJump = true;
        }

        if (universe.leftPressed()&& state instanceof State.Stand) {
            horizontalVelocity -=1;
            this.image = Utils.loadImage("/walk.gif");
        } else if (universe.rightPressed()&& state instanceof State.Stand) {
            this.image = Utils.loadImage("/walk.gif");
            horizontalVelocity+=1;
        }else{
            horizontalVelocity=0;
        }
        if (universe.spacePressed() && canJump && state instanceof State.Stand) {
            verticalVelocity = JUMP_SPEED;
            state = new State.Jump();
            this.image = Utils.loadImage("/jumpup.gif");
            canJump = false;
        }
        //add squat action
        if (universe.downPressed() && state instanceof State.Stand) {
            this.image = Utils.loadImage("/down.gif");
        }
        y += verticalVelocity;
        x += horizontalVelocity;
    }

    public boolean onGround() {
        var collisions = universe.getCollisionEntities(this);
        for (Entity entity : collisions) {
            if (entity instanceof GroundBlock) {
                GroundBlock groundBlock = (GroundBlock) entity;
                y = groundBlock.getTopY() - this.getCollisionBox().getHeight()/2;
                return true;
            }
        }
        return false;
    }
}

