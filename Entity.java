package FINALSS;

public abstract class Entity {
    protected int x, y, width, height;

    public Entity(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public abstract void move();
}