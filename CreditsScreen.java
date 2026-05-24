package FINALSS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreditsScreen extends JPanel implements KeyListener {

    JFrame frame;

    public CreditsScreen(JFrame frame) {

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

        // TITLE
        g.setFont(new Font("Arial", Font.BOLD, 40));

        String title = "CREDITS";
        FontMetrics fm = g.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;

        g.drawString(title, titleX, 120);

        // TEXT
        g.setFont(new Font("Arial", Font.PLAIN, 24));

        g.drawString("Developer: Angelica Sumague & Kiel Delgado", 120, 220);
        g.drawString("Graphics: Dwight Macatangay", 120, 270);
        g.drawString("Extra: Adonis Hernandez", 120, 320);

        // FOOTER
        g.setFont(new Font("Arial", Font.PLAIN, 18));

        String back = "Press ESC to return";
        FontMetrics fm2 = g.getFontMetrics();

        int backX = (getWidth() - fm2.stringWidth(back)) / 2;

        g.drawString(back, backX, 420);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            App.showMenu();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}