package pdc.project.ui;

import pdc.project.Database;
import pdc.project.DatabaseDerby;

import javax.swing.*;
import java.awt.*;
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

    public Main() {
        setTitle("Some game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        add(mainPanel);

        mainPanel.add(welcomeScreen, "Welcome");
        mainPanel.add(gameScreen, "Game");

        cardLayout.show(mainPanel, "Welcome");

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
