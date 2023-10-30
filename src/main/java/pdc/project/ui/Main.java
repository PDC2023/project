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
        // setVisible(true);
        // setSize(size.width/2, size.height/2);
        device.setFullScreenWindow(this);
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

