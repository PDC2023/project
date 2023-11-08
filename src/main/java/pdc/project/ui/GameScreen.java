package pdc.project.ui;
import pdc.project.level.Level1;
import pdc.project.Universe;
import pdc.project.BGMPlayer;
import pdc.project.Universe;
import pdc.project.entity.*;
import pdc.project.level.Level0;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameScreen extends JPanel {
    private final Main main;
    private BGMPlayer bgmPlayer = new BGMPlayer();
    private CoinCounter coinCounter;
    private int cameraX;
    private int cameraY;
    JButton backButton = new JButton("Back to Welcome");

    Universe universe;

    private boolean pauseForSavingPoint = false;

    private final Timer timer = new Timer(16, (e) -> tick());
    private void resetCamera(){
        cameraX = 0;
        cameraY = 0;
    }

    private void tick() {
        universe.preTick();
        if (!pauseForSavingPoint) {
            cameraX = (int) (cameraX * 0.8 + universe.player.getX() * 0.2);
            cameraY = (int) (cameraY * 0.8 + universe.player.getY() * 0.2);
            universe.tick();
        }
        repaint();
    }

    private void switchToWinScreen() {
        pauseGame();
        main.cardLayout.show(main.mainPanel, "Win");
    }

    public GameScreen(Main main) {
        this.main = main;
        this.universe = new Universe(main);
        coinCounter = new CoinCounter();
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
                } else if (obj instanceof Enemy) {
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
        {
            g2d.setColor(Color.WHITE);
            coinCounter.draw(universe, g2d);
        }
    }

    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyPressed(KeyEvent e) {
            universe.pressedKeys.add(e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                returnToMainMenu();
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

    public void pauseForReturningToSavePoint() {
        pauseForSavingPoint = true;
        main.deactivateKeyListener(keyListener);
    }

    public void resumeForReturningToSavePoint() {
        pauseForSavingPoint = false;
        universe.pressedKeys.clear();
        main.activateKeyListener(keyListener);
    }
    public void setLevel(Level1 level1) {
        pauseGame();
        universe.entities.clear();
        level1.spawn(universe);
        resetCamera();
        universe.Reset();
        resumeGame();
    }
}
