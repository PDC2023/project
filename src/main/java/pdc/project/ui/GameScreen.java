package pdc.project.ui;

import pdc.project.BGMPlayer;
import pdc.project.Universe;
import pdc.project.entity.Entity;
import pdc.project.entity.GroundBlock;
import pdc.project.entity.Coin;
import pdc.project.entity.Mushroom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class GameScreen extends JPanel {
    private final Main main;
    private BGMPlayer bgmPlayer = new BGMPlayer();
    private int cameraX = 0;
    private int cameraY = 0;
    JButton backButton = new JButton("Back to Welcome");

    Universe universe;

    private Timer timer = new Timer(16, (e) -> {
        tick();
        universe.tick();
        for (var entity : universe.entities) {
            entity.tick();
        }
        repaint();
    });

    private void tick() {
        cameraX = universe.player.getX() - this.getWidth() / 2;
        cameraY = universe.player.getY() - this.getHeight() / 2;
    }

    public GameScreen(Main main) {
        this.main = main;
        this.universe = new Universe(main);
        setLayout(null);
        backButton.setBounds(10, 530, 150, 30);
        add(backButton);
        backButton.addActionListener(e -> {
            returnToMainMenu();
        });

    }

    public void returnToMainMenu(){
        pauseGame();
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

        var block = new GroundBlock(universe, 100, 150);
        universe.entities.add(block);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(-cameraX, -cameraY);

        {
            ArrayList<Entity> draw0 = new ArrayList<>();
            ArrayList<Entity> draw1 = new ArrayList<>();
            ArrayList<Entity> draw2 = new ArrayList<>();
            for (var obj : universe.entities) {
                if (obj instanceof Mushroom) {
                    draw0.add(obj);
                } else if (obj instanceof Coin) {
                    draw1.add(obj);
                } else {
                    draw2.add(obj);
                }
            }
            for(var xs: new ArrayList[]{draw2, draw1, draw0 }){
                for (var obj : (ArrayList<Entity>) xs) {
                    obj.draw(g2d);
                }
            }
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

    public void createUniverseAndStartFreshGame() {
        universe = new Universe(main);
        setUpFlatGroundForTesting();
        resumeGame();
    }

    public void resumeGame() {
        main.activateKeyListener(keyListener);
        timer.start();
        bgmPlayer.startBGM();
    }

    public void pauseGame() {
        main.deactivateKeyListener(keyListener);
        timer.stop();
        bgmPlayer.stopBGM();
    }
}
