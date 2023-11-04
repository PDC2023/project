package pdc.project.ui;

import pdc.project.Drawable;
import pdc.project.Universe;

import java.awt.*;

public class CoinCounter {

    public void draw(Universe universe, Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 42)); // 14 * 3 = 42
        g2d.drawString("Coins: " + universe.getCollectedCoins(), 100, 30);
    }

}
