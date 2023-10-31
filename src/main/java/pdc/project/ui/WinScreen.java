package pdc.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WinScreen extends JPanel {
    private Main main;
    private JButton backButton;
    private JLabel winLabel;

    public WinScreen(Main main) {
        this.main = main;
        setLayout(new BorderLayout());

        winLabel = new JLabel("YOU WIN!");
        winLabel.setHorizontalAlignment(JLabel.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(winLabel, BorderLayout.CENTER);

        backButton = new JButton("Return");
        backButton.addActionListener(e -> main.switchToWelcomeScreen());
        add(backButton, BorderLayout.SOUTH);
    }
}
