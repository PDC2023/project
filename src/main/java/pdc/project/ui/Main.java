package pdc.project.ui;
import pdc.project.level.Level1;
import pdc.project.Database;
import pdc.project.DatabaseDerby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

public class Main extends JFrame {
    Database db;

    {
        try {
            db = new DatabaseDerby();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    WelcomeScreen welcomeScreen = new WelcomeScreen(this);
    public GameScreen gameScreen = new GameScreen(this);
    WinScreen winScreen = new WinScreen(this);

    boolean FULLSCREEN = true; // Currently for debug

    public Main() {
        setTitle("Get the lolly");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);

        mainPanel.add(welcomeScreen, "Welcome");
        mainPanel.add(gameScreen,   "Game");
        mainPanel.add(winScreen, "Win");
        cardLayout.show(mainPanel, "Welcome");

        setFocusable(true);
        if (FULLSCREEN) {
            setUndecorated(true);
            setResizable(false);
            //setDisplayMode();
            setFullscreenWithDefaultResolution();
        } else {
            setSize(1280, 720);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    private void setFullscreenWithDefaultResolution() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        gd.setFullScreenWindow(this);
    }

    private void setDisplayMode() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] supportedModes = gd.getDisplayModes();
        DisplayMode newDisplayMode = null;

        System.out.println("Supported Display Modes:");
        for (DisplayMode mode : supportedModes) {
            System.out.printf("Width: %d, Height: %d, Bit Depth: %d, Refresh Rate: %d Hz%n",
                    mode.getWidth(), mode.getHeight(), mode.getBitDepth(), mode.getRefreshRate());

            if (mode.getWidth() == 1280 && mode.getHeight() == 720) {
                newDisplayMode = mode;
            }
        }
        if (newDisplayMode != null) {
            if (gd.isFullScreenSupported()) {
                gd.setFullScreenWindow(this);

                if (gd.isDisplayChangeSupported()) {
                    try {
                        gd.setDisplayMode(newDisplayMode);
                        System.out.println("Display mode changed successfully!");
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Display mode change not supported", e);
                    }
                } else {
                    throw new RuntimeException("Display mode change not supported");
                }
            } else {
                //this.setSize(1024, 768);
                //this.setLocationRelativeTo(null);
                //this.setVisible(true);
                throw new RuntimeException("display mode not found");
            }
        } else {
            throw new RuntimeException("display mode not found");
        }

        this.setSize(1280, 720);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Main.this.repaint();
            }
        });
    }

    public void activateKeyListener(KeyListener keyListener) {
        this.addKeyListener(keyListener);
    }

    public void deactivateKeyListener(KeyListener keyListener) {
        this.removeKeyListener(keyListener);
    }

    public void switchToLossScreen() {
        cardLayout.show(mainPanel, "Loss");
        gameScreen.pauseGame();
    }

    public void switchToWinScreen() {
        cardLayout.show(mainPanel, "Win");
        gameScreen.pauseGame();
    }
    public void switchToLevel1() {
        SwingUtilities.invokeLater(() -> {
            Level1 level1 = new Level1();
            gameScreen.winSwitchLevel(level1);
            cardLayout.show(mainPanel, "Game");
        });
    }

    public void switchToWelcomeScreen() {
        cardLayout.show(mainPanel, "Welcome");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}

