package pdc.project.ui;

import pdc.project.BGMPlayer;
import pdc.project.Drawable;
import pdc.project.Universe;
import pdc.project.entity.Entity;
import pdc.project.entity.GroundBlock;
import pdc.project.entity.Coin;
import pdc.project.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GameScreen extends JPanel {
    private final Main main;
    private BGMPlayer bgmPlayer = new BGMPlayer();
    private int cameraX = 0;
    private int cameraY = 0;
    JButton backButton = new JButton("Back to Welcome");

    Universe universe;

    private Timer timer = new Timer(16, (e) -> {
        universe.tick();
        for (var entity : universe.entities) {
            entity.tick();
        }
        repaint();
    });

    public GameScreen(Main main) {
        this.main = main;
        this.universe = new Universe(main);
        setLayout(null);
        backButton.setBounds(10, 530, 150, 30);
        add(backButton);
        backButton.addActionListener(e -> {
            returnToMainMenu();
        });
        setUpFlatGroundForTesting();

    }

    public void returnToMainMenu(){
        stopGame();
        main.cardLayout.show(main.mainPanel, "Welcome");
    }


    public void setUpFlatGroundForTesting() {
        var currentX = 0;
        var verticalSpacing = 100;

        while (currentX < 600 / 2) {
            var block = new GroundBlock(universe, currentX, 300 - verticalSpacing);
            universe.entities.add(block);
            currentX += block.getCollisionBox().getWidth();
        }

        currentX = 0;
        while (currentX < 600) {
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
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(-cameraX, -cameraY);

        for (var entity : universe.entities) {
            entity.draw(g2d);
        }

        g2d.translate(cameraX, cameraY);
    }

    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyPressed(KeyEvent e) {
            universe.pressedKeys.add(e.getKeyCode());
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    returnToMainMenu();
                    break;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            universe.pressedKeys.remove(e.getKeyCode());
        }
    };

    public void startGame() {
        main.activateKeyListener(keyListener);
        timer.start();
        bgmPlayer.startBGM();
    }

    public void stopGame() {
        main.deactivateKeyListener(keyListener);
        timer.stop();
        bgmPlayer.stopBGM();
    }
}
