package pdc.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LossScreen extends JPanel {

    public LossScreen(Main main) {
        setLayout(new BorderLayout());

        JLabel lossLabel = new JLabel("YOU LOSE", SwingConstants.CENTER);
        lossLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        add(lossLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Welcome");
        add(backButton, BorderLayout.SOUTH);
        backButton.addActionListener(e -> main.cardLayout.show(main.mainPanel, "Welcome"));
    }
}