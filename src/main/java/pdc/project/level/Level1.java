package pdc.project.level;

import pdc.project.Universe;
import pdc.project.entity.BackgroundEntity;
import pdc.project.entity.Coin;
import pdc.project.entity.Ghost;
import pdc.project.entity.Flag;
import pdc.project.entity.GroundBlock;

public class Level1 implements Level {
    @Override
    public void spawn(Universe universe) {
        Ghost ghost1 = new Ghost(0, 360, universe, 260);
        universe.entities.add(ghost1);
        Ghost ghost2 = new Ghost(0, 220, universe, -290);
        universe.entities.add(ghost2);
        Ghost ghost3 = new Ghost(-474, -120, universe, 50);
        universe.entities.add(ghost3);
        universe.entities.add(new BackgroundEntity(universe, 0, 0));

        int currentX = 0;
        var verticalSpacing = 100;
        int gap = 0;

        while (currentX <= 150) {
            var block = new GroundBlock(universe, currentX, 40);
            universe.entities.add(block);

            currentX += gap; // Update the value of currentX using the gap
            gap = block.getCollisionBox().getWidth(); // Set gap as the width of the block
        }


        int centerX = 0;
        int centerY = 0;
        int squareSize = 1000;

// 计算边框的左上角和右下角坐标
        int leftX = centerX - squareSize / 2;
        int rightX = centerX + squareSize / 2;
        verticalSpacing = 30;

// 左边框
        for (int y = centerY - squareSize / 2; y <= centerY + squareSize / 2; y += verticalSpacing) {
            universe.entities.add(new GroundBlock(universe, -504, y-60));
        }

// 右边框
        for (int y = centerY - squareSize / 2; y <= centerY + squareSize / 2; y += verticalSpacing) {
            universe.entities.add(new GroundBlock(universe, rightX, y-60));
        }
//右下
        currentX = 0;
        while (currentX < 400) {
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


//楼梯
        currentX = 0;
        int currentY = 40;
        int endX = -360;
        int endY = -330;

        while (currentX >= endX && currentY >= endY) {
        var block = new GroundBlock(universe, currentX, currentY);
        universe.entities.add(block);

        currentX -= block.getCollisionBox().getWidth();
        currentY -= block.getCollisionBox().getHeight();
        }

// 左上角的方块1
        var block1 = new GroundBlock(universe, -414, -342);
        universe.entities.add(block1);

        int coinWidth1 = block1.getCollisionBox().getWidth() - 10;
        int coinHeight1 = block1.getCollisionBox().getHeight() - 10;
        int coinX1 = block1.getX();
        int coinY1 = block1.getY() - block1.getCollisionBox().getHeight() / 2 - coinHeight1 / 2;
        Coin coin1 = new Coin(universe, coinX1, coinY1 - 15, coinWidth1, coinHeight1);
        universe.entities.add(coin1);
        var block2 = new GroundBlock(universe, -474 , -342);
        universe.entities.add(block2);
        var block21 = new GroundBlock(universe, -444 , -342);
        universe.entities.add(block21);
        var block4 = new GroundBlock(universe, -384, -312);
        universe.entities.add(block4);
        int coinWidth2 = block2.getCollisionBox().getWidth() - 10;
        int coinHeight2 = block2.getCollisionBox().getHeight() - 10;
        int coinX2 = block2.getX();
        int coinY2 = block2.getY() - block2.getCollisionBox().getHeight() / 2 - coinHeight2 / 2;
        Coin coin2 = new Coin(universe, coinX2, coinY2 - 15, coinWidth2, coinHeight2);
        universe.entities.add(coin2);

// 中间
        var block3 = new GroundBlock(universe, -180, -330);
        universe.entities.add(block3);
        var block5 = new GroundBlock(universe, -150, -330);
        universe.entities.add(block5);
        var block6 = new GroundBlock(universe, -210, -330);
        universe.entities.add(block6);


//左下角竖直墙
        var block7 = new GroundBlock(universe, -120, 180);
        universe.entities.add(block7);
        var block9 = new GroundBlock(universe, -120, 150);
        universe.entities.add(block9);
        var block10 = new GroundBlock(universe, -120, 120);
        universe.entities.add(block10);
        var block36 = new GroundBlock(universe, -300, 180);
        universe.entities.add(block36);
        var block37 = new GroundBlock(universe, -300, 150);
        universe.entities.add(block37);
        var block38 = new GroundBlock(universe, -300, 120);
        universe.entities.add(block38);
//连接的短平台 3
        var block30 = new GroundBlock(universe, -120, 90);
        universe.entities.add(block30);
        var block31 = new GroundBlock(universe, -150, 90);
        universe.entities.add(block31);
        var block32 = new GroundBlock(universe, -180, 90);
        universe.entities.add(block32);
        var block33 = new GroundBlock(universe, -210, 90);
        universe.entities.add(block33);
        var block34 = new GroundBlock(universe, -240, 90);
        universe.entities.add(block34);
        var block35 = new GroundBlock(universe, -270, 90);
        universe.entities.add(block35);
        var block39 = new GroundBlock(universe, -300, 90);
        universe.entities.add(block39);

//放旗子的平台

        var block40 = new GroundBlock(universe, -474, 90);
        universe.entities.add(block40);
        var block41 = new GroundBlock(universe, -444, 240);
        universe.entities.add(block41);
        var block42 = new GroundBlock(universe, -414, 240);
        universe.entities.add(block42);
        int coinWidth41 = block41.getCollisionBox().getWidth() - 10;
        int coinHeight41 = block41.getCollisionBox().getHeight() - 10;
        int coinX41 = block41.getX();
        int coinY41 = block41.getY() - block41.getCollisionBox().getHeight() / 2 - coinHeight41 / 2;
        Coin coin41 = new Coin(universe, coinX41, coinY41 - 15, coinWidth41, coinHeight41);
        universe.entities.add(coin41);

        int coinWidth42 = block42.getCollisionBox().getWidth() - 10;
        int coinHeight42 = block42.getCollisionBox().getHeight() - 10;
        int coinX42 = block42.getX();
        int coinY42 = block42.getY() - block42.getCollisionBox().getHeight() / 2 - coinHeight42 / 2;
        Coin coin42 = new Coin(universe, coinX42, coinY42 - 15, coinWidth42, coinHeight42);
        universe.entities.add(coin42);

//右上4带糖果的方块
        currentX = 0;
        while (currentX < 200) {
        var block = new GroundBlock(universe, currentX, -250);
        universe.entities.add(block);
        int coinWidth = block.getCollisionBox().getWidth() - 10;
        int coinHeight = block.getCollisionBox().getHeight() - 10;
        int coinX = block.getX();
        int coinY = block.getY() - block.getCollisionBox().getHeight() / 2 - coinHeight / 2;
        Coin coin = new Coin(universe, coinX, coinY - 15, coinWidth, coinHeight);
        universe.entities.add(coin);


        currentX +=block.getCollisionBox().getWidth();
        }
        Flag flag = new Flag(universe, -477,50);
        universe.entities.add(flag);
        }
        }
