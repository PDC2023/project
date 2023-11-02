package pdc.project.entity;

import pdc.project.Universe;
import pdc.project.Utils;

public class SavePointResumeEffect extends Effect {

    public SavePointResumeEffect(Universe universe, int x, int y) {
        super(universe, x, y, Utils.loadImage("/circle-loading.gif"));
    }

}
