package pdc.project.ui;
import pdc.project.level.Level;
import pdc.project.Universe;
import pdc.project.BGMPlayer;
import pdc.project.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameScreen extends JPanel {
    private final Main main;
    private BGMPlayer bgmPlayer = new BGMPlayer();
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
//to be deleted?
    private void switchToWinScreen() {
        pauseGame();
        main.cardLayout.show(main.mainPanel, "Win");
    }

    public GameScreen(Main main) {
        this.main = main;
        this.universe = new Universe(main);
        setLayout(null);
        backButton.setBounds(10, 530, 150, 30);add(backButton);
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
            drawCoinCounter(universe, g2d);
        }
    }
    private void drawCoinCounter(Universe universe, Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 42)); // 14 * 3 = 42
        g2d.drawString("Coins: " + universe.getCollectedCoins(), 100, 30);
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

    public String username;
    public int levelNumber;

    public void createUniverseAndStartFreshGame(String username, Level level) {
        initCamera();
        changeLevel(level);
        this.username = username;
        resumeGame();
    }

    private void changeLevel(Level level) {
        universe = new Universe(main);
        (level).spawn(universe);
        levelNumber = level.getNumber();
        resetCamera();
    }

    public void saveCoin() {
        try {
            main.db.saveHighestScore(username, levelNumber, universe.getCollectedCoins());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    //reset cam and universe
    public void winSwitchLevel(Level level1) {
        saveCoin();
        pauseGame();
        changeLevel(level1);
        resumeGame();
    }
}
