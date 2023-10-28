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

    private int verticalVelocity = 0;
    private boolean canJump = true;

    public Player(Universe universe, int x, int y) {
        super(universe, x, y, 40 / 2, 70 / 2);
        this.image = Utils.loadImage("/standing.gif");
        this.image = Utils.scaleImage(this.image, 40 / 2, 70 / 2);
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
    }

    private State state = new State.Stand();

    @Override
    public void tick() {
        verticalVelocity += GRAVITY;
        y += verticalVelocity;
        //i cannot understand this logic, probly wrong dude
        if (onGround()) {
            verticalVelocity = 0;
            state = new State.Stand();

            this.image = Utils.loadImage("/standing.gif");
            canJump = true;
        } else {
            this.image = Utils.loadImage("/jumpup.gif");
        }

        if (universe.leftPressed()) {
            x -= 1;
        } else if (universe.rightPressed()) {
            x += 1;
        }
        if (universe.spacePressed() && canJump && state instanceof State.Stand) {
            verticalVelocity = JUMP_SPEED;
            state = new State.Jump();
            this.image = Utils.loadImage("/jumpup.gif");
            canJump = false;
        }
    }

    public boolean onGround() {
        var collisions = universe.getCollisionEntities(this);
        for (Entity entity : collisions) {
            if (entity instanceof GroundBlock) {
                GroundBlock groundBlock = (GroundBlock) entity;
                y = groundBlock.getTopY() - this.getCollisionBox().getHeight();
                return true;
            }
        }
        return false;
    }
}

