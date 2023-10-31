package pdc.project.ui;

import pdc.project.BGMPlayer;
import pdc.project.Universe;
import pdc.project.entity.*;
import pdc.project.level.Level0;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class GameScreen extends JPanel {
    private final Main main;
    private BGMPlayer bgmPlayer = new BGMPlayer();
    private int cameraX;
    private int cameraY;
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
            ArrayList<Entity>[] draw = new ArrayList[5];
            for (int i = 0; i < draw.length; i++) {
                draw[i] = new ArrayList<Entity>();
            }
            for (var obj : universe.entities) {
                if (obj instanceof Player) {
                    draw[0].add(obj);
                } else if (obj instanceof Mushroom) {
                    draw[1].add(obj);
                } else if (obj instanceof Coin) {
                    draw[2].add(obj);
                } else if (obj instanceof BackgroundEntity) {
                    draw[4].add(obj);
                } else {
                    draw[3].add(obj);
                }
            }
            for (int i = draw.length - 1; i >= 0; i--) {
                for (var obj : draw[i]) {
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

    public void initCamera() {
        cameraX = -100;
        cameraY = -100;
    }

    public void createUniverseAndStartFreshGame() {
        initCamera();
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
