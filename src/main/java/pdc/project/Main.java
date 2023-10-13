package pdc.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private WelcomeScreen welcomeScreen = new WelcomeScreen();
    private GameScreen gameScreen = new GameScreen();

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

    class WelcomeScreen extends JPanel {
        JButton startButton = new JButton("Start Game");

        public WelcomeScreen() {
            add(startButton);
            startButton.addActionListener(e -> {
                cardLayout.show(mainPanel, "Game");
                gameScreen.startGame();
            });
        }
    }

    class GameScreen extends JPanel implements ActionListener {
        private BGMPlayer bgmPlayer = new BGMPlayer();
        private int cameraX = 0;
        private int cameraY = 0;
        private Timer timer = new Timer(10, this);

        private Image gifImage = new ImageIcon(getClass().getResource("/mario.gif")).getImage();
        JButton backButton = new JButton("Back to Welcome");

        public GameScreen() {
            setLayout(null);
            backButton.setBounds(10, 530, 150, 30);
            add(backButton);
            backButton.addActionListener(e -> {
                stopGame();
                cardLayout.show(mainPanel, "Welcome");
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Apply the camera
            g2d.translate(-cameraX, -cameraY);
            g2d.drawImage(gifImage, 200, 400, this);

            // Reset the camera for buttons etc.
            g2d.translate(cameraX, cameraY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cameraX += 1;
            cameraY += 1;
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
}
