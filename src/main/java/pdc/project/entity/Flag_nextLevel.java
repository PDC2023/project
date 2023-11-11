package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

import java.awt.Graphics2D;

public class Flag_nextLevel extends Flag {

    public Flag_nextLevel(Universe universe, int x, int y) {
        super(universe, x, y);
    }

    @Override
    public void done() {
        getUniverse().player.nextLevel();
    }
}




