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
    public Player(Universe universe, int x, int y) {
        super( universe, x, y, 10, 30);
        this.image = Utils.loadImage("/mario.gif");
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
        var collisions = universe.getCollisionEntities(this);
        // falling
        if (collisions.stream().noneMatch(e -> e instanceof GroundBlock)) {
            y += 1;
        }
    }
}
