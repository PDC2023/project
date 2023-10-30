package pdc.project.ui;

import pdc.project.Database;
import pdc.project.DatabaseDerby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
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
    GameScreen gameScreen = new GameScreen(this);
    LossScreen lossScreen = new LossScreen(this);  // Adding LossScreen instance here

    public Main() {
        setTitle("Some game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);

        mainPanel.add(welcomeScreen, "Welcome");
        mainPanel.add(gameScreen, "Game");
        mainPanel.add(lossScreen, "Loss");

        cardLayout.show(mainPanel, "Welcome");

        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        setFocusable(true);
        setUndecorated(true);
        setResizable(false);
        setDisplayMode();
    }

    private void setDisplayMode() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] supportedModes = gd.getDisplayModes();
        DisplayMode newDisplayMode = null;

        System.out.println("Supported Display Modes:");
        for (DisplayMode mode : supportedModes) {
            System.out.printf("Width: %d, Height: %d, Bit Depth: %d, Refresh Rate: %d Hz%n",
                    mode.getWidth(), mode.getHeight(), mode.getBitDepth(), mode.getRefreshRate());

            if (mode.getWidth() == 1024 && mode.getHeight() == 768) {
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
                throw new RuntimeException("1024x768 display mode not found");
            }
        } else {
            throw new RuntimeException("1024x768 display mode not found");
        }

        this.setSize(1024, 768);
    }

    public  void activateKeyListener(KeyListener keyListener) {
        this.addKeyListener(keyListener);
    }

    public void deactivateKeyListener(KeyListener keyListener) {
        this.removeKeyListener(keyListener);
    }

    public void switchToLossScreen() {
        cardLayout.show(mainPanel, "Loss");
        gameScreen.stopGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}

