package FINALSS;

public class Player {

    public int x, y;
    int speed = 10;

    // ship size (important for boundary)
    int width = 60;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        if (x > 0) x -= speed;
    }

    public void moveRight() {
        if (x < 900 - width) x += speed;
    }
}