package FINALSS;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy {

    int x, y;
    int speed;
    String type;

    Image img;

    Random rand = new Random();

    public Enemy(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;

        if (type.equals("FAST")) {
            img = loadImage("/FINALSS/alien.png");
            speed = 4;
        }
        else if (type.equals("SLOW")) {
            img = loadImage("/FINALSS/alien-cyan.png");
            speed = 1;
        }
        else if (type.equals("ZIGZAG")) {
            img = loadImage("/FINALSS/alien-magenta.png");
            speed = 2;
        }
        else {
            img = loadImage("/FINALSS/alien-yellow..png");
            speed = 2;
        }
    }

    private Image loadImage(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("IMAGE NOT FOUND: " + path);
            return null;
        }
        return new ImageIcon(url).getImage();
    }

    public void move() {
        y += speed;

        if (type.equals("ZIGZAG")) {
            x += rand.nextInt(7) - 3;
        }
    }
}