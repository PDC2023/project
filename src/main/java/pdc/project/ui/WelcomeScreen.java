package pdc.project.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

class WelcomeScreen extends JPanel {
    private final Main main;
    JButton startButton = new JButton("Start Game");
    JLabel usernameLabel = new JLabel("Enter Username: ");
    JTextField usernameField = new JTextField(15);

    public WelcomeScreen(Main main) {
        this.main = main;
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
}
