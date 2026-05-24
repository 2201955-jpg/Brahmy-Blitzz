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

    // UI
    JTextField nameField;
    JButton enterBtn;
    JButton backBtn;
    JButton restartBtn;

    public GamePanel(JFrame frame) {

        this.frame = frame;

        setPreferredSize(new Dimension(750, 750));
        setSize(750, 750);
        setLayout(null);
        setFocusable(true);

        addKeyListener(this);

        bgImg = loadImage("/FINALSS/space.png");
        shipImg = loadImage("/FINALSS/ship.png");

        createUI();

        startGame();

        timer = new javax.swing.Timer(16, this);
        timer.start();

        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    // ================= UI =================
    private void createUI() {

        nameField = new JTextField();
        nameField.setBounds(250, 300, 200, 35);
        nameField.setVisible(false);
        add(nameField);

        enterBtn = new JButton("ENTER");
        enterBtn.setBounds(250, 340, 200, 35);
        enterBtn.setVisible(false);
        add(enterBtn);

        backBtn = new JButton("BACK");
        backBtn.setBounds(200, 390, 120, 40);
        backBtn.setVisible(false);
        add(backBtn);

        restartBtn = new JButton("RESTART");
        restartBtn.setBounds(350, 390, 120, 40);
        restartBtn.setVisible(false);
        add(restartBtn);

        // ENTER SAVE
        enterBtn.addActionListener(e -> {

            String name = nameField.getText().trim();

            if (!name.isEmpty()) {
                LeaderboardDB.saveScore(name, score);
                nameField.setEditable(false);
            }
        });

        // BACK
        backBtn.addActionListener(e -> {
            frame.setContentPane(new MenuScreen(frame));
            frame.revalidate();
            frame.repaint();
        });

        // RESTART
        restartBtn.addActionListener(e -> startGame());
    }

    // ================= GAME RESET =================
    public void startGame() {

        player = new Player(370, 650);

        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();

        score = 0;
        wave = 1;
        gameOver = false;

        nameField.setText("");
        nameField.setEditable(true);

        nameField.setVisible(false);
        enterBtn.setVisible(false);
        backBtn.setVisible(false);
        restartBtn.setVisible(false);

        spawnWave();
    }

    private Image loadImage(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) return null;
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

    // ================= DRAW =================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImg != null)
            g.drawImage(bgImg, 0, 0, 750, 750, null);

        if (shipImg != null)
            g.drawImage(shipImg, player.x, player.y, 60, 60, null);

        for (Enemy e : enemies)
            if (e.img != null)
                g.drawImage(e.img, e.x, e.y, 40, 40, null);

        g.setColor(Color.YELLOW);
        for (Bullet b : bullets)
            g.fillRect(b.x, b.y, 5, 10);

        g.setColor(Color.RED);
        for (Bullet b : enemyBullets)
            g.fillRect(b.x, b.y, 5, 10);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 20, 20);
        g.drawString("Wave: " + wave, 20, 40);

        if (gameOver) {

            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 220, 220);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Enter Name:", 250, 270);

            nameField.setVisible(true);
            enterBtn.setVisible(true);
            backBtn.setVisible(true);
            restartBtn.setVisible(true);
        }
    }

    // ================= GAME LOOP =================
    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameOver) {

            if (leftPressed) player.x -= player.speed;
            if (rightPressed) player.x += player.speed;

            if (player.x < 0) player.x = 0;
            if (player.x > 690) player.x = 690;

            for (Enemy enemy : enemies)
                enemy.move();

            // enemy shooting wave 5+
            if (wave >= 5 && rand.nextInt(20) == 0) {
                for (Enemy enemy : enemies) {
                    if (rand.nextInt(100) < 3) {
                        enemyBullets.add(new Bullet(enemy.x + 20, enemy.y + 20));
                    }
                }
            }

            for (Bullet b : bullets)
                b.y -= 10;

            for (Bullet b : enemyBullets)
                b.y += 6;

            // COLLISION (UNCHANGED LOGIC)
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

            Rectangle playerRect = new Rectangle(player.x, player.y, 60, 60);

            Iterator<Bullet> eit = enemyBullets.iterator();

            while (eit.hasNext()) {

                Bullet b = eit.next();

                if (new Rectangle(b.x, b.y, 5, 10).intersects(playerRect)) {
                    gameOver = true;
                    break;
                }

                if (b.y > 750) eit.remove();
            }

            for (Enemy enemy : enemies) {
                if (new Rectangle(enemy.x, enemy.y, 40, 40).intersects(playerRect)) {
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

    // ================= INPUT =================
    @Override
    public void keyPressed(KeyEvent e) {

        if (gameOver) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(player.x + 25, player.y));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override public void keyTyped(KeyEvent e) {}
}