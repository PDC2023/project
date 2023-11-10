package pdc.project.ui;

import pdc.project.Utils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

class WelcomeScreen extends JPanel {
    JButton startButton = new JButton("Start Game");
    JLabel usernameLabel = new JLabel("Enter Username: ");
    JTextField usernameField = new JTextField(15);

    public WelcomeScreen(Main main) {
        setLayout(new FlowLayout());

        try {
            String defaultUsername = main.db.getConfigValue("defaultUsername", "thererealba");
            usernameField.setText(defaultUsername);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        add(usernameLabel);
        add(usernameField);
        add(startButton);

        startButton.addActionListener(e -> {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                try {
                    main.db.saveConfig("defaultUsername", username);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                main.cardLayout.show(main.mainPanel, "Game");
                main.gameScreen.createUniverseAndStartFreshGame();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a username!");
            }
        });
    }

    private final static Image background = Utils.loadImage("/titlebackground.png", 1);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
