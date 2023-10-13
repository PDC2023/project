package pdc.project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JPanel implements ActionListener {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Some game");
            Main panel = new Main();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(panel);
            frame.setVisible(true);
        });
    }

    private BGMPlayer bgmPlayer = new BGMPlayer();
    private int cameraX = 0;
    private int cameraY = 0;
    private Timer timer = new Timer(10, this);

    // Load gifImage from resources
    private Image gifImage = new ImageIcon(getClass().getResource("/mario.gif")).getImage();

    public Main() {
        timer.start();
        bgmPlayer.startBGM();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(-cameraX, -cameraY);

        // Draw the GIF
        g2d.drawImage(gifImage, 200, 400, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cameraX += 1;
        cameraY += 1;
        repaint();
    }
}
