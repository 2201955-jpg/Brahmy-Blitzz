package FINALSS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    JFrame frame;

    Player player;
    ArrayList<Enemy> enemies;
    ArrayList<Bullet> bullets;
    ArrayList<Bullet> enemyBullets;

    javax.swing.Timer timer;

    int score = 0;
    int wave = 1;
    boolean gameOver = false;

    boolean leftPressed = false;
    boolean rightPressed = false;

    Random rand = new Random();

    Image bgImg;
    Image shipImg;

    public GamePanel(JFrame frame) {

        this.frame = frame;

        setPreferredSize(new Dimension(750, 750));
        setSize(750, 750);
        setLayout(null);
        setFocusable(true);

        addKeyListener(this);

        bgImg = loadImage("/FINALSS/space.png");
        shipImg = loadImage("/FINALSS/ship.png");

        startGame();

        timer = new javax.swing.Timer(16, this);
        timer.start();

        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    // 🔁 RESET GAME FUNCTION
    public void startGame() {

        player = new Player(370, 650);

        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();

        score = 0;
        wave = 1;
        gameOver = false;

        spawnWave();
    }

    private Image loadImage(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("IMAGE NOT FOUND: " + path);
            return null;
        }
        return new ImageIcon(url).getImage();
    }

    public void spawnWave() {

        enemies.clear();

        int count = 6 + wave;

        for (int i = 0; i < count; i++) {

            int r = rand.nextInt(4);
            String type = (r == 0) ? "FAST" :
                          (r == 1) ? "SLOW" :
                          (r == 2) ? "ZIGZAG" : "NORMAL";

            enemies.add(new Enemy(rand.nextInt(710), rand.nextInt(200), type));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImg != null)
            g.drawImage(bgImg, 0, 0, 750, 750, null);

        if (shipImg != null)
            g.drawImage(shipImg, player.x, player.y, 60, 60, null);

        // ENEMIES
        for (Enemy e : enemies)
            if (e.img != null)
                g.drawImage(e.img, e.x, e.y, 40, 40, null);

        // PLAYER BULLETS
        g.setColor(Color.YELLOW);
        for (Bullet b : bullets)
            g.fillRect(b.x, b.y, 5, 10);

        // ENEMY BULLETS
        g.setColor(Color.RED);
        for (Bullet b : enemyBullets)
            g.fillRect(b.x, b.y, 5, 10);

        // UI
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 20, 20);
        g.drawString("Wave: " + wave, 20, 40);

        // GAME OVER SCREEN
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 230, 300);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", 270, 350);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameOver) {

            // MOVE PLAYER
            if (leftPressed) player.x -= player.speed;
            if (rightPressed) player.x += player.speed;

            if (player.x < 0) player.x = 0;
            if (player.x > 690) player.x = 690;

            // MOVE ENEMIES
            for (Enemy enemy : enemies)
                enemy.move();

            // MOVE BULLETS
            for (Bullet b : bullets)
                b.y -= 10;

            for (Bullet b : enemyBullets)
                b.y += 6;

            // COLLISION PLAYER BULLETS
            Iterator<Bullet> it = bullets.iterator();

            while (it.hasNext()) {
                Bullet b = it.next();

                Rectangle br = new Rectangle(b.x, b.y, 5, 10);

                for (int i = 0; i < enemies.size(); i++) {
                    Enemy en = enemies.get(i);
                    Rectangle er = new Rectangle(en.x, en.y, 40, 40);

                    if (br.intersects(er)) {
                        enemies.remove(i);
                        it.remove();
                        score += 10;
                        break;
                    }
                }
            }

            // COLLISION ENEMY BULLETS
            Rectangle playerRect = new Rectangle(player.x, player.y, 60, 60);

            Iterator<Bullet> eit = enemyBullets.iterator();

            while (eit.hasNext()) {
                Bullet b = eit.next();

                Rectangle br = new Rectangle(b.x, b.y, 5, 10);

                if (br.intersects(playerRect)) {
                    gameOver = true;
                    break;
                }

                if (b.y > 750) eit.remove();
            }

            // COLLISION ENEMY TOUCH PLAYER (FIXED)
            Rectangle pRect = new Rectangle(player.x, player.y, 60, 60);

            for (Enemy enemy : enemies) {
                Rectangle eRect = new Rectangle(enemy.x, enemy.y, 40, 40);

                if (eRect.intersects(pRect)) {
                    gameOver = true;
                    break;
                }
            }

            bullets.removeIf(b -> b.y < 0);
            enemies.removeIf(e1 -> e1.y > 750);

            if (enemies.isEmpty()) {
                wave++;
                spawnWave();
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (gameOver && key == KeyEvent.VK_R) {
            startGame(); // 🔁 restart
            return;
        }

        if (key == KeyEvent.VK_LEFT) leftPressed = true;
        if (key == KeyEvent.VK_RIGHT) rightPressed = true;

        if (key == KeyEvent.VK_SPACE) {
            if (!gameOver)
                bullets.add(new Bullet(player.x + 25, player.y));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) leftPressed = false;
        if (key == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override public void keyTyped(KeyEvent e) {}
}