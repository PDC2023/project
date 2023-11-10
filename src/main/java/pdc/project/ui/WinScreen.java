package pdc.project.ui;

import pdc.project.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WinScreen extends JPanelWithBackground {
    private Main main;
    private JButton backButton;
    private JLabel winLabel;

    public WinScreen(Main main) {
        super(Utils.loadImage("/win.png"));
        this.main = main;
        setLayout(new BorderLayout());

        winLabel = new JLabel("YOU WIN!");
        winLabel.setForeground(Color.white);
        winLabel.setHorizontalAlignment(JLabel.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(winLabel, BorderLayout.CENTER);

        backButton = new JButton("Return");
        backButton.addActionListener(e -> main.switchToWelcomeScreen());
        add(backButton, BorderLayout.SOUTH);
    }

    private final static Image background = Utils.loadImage("/win.png");

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
