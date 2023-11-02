package pdc.project.ui;

import pdc.project.Drawable;
import pdc.project.Universe;

import java.awt.*;

public class CoinCounter implements Drawable {
    private Universe universe;

    public CoinCounter(Universe universe) {
        this.universe = universe;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 42)); // 14 * 3 = 42
        g2d.drawString("Coins: " + universe.getCollectedCoins(), 100, 30);
    }

}
