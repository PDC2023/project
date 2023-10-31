package pdc.project.ui;

import pdc.project.BGMPlayer;
import pdc.project.Universe;
import pdc.project.entity.Entity;
import pdc.project.entity.Coin;
import pdc.project.entity.Mushroom;
import pdc.project.level.Level0;

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

    private final Timer timer = new Timer(16, (e) -> {
        tick();
        universe.tick();
        for (var entity : universe.entities) {
            entity.tick();
        }
        repaint();
    });

    private void tick() {
        cameraX = (int) (cameraX * 0.8 + universe.player.getX() * 0.2);
        cameraY = (int) (cameraY * 0.8 + universe.player.getY() * 0.2);
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

    public void returnToMainMenu() {
        pauseGame();
        main.cardLayout.show(main.mainPanel, "Welcome");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(this.getWidth() / 2 - cameraX, this.getHeight() / 2 - cameraY);

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
            for (var xs : new ArrayList[]{draw2, draw1, draw0}) {
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
        (new Level0()).spawn(universe);
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
