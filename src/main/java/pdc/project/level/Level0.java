package pdc.project.level;

import pdc.project.Universe;
import pdc.project.entity.BackgroundEntity;
import pdc.project.entity.Coin;
import pdc.project.entity.Ghost;
import pdc.project.entity.Flag;
import pdc.project.entity.GroundBlock;

public class Level0 implements Level {

    @Override
    public void spawn(Universe universe) {
        Ghost ghost1 = new Ghost(0, 600, universe, 270);
        universe.entities.add(ghost1);

        universe.entities.add(new BackgroundEntity(universe, 0, 0));
        var currentX = 0;
        var verticalSpacing = 100;
        int gap = 0;

        while (currentX < 600 / 2) {
            var block = new GroundBlock(universe, currentX, 300 - verticalSpacing);
            universe.entities.add(block);
            currentX += block.getCollisionBox().getWidth();
            gap = block.getCollisionBox().getHeight();
        }

        currentX = 0;
        while (currentX < 500) {
            var block = new GroundBlock(universe, currentX, 300);
            universe.entities.add(block);

            int coinWidth = block.getCollisionBox().getWidth() - 10;
            int coinHeight = block.getCollisionBox().getHeight() - 10;
            int coinX = block.getX();
            int coinY = block.getY() - block.getCollisionBox().getHeight() / 2 - coinHeight / 2;
            Coin coin = new Coin(universe, coinX, coinY - 15, coinWidth, coinHeight);
            universe.entities.add(coin);

            currentX += block.getCollisionBox().getWidth();
        }

        currentX = 200;
        while (currentX < 580) {
            var block = new GroundBlock(universe, currentX, 300 + verticalSpacing);
            universe.entities.add(block);
            currentX += block.getCollisionBox().getWidth();
        }
        currentX = 660;
        while (currentX < 900) {
            var block = new GroundBlock(universe, currentX, 10 + verticalSpacing);
            universe.entities.add(block);
            currentX += block.getCollisionBox().getWidth();
        }
        currentX =800;
        while (currentX < 1000) {
            var block = new GroundBlock(universe, currentX, 200+verticalSpacing);
            universe.entities.add(block);
            currentX += block.getCollisionBox().getWidth();
        }
        //add wall
        for (var y = 0; y < 590; y = y + gap) {
            var block = new GroundBlock(universe, 600, y);
            universe.entities.add(block);
        }

        //add flag
        Flag flag = new Flag(universe, 850,193+verticalSpacing-gap);
        universe.entities.add(flag);
    }
}
