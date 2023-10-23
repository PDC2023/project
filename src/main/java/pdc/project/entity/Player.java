/**
 * Represents the player character in the game, providing functionalities for rendering the player on the screen
 * and interacting with the game world. This class extends {@link AbstractEntity} and implements {@link Drawable}.
 */
package pdc.project.entity;

import pdc.project.Drawable;
import pdc.project.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Player extends AbstractEntity {
    public Player(double x, double y) {
        super(x, y, 10, 20);
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
            int currentFrame = 0; // We manually control gif animation play
            private Stand() {
            }
        }
    }

    private State state = new State.Stand();

    private Image gifImage = Utils.loadImage("/mario.gif");

    @Override
    public void draw(Graphics2D g2d) {
        Utils.drawImage(g2d, gifImage, x, y);
    }
}
