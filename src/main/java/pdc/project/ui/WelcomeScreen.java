package pdc.project.ui;

import pdc.project.Utils;
import pdc.project.level.Level0;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.SQLException;

class WelcomeScreen extends JPanelWithBackground {
    JButton startButton = new JButton("Start Game");
    JLabel usernameLabel = new JLabel("Enter Username: ");
    JTextField usernameField = new JTextField(20);
    JTextArea tips = new JTextArea();

    JLabel scoreLabel = new JLabel("<html><div style='color:white; font-size:20px;'>Level 0 Score: <br>Level 1 Score:</div></html>");

    private void updateScoreLabel() {
        try {
            String username = usernameField.getText().trim();
            int scoreLevel0 = main.db.queryScores(username, 0);
            int scoreLevel1 = main.db.queryScores(username, 1);
            scoreLabel.setText("<html><div style='color:white; font-size:20px;'>Level 0 Score: " + scoreLevel0 + "<br>Level 1 Score: " + scoreLevel1 + "</div></html>");
        } catch (SQLException sqlException) {
            throw new RuntimeException("Error querying scores: " + sqlException.getMessage(), sqlException);
        }
    }


    private final Main main;


    public WelcomeScreen(Main main) {
        super(Utils.loadImage("/titlebackground.png"));
        setLayout(new FlowLayout());
        this.main = main;


        tips.setText("Use space to jump\nUse Up key to climb \nUse Down key to squat ");
        //set font
        Font Arial = new Font("Arial", Font.BOLD, 20);
        tips.setFont(Arial);
        usernameLabel.setFont(Arial);
        startButton.setFont(Arial);
        usernameField.setFont(Arial);
        //set color
        usernameField.setForeground(Color.white);
        tips.setForeground(Color.white);
        usernameLabel.setForeground(Color.white);

        usernameField.setOpaque(false);
        tips.setOpaque(false);

        updateScoreLabel();
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateScoreLabel();
            }

            public void removeUpdate(DocumentEvent e) {
                updateScoreLabel();
            }

            public void insertUpdate(DocumentEvent e) {
                updateScoreLabel();
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(startButton);
        add(tips);
        add(scoreLabel);
        try {
            String defaultUsername = main.db.getConfigValue("defaultUsername", "thererealba");
            usernameField.setText(defaultUsername);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        startButton.addActionListener(e -> {
            String username = usernameField.getText();
            if (username.isEmpty()) {
                //JOptionPane.showMessageDialog(this, "Please enter a username!"); return;
                username = "anonymous";
            }
            try {
                main.db.saveConfig("defaultUsername", username);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            main.cardLayout.show(main.mainPanel, "Game");
            main.gameScreen.createUniverseAndStartFreshGame(username, new Level0());
        });
    }
}
