package FINALSS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LeaderboardScreen extends JPanel implements KeyListener {

    JFrame frame;
    ArrayList<String> scores;

    public LeaderboardScreen(JFrame frame) {

        this.frame = frame;

        setFocusable(true);
        addKeyListener(this);

        loadScores();

        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    private void loadScores() {
        scores = LeaderboardDB.getScores();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLACK);

        scores = LeaderboardDB.getScores(); // always refresh

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("LEADERBOARD", 180, 100);

        g.setFont(new Font("Arial", Font.PLAIN, 25));

        int y = 180;

        if (scores.isEmpty()) {
            g.drawString("No scores yet", 250, y);
        } else {
            for (int i = 0; i < scores.size(); i++) {
                g.drawString((i + 1) + ". " + scores.get(i), 200, y);
                y += 40;
            }
        }

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Press B to go back", 250, 650);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_B) {
            frame.setContentPane(new MenuScreen(frame));
            frame.revalidate();
            frame.repaint();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}