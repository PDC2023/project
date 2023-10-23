package pdc.project.ui;

import pdc.project.BGMPlayer;
import pdc.project.Drawable;
import pdc.project.Universe;
import pdc.project.entity.Entity;
import pdc.project.entity.GroundBlock;
import pdc.project.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

class GameScreen extends JPanel {
    private final Main main;
    private BGMPlayer bgmPlayer = new BGMPlayer();
    private int cameraX = 0;
    private int cameraY = 0;
    JButton backButton = new JButton("Back to Welcome");

    Universe universe = new Universe();

    private Timer timer = new Timer(10, (e) -> {
        for (var entity : universe.entities) {
            entity.tick();
        }
        repaint();
    });

    public GameScreen(Main main) {
        this.main = main;
        setLayout(null);
        backButton.setBounds(10, 530, 150, 30);
        add(backButton);
        backButton.addActionListener(e -> {
            stopGame();
            main.cardLayout.show(main.mainPanel, "Welcome");
        });
        setUpFlatGroundForTesting();
    }

    public void setUpFlatGroundForTesting() {
        var currentX = 0;
        while (currentX < 600) {
            var block = new GroundBlock(currentX, 300);
            universe.entities.add(block);
            currentX += block.getCollisionBox().getWidth();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Apply the camera
        g2d.translate(-cameraX, -cameraY);

        for (var entity : universe.entities) {
            entity.draw(g2d);
        }

        // Reset the camera for buttons etc.
        g2d.translate(cameraX, cameraY);
    }

    public void startGame() {
        timer.start();
        bgmPlayer.startBGM();
    }

    public void stopGame() {
        timer.stop();
        bgmPlayer.stopBGM();
    }
}
