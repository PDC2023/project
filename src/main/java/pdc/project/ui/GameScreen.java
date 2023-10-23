package pdc.project.ui;

import pdc.project.BGMPlayer;
import pdc.project.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameScreen extends JPanel implements ActionListener {
    private final Main main;
    private BGMPlayer bgmPlayer = new BGMPlayer();
    private int cameraX = 0;
    private int cameraY = 0;
    private Timer timer = new Timer(10, this);

    JButton backButton = new JButton("Back to Welcome");

    private Player player = new Player(200, 400);

    public GameScreen(Main main) {
        this.main = main;
        setLayout(null);
        backButton.setBounds(10, 530, 150, 30);
        add(backButton);
        backButton.addActionListener(e -> {
            stopGame();
            main.cardLayout.show(main.mainPanel, "Welcome");
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Apply the camera
        g2d.translate(-cameraX, -cameraY);

        player.draw(g2d, this);

        // Reset the camera for buttons etc.
        g2d.translate(cameraX, cameraY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void startGame() {
        timer.start();
        bgmPlayer.startBGM();
    }

    public void stopGame() {
        timer.stop();
        bgmPlayer.stopBGM();
    }
}
