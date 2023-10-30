package pdc.project.level;

import pdc.project.Universe;
import pdc.project.entity.Coin;
import pdc.project.entity.GroundBlock;

public class Level0 implements Level{

    @Override
    public void spawn(Universe universe) {
        var currentX = 0;
        var verticalSpacing = 100;

        while (currentX < 600 / 2) {
            var block = new GroundBlock(universe, currentX, 300 - verticalSpacing);
            universe.entities.add(block);
            currentX += block.getCollisionBox().getWidth();
        }

        currentX = 0;
        while (currentX < 500) {
            var block = new GroundBlock(universe, currentX, 300);
            universe.entities.add(block);

            int coinWidth = block.getCollisionBox().getWidth() - 10;
            int coinHeight = block.getCollisionBox().getHeight() - 10;
            int coinX = block.getX();
            int coinY = block.getY() - block.getCollisionBox().getHeight() / 2 - coinHeight / 2;
            Coin coin = new Coin(universe, coinX, coinY, coinWidth, coinHeight);
            universe.entities.add(coin);

            currentX += block.getCollisionBox().getWidth();
        }

        currentX = 100;
        while (currentX < 600) {
            var block = new GroundBlock(universe, currentX, 300 + verticalSpacing);
            universe.entities.add(block);
            currentX += block.getCollisionBox().getWidth();
        }

        var block = new GroundBlock(universe, 100, 250);
        universe.entities.add(block);
    }
}