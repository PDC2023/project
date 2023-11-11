package pdc.project.level;

import pdc.project.Universe;

/**
 * The {@code Level} interface represents a game level within the game universe.
 * Classes implementing this interface define the behavior for spawning entities
 * and configuring the state of the game universe for a specific level.
 */
public interface Level {

    /**
     * Spawns entities and configures the game universe for the level.
     *
     * @param universe The game universe in which the level is to be spawned.
     */
    void spawn(Universe universe);

    int getNumber();
}
