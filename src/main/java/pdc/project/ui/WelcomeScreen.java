package pdc.project.ui;

import pdc.project.Utils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

class WelcomeScreen extends JPanelWithBackground {
    JButton startButton = new JButton("Start Game");
    JLabel usernameLabel = new JLabel("Enter Username: ");
    JTextField usernameField = new JTextField(20);
    JTextArea tips=new JTextArea();

    public WelcomeScreen(Main main) {
        super(Utils.loadImage("/titlebackground.png"));
        setLayout(new FlowLayout());

        try {
            String defaultUsername = main.db.getConfigValue("defaultUsername", "thererealba");
            usernameField.setText(defaultUsername);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        tips.setText("Use space to jump");
        //set font
        Font Arial =new Font("Arial",Font.BOLD,20);
        tips.setFont(Arial);
        usernameLabel.setFont(Arial);
        startButton.setFont(Arial);
        usernameField.setFont(Arial);
        //set color
        usernameField.setForeground(Color.white);
        tips.setForeground(Color.white);
        usernameField.setOpaque(false);
        tips.setOpaque(false);
        usernameLabel.setForeground(Color.white);

        add(usernameLabel);
        add(usernameField);
        add(startButton);
        add(tips);

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
