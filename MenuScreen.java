package FINALSS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuScreen extends JPanel implements KeyListener {

    JFrame frame;

    String[] options = {"PLAY", "CREDITS", "EXIT"};
    int selected = 0;

    public MenuScreen(JFrame frame) {

        this.frame = frame;

        setFocusable(true);
        addKeyListener(this);

        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLACK);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("BRAHMMY BLITZ", 150, 120);

        for (int i = 0; i < options.length; i++) {

            if (i == selected) g.setColor(Color.YELLOW);
            else g.setColor(Color.WHITE);

            g.setFont(new Font("Arial", Font.PLAIN, 28));
            g.drawString(options[i], 250, 250 + (i * 60));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP)
            selected--;

        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            selected++;

        if (selected < 0) selected = 0;
        if (selected > 2) selected = 2;

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (selected == 0) {
                frame.setContentPane(new GamePanel(frame));
            }

            if (selected == 1) {
                frame.setContentPane(new CreditsScreen(frame));
            }

            if (selected == 2) {
                System.exit(0);
            }

            frame.revalidate();
            frame.repaint();
        }

        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}