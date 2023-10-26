package pdc.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LossScreen extends JPanel {
    private final Main main;

    public LossScreen(Main main) {
        this.main = main;
        setLayout(new BorderLayout());

        JLabel lossLabel = new JLabel("YOU LOSS", SwingConstants.CENTER);
        lossLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        add(lossLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Welcome");
        add(backButton, BorderLayout.SOUTH);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.cardLayout.show(main.mainPanel, "Welcome");
            }
        });
    }
}
