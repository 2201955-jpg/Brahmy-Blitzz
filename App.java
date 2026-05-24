package FINALSS;

import javax.swing.*;

public class App {

    public static JFrame frame;

    public static void main(String[] args) {

        frame = new JFrame("Brahmmy Blitz");

        frame.setSize(750, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.setContentPane(new MenuScreen(frame));

        frame.setVisible(true);
    }

    public static void showMenu() {
        frame.setContentPane(new MenuScreen(frame));
        frame.revalidate();
        frame.repaint();
    }
}