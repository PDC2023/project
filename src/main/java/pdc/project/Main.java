package pdc.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Main extends JFrame {
    private Database db;
    {
        try {
            db = new DatabaseDerby();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
        JLabel usernameLabel = new JLabel("Enter Username: ");
        JTextField usernameField = new JTextField(15);

        public WelcomeScreen() {
            setLayout(new FlowLayout());

            // Set default username from the database
            try {
                String defaultUsername = db.getConfigValue("defaultUsername", "thererealba");
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
                        db.saveConfig("defaultUsername", username);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    cardLayout.show(mainPanel, "Game");
                    gameScreen.startGame();
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a username!");
                }
            });
        }
    }
    class GameScreen extends JPanel implements ActionListener {
        private BGMPlayer bgmPlayer = new BGMPlayer();
        private int cameraX = 0;
        private int cameraY = 0;
        private Timer timer = new Timer(10, this);

        JButton backButton = new JButton("Back to Welcome");

        private Player player = new Player(200, 400);

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

            player.draw(g2d, this);

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
