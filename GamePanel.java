import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
    // Initialize Object Variables
    private Ball ball;
    private Paddle paddle;
    private Bricks[] bricks; // an array for creating bricks
    private Background bg;
    private Heart heart;

    // Setting color variables
    private Color menuScreen = new Color(102, 178, 225);
    private Color menuButton = new Color(51, 0, 102);
    private Color menuText = new Color(225, 225, 255);

    // Ball starting positions
    private static int ballSTARTX = 200;
    private static int ballSTARTY = 400;
    // The varibles for the number of bricks per row and column
    private int row = 11;
    private int col = 6;
    // Speed of the paddle
    private int pSPEED = 15;
    // variables to track the score, round, and lives
    private int score;
    private int round;
    private int maxLives = 5;
    private int lives = maxLives;

    // Animation-related variables
    private Timer timer;
    private int delay = 10;
    // Game-state boolean variables
    private boolean GameNew = true;
    private boolean GameRunning = false;
    private boolean GameOver = false;
    private boolean GamePaused = false;
    private boolean InMenu = true;

    // Constructor for the class
    public GamePanel() {
        // loading the images
        heart = new Heart(new ImageIcon("Images/heart.png").getImage());
        bg = new Background(new ImageIcon("Images/bg.png").getImage());
        Reset();
        addKeyListener(new KeyEvents());
        setFocusable(true);
    }

    // Resets (creates) the game interface
    private void Reset() {
        // Resetting objects and values
        ball = new Ball(getRandomColor(), this, ballSTARTX, ballSTARTY);
        ball.speedReset();
        paddle = new Paddle(getRandomColor(), this);
        bricks = new Bricks[row * col];
        createBricks();
        if (timer != null) {
            timer.stop();
        }
        lives = maxLives;
        round = 1;
        score = 0;

        timer = new Timer(delay, new TimeEvents());
    }

    // Creates instances of the brick for each brick in the game
    public void createBricks() {
        int b = 0;
        for (int i = 1; i <= col; i++) {
            for (int j = 1; j <= row; j++) {
                bricks[b] = new Bricks((j - 1) * 88 + 16, (i - 1) * 40 + 70, getRandomColor(), this);
                b++;
            }
        }
    }

    // all sprites will be in a random color for fun
    private Color getRandomColor() {
        Color color = new Color
        // the color will always be lighter since we want a dark background
        ((int) (Math.random() * 120 + 135), (int) (Math.random() * 120 + 135), (int) (Math.random() * 120 + 135));
        return color;
    }

    // Checks for collisions between objects
    public void checkCollisions() {
        // Getting rectangles for each sprite to help detect collisions
        Rectangle ba = ball.getBounds();
        Bricks brick;
        Rectangle br;
        Rectangle pa = paddle.getBounds();
        // Bounces ball back up if it hits the paddle
        if (ba.intersects(pa)) {
            ball.Up();
            if (ball.getCenterX() <= paddle.getX() + paddle.getWidth() / 2) {
                ball.Left();
            } else if (ball.getCenterX() > paddle.getX() + paddle.getWidth() / 2) {
                ball.Right();
            }
        }
        // Checks each non-destroyed brick for a collision
        for (int i = 0; i < bricks.length; i++) {
            brick = bricks[i];
            br = brick.getBounds();
            if (!bricks[i].checkStatus() && ba.intersects(br)) {
                bounceBallBrick(ball, bricks[i]);
                BrickDestroyed(i);
            }
        }
        // Checks if the ball is below the paddle; if it is, lose a life
        if (belowPaddle()) {
            loseLife();
            ball.Up();
            isGameOver();
        }
    }

    // Bounces the ball off the brick based on the ball's relative location to the
    // brick
    public void bounceBallBrick(Ball ba, Bricks br) {
        if (ball.BallLeft(ba, br)) {
            ball.Left();
        }
        if (ball.BallRight(ba, br)) {
            ball.Right();
        }
        if (ball.BallAbove(ba, br)) {
            ball.Up();
        }
        if (ball.BallBelow(ba, br)) {
            ball.Down();
        }
    }

    // Destroys the brick and increases score
    public void BrickDestroyed(int i) {
        bricks[i].Destroyed();
        score += 10;
        NewRound();
    }

    // Returns true if the ball is below the paddle
    public boolean belowPaddle() {
        if (ball.getCenterY() > paddle.getCenterY()) {
            return true;
        } else {
            return false;
        }
    }

    // Removes a life
    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    // Ends the game if there are no lives remaining
    public void isGameOver() {
        if (lives < 1) {
            EndGame();
        }
    }

    // Creates a new set of bricks once all previous bricks are gone
    public void NewRound() {
        int flag = 0;
        for (int i = 0; i < bricks.length; i++) {
            if (!bricks[i].checkStatus()) {
                flag++;
            }
        }
        if (flag == 0) {
            bricks = new Bricks[row * col];
            createBricks();
            round++;
            ball.speedUp();
            score += 50;
        }
    }

    // Primary in-game actions - moves ball and paddle, checks for collisions and
    // repaints the screen
    public void gameCycle() {
        ball.bMove();
        paddle.pMove();

        checkCollisions();
        repaint();
    }

    // Starts a new game by resetting the game and game-state variables
    public void NewGame() {
        if (GameNew || GameOver || GamePaused) {
            Reset();
            GameRunning = true;
            GamePaused = false;
            GameNew = false;
            GameOver = false;
            InMenu = false;
            timer.restart();
            repaint();
        }
    }

    // Resumes a paused game
    public void ResumeGame() {
        if (GamePaused) {
            GameRunning = true;
            GamePaused = false;
            GameNew = false;
            GameOver = false;
            InMenu = false;
            timer.restart();
            repaint();
        }
    }

    // Pauses the game
    public void PauseGame() {
        GameRunning = false;
        GamePaused = true;
        InMenu = true;
        if (timer != null) {
            timer.stop();
        }
        repaint();
    }

    // Ends the game
    public void EndGame() {
        GameRunning = false;
        GamePaused = false;
        GameNew = false;
        GameOver = true;
        InMenu = true;

        if (timer != null) {
            timer.stop();
        }
        repaint();
    }

    // Closes the game
    public void exit() {
        System.exit(0);
    }

    // The main paint method
    // Depending on the game state, it will either display the game statistics or a
    // menu screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        bg.paintBg(g2);
        paintGame(g2);
        if (GameRunning) {
            inGameMessage(g2);
        }
        if (!GameRunning) {
            MenuScreen(g2);
        }
    }

    // Paints the game objects
    public void paintGame(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ball.draw(g2);
        paddle.draw(g2);
        for (int i = 0; i < bricks.length; i++) {
            if (!bricks[i].checkStatus()) {
                bricks[i].draw(g2);
            }
        }
    }

    // A method used for displaying text on screen
    public void GameText(String message, int x, int y, Color color, int size, Graphics g2) {
        Font gFont = new Font("ComicSans", Font.BOLD + Font.ITALIC, size);
        g2.setFont(gFont);
        g2.setColor(color);
        g2.drawString(message, x, y);
    }

    // Displays round, score and lives
    // Only shown when in-game
    public void inGameMessage(Graphics2D g2) {
        String roundString = "Round: " + String.valueOf(round);
        GameText(roundString, 50, 40, menuText, 30, g2);
        String scoreString = "Score: " + String.valueOf(score);
        GameText(scoreString, 300, 40, menuText, 30, g2);
        String livesString = "Lives: ";
        GameText(livesString, 550, 40, menuText, 30, g2);
        for (int i = 0; i < lives; i++) {
            heart.heartImage(650 + i * 60, 10, g2);
        }
    }

    // Displays the menu
    // Only shown when the game is paused or over
    public void MenuScreen(Graphics2D g2) {
        g2.setColor(menuScreen);
        g2.fillRect(275, 25, 450, 525);
        String gNew = "Press Enter To Start";
        String gPaused = "Game Paused";
        String gOver = "Game Over";
        String gScore = "Score: " + score;
        // Depending on the game state, the menu will be different
        if (GameNew) {
            GameText(gNew, 283, 225, menuText, 45, g2);
        }
        if (GamePaused) {
            GameText(gPaused, 340, 100, menuText, 50, g2);
            g2.setColor(menuButton);
            g2.fillRect(400, 150, 200, 100);
            GameText("Resume Game", 405, 190, menuText, 28, g2);
            GameText("Press Space", 430, 230, menuText, 24, g2);
        }
        if (GameOver) {
            GameText(gOver, 365, 205, menuText, 50, g2);
            GameText(gScore, 330, 245, menuText, 30, g2);
        }
        g2.setColor(menuButton);
        g2.fillRect(400, 275, 200, 100);
        g2.fillRect(400, 400, 200, 100);
        GameText("New Game", 420, 315, menuText, 30, g2);
        GameText("Press Enter", 430, 355, menuText, 24, g2);
        GameText("Exit Game", 420, 440, menuText, 30, g2);
        GameText("Press 0", 450, 480, menuText, 24, g2);
        if (GameNew || GameOver) {
            GameText("BREAKOUT", 330, 120, menuButton, 60, g2);
        }
    }

    // Game loop based on the timer: receives event from timer with set delay
    class TimeEvents implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (GameRunning) {
                gameCycle();
            }
        }
    }

    // Detects events from the user (keyboard)
    class KeyEvents implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        // Stops paddle when keys are released
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                paddle.setMoveSpeed(0);
                // the print was added for troubleshooting, isn't necessary for the code
                System.out.print("Stop");
            }
        }

        public void keyPressed(KeyEvent e) {
            // Moves paddle left or right based on key pressed
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                paddle.setMoveSpeed(-pSPEED);
                System.out.print("Left");
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                paddle.setMoveSpeed(pSPEED);
                System.out.print("right");
            }

            // Keys to change game state (start, pause/resume, exit, etc.)
            if (e.getKeyCode() == KeyEvent.VK_ENTER && !GameRunning) {
                NewGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE && InMenu) {
                ResumeGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !InMenu) {
                PauseGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_0) {
                System.exit(0);
            }
            // Used for testing the game to speed things up, isn't necessary for the code
            if (e.getKeyCode() == KeyEvent.VK_1) {
                ball.speedUp();
            }
        }
    }
}