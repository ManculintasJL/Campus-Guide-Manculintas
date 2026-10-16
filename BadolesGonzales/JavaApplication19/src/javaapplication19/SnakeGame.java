package javaapplication19;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * Snake Game
 * A polished, single-file Java Swing implementation.
 *
 * Controls:
 *   Arrow Keys / WASD - Move
 *   P                 - Pause / Resume
 *   R                 - Restart (after Game Over)
 *   ESC               - Quit
 *
 * Compile: javac SnakeGame.java
 * Run:     java SnakeGame
 */
public class SnakeGame extends JPanel implements ActionListener {

    // ---- Grid / window settings ----
    private static final int TILE_SIZE = 25;
    private static final int GRID_WIDTH = 28;
    private static final int GRID_HEIGHT = 22;
    private static final int WINDOW_WIDTH = TILE_SIZE * GRID_WIDTH;
    private static final int WINDOW_HEIGHT = TILE_SIZE * GRID_HEIGHT + 40; // +40 for header bar

    // ---- Game speed ----
    private static final int BASE_DELAY_MS = 140;
    private static final int MIN_DELAY_MS = 60;
    private static final int SPEEDUP_EVERY_N_FOODS = 5;

    // ---- Colors ----
    private static final Color COLOR_BG = new Color(18, 18, 24);
    private static final Color COLOR_GRID = new Color(28, 28, 36);
    private static final Color COLOR_SNAKE_HEAD = new Color(102, 217, 137);
    private static final Color COLOR_SNAKE_BODY = new Color(60, 170, 100);
    private static final Color COLOR_FOOD = new Color(235, 87, 87);
    private static final Color COLOR_TEXT = new Color(240, 240, 245);
    private static final Color COLOR_HEADER_BG = new Color(12, 12, 16);
    private static final Color COLOR_ACCENT = new Color(240, 200, 90);

    private final LinkedList<Point> snake = new LinkedList<>();
    private Point food;
    private Direction direction = Direction.RIGHT;
    private Direction pendingDirection = Direction.RIGHT;

    private Timer timer;
    private int currentDelay = BASE_DELAY_MS;
    private int score = 0;
    private int highScore = 0;
    private int foodEatenCount = 0;

    private boolean running = false;
    private boolean paused = false;
    private boolean gameOver = false;

    private final Random random = new Random();

    private enum Direction {
        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

        final int dx, dy;
        Direction(int dx, int dy) { this.dx = dx; this.dy = dy; }

        boolean isOpposite(Direction other) {
            return this.dx == -other.dx && this.dy == -other.dy;
        }
    }

    public SnakeGame() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(COLOR_BG);
        setFocusable(true);
        setDoubleBuffered(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });

        initGame();

        timer = new Timer(currentDelay, this);
        timer.start();
    }

    private void initGame() {
        snake.clear();
        int startX = GRID_WIDTH / 4;
        int startY = GRID_HEIGHT / 2;
        snake.add(new Point(startX, startY));
        snake.add(new Point(startX - 1, startY));
        snake.add(new Point(startX - 2, startY));

        direction = Direction.RIGHT;
        pendingDirection = Direction.RIGHT;
        score = 0;
        foodEatenCount = 0;
        currentDelay = BASE_DELAY_MS;
        running = true;
        paused = false;
        gameOver = false;

        spawnFood();

        if (timer != null) {
            timer.setDelay(currentDelay);
        }
    }

    private void spawnFood() {
        Point candidate;
        do {
            int x = random.nextInt(GRID_WIDTH);
            int y = random.nextInt(GRID_HEIGHT);
            candidate = new Point(x, y);
        } while (snake.contains(candidate));
        food = candidate;
    }

    private void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                setPendingDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                setPendingDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                setPendingDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                setPendingDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_P:
                if (running && !gameOver) {
                    paused = !paused;
                    repaint();
                }
                break;
            case KeyEvent.VK_R:
                if (gameOver) {
                    initGame();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    private void setPendingDirection(Direction newDir) {
        if (!newDir.isOpposite(direction)) {
            pendingDirection = newDir;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !paused && !gameOver) {
            moveSnake();
            checkCollisions();
            repaint();
        }
    }

    private void moveSnake() {
        direction = pendingDirection;
        Point head = snake.getFirst();
        Point newHead = new Point(head.x + direction.dx, head.y + direction.dy);
        snake.addFirst(newHead);

        if (newHead.equals(food)) {
            score += 10;
            foodEatenCount++;
            if (foodEatenCount % SPEEDUP_EVERY_N_FOODS == 0 && currentDelay > MIN_DELAY_MS) {
                currentDelay = Math.max(MIN_DELAY_MS, currentDelay - 10);
                timer.setDelay(currentDelay);
            }
            spawnFood();
        } else {
            snake.removeLast();
        }
    }

    private void checkCollisions() {
        Point head = snake.getFirst();

        // Wall collision
        if (head.x < 0 || head.x >= GRID_WIDTH || head.y < 0 || head.y >= GRID_HEIGHT) {
            endGame();
            return;
        }

        // Self collision
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                endGame();
                return;
            }
        }
    }

    private void endGame() {
        gameOver = true;
        running = false;
        highScore = Math.max(highScore, score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawHeader(g2);
        drawGrid(g2);
        drawFood(g2);
        drawSnake(g2);

        if (paused) {
            drawOverlayMessage(g2, "PAUSED", "Press P to resume");
        } else if (gameOver) {
            drawOverlayMessage(g2, "GAME OVER", "Press R to restart  |  Score: " + score);
        }
    }

    private void drawHeader(Graphics2D g2) {
        g2.setColor(COLOR_HEADER_BG);
        g2.fillRect(0, 0, WINDOW_WIDTH, 40);

        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        g2.setColor(COLOR_TEXT);
        g2.drawString("Score: " + score, 12, 26);

        g2.setColor(COLOR_ACCENT);
        String highText = "Best: " + highScore;
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(highText, WINDOW_WIDTH - fm.stringWidth(highText) - 12, 26);
    }

    private void drawGrid(Graphics2D g2) {
        g2.setColor(COLOR_GRID);
        int top = 40;
        for (int x = 0; x <= GRID_WIDTH; x++) {
            g2.drawLine(x * TILE_SIZE, top, x * TILE_SIZE, top + GRID_HEIGHT * TILE_SIZE);
        }
        for (int y = 0; y <= GRID_HEIGHT; y++) {
            g2.drawLine(0, top + y * TILE_SIZE, WINDOW_WIDTH, top + y * TILE_SIZE);
        }
    }

    private void drawFood(Graphics2D g2) {
        int top = 40;
        g2.setColor(COLOR_FOOD);
        int pad = 3;
        g2.fillOval(food.x * TILE_SIZE + pad, top + food.y * TILE_SIZE + pad,
                TILE_SIZE - pad * 2, TILE_SIZE - pad * 2);
    }

    private void drawSnake(Graphics2D g2) {
        int top = 40;
        int i = 0;
        for (Point p : snake) {
            g2.setColor(i == 0 ? COLOR_SNAKE_HEAD : COLOR_SNAKE_BODY);
            int pad = i == 0 ? 1 : 2;
            g2.fillRoundRect(p.x * TILE_SIZE + pad, top + p.y * TILE_SIZE + pad,
                    TILE_SIZE - pad * 2, TILE_SIZE - pad * 2, 8, 8);
            i++;
        }
    }

    private void drawOverlayMessage(Graphics2D g2, String title, String subtitle) {
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        g2.setFont(new Font("SansSerif", Font.BOLD, 36));
        g2.setColor(COLOR_TEXT);
        FontMetrics fm = g2.getFontMetrics();
        int titleX = (WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        g2.drawString(title, titleX, WINDOW_HEIGHT / 2 - 10);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
        FontMetrics fm2 = g2.getFontMetrics();
        int subX = (WINDOW_WIDTH - fm2.stringWidth(subtitle)) / 2;
        g2.drawString(subtitle, subX, WINDOW_HEIGHT / 2 + 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake");
            SnakeGame game = new SnakeGame();
            frame.add(game);
            frame.pack();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            game.requestFocusInWindow();
        });
    }
}