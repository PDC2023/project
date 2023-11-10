package pdc.project.ui;

import javax.swing.*;
import java.awt.*;

abstract class JPanelWithBackground extends JPanel {
    private final Image background;

    JPanelWithBackground(Image background) {
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
