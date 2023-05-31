import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {

    private Ball ball;
    private Paddle paddle;
    private Bricks[] bricks; // an array for creating bricks
    private JFrame f;
    Menu m = new Menu(f);
    Background bg;
    Heart heart;
    Color menuScreen=new Color(102,178,225);
    Color menuButton=new Color(51,0,102);
    Color menuText=new Color(225,225,255);

    private static int ballSTARTX = 200;
    private static int ballSTARTY = 400;
    private int row = 12;
    private int col = 8;
    private int pSPEED = 10;

    private Timer timer;
    private int delay = 10;
    private boolean GameNew = true;
    private boolean GameRunning = false;
    private boolean GameOver = false;
    private boolean GamePaused = false;
    private int score;
    private int round;
    private int maxLives=5;
    private int lives = maxLives;

    public GamePanel(JFrame f) {
        this.f = f;
        Reset();
        addKeyListener(new KeyEvents());
        setFocusable(true);

        timer.start();
    }

    private void Reset() {
        ball = new Ball(getRandomColor(), this, ballSTARTX, ballSTARTY);
        ball.speedReset();
        paddle = new Paddle(getRandomColor(), this);
        bricks = new Bricks[row * col];
        lives = maxLives;
        heart = new Heart(new ImageIcon("Images/heart.png").getImage());
        bg = new Background(new ImageIcon("Images/bg.png").getImage());

        createBricks();
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(delay, new TimeEvents());
        round = 1;
        score = 0;
    }

    public void createBricks() {
        int b = 0;
        for (int i = 1; i <= col; i++) {
            for (int j = 1; j <= row; j++) {
                bricks[b] = new Bricks((j - 1) * 75 + 50, (i - 1) * 35 + 70, getRandomColor(), this);
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

    public void checkCollisions() {
        Rectangle ba = ball.getBounds();
        Bricks brick;
        Rectangle br;
        Rectangle pa = paddle.getBounds();
        // bouncing ball off paddle - angles? randomization? - a little boring the way
        // it is
        if (ba.intersects(pa)) {
            ball.Up();
            if (ball.getCenterX() <= paddle.getX() + paddle.getWidth() / 2) {
                ball.Left();
            } else if (ball.getCenterX() > paddle.getX() + paddle.getWidth() / 2) {
                ball.Right();
            }
        }
        for (int i = 0; i < bricks.length; i++) {
            brick = bricks[i];
            br = brick.getBounds();
            if (!bricks[i].checkStatus() && ba.intersects(br)) {
                bounceBallBrick(ball, bricks[i]);
                BrickDestroyed(i);
            }
        }
        if (belowPaddle()) {
            loseLife();
            ball.Up();
            isGameOver();
        }
    }

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

    public void BrickDestroyed(int i) {
        bricks[i].Destroyed();
        score += 10;
        NewRound();
    }

    public boolean belowPaddle() {
        if (ball.getCenterY() > paddle.getCenterY()) {
            return true;
        } else {
            return false;
        }
    }

    public void isGameOver() {
        if (lives < 1) {
            EndGame();
        }
    }

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

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    public void gameCycle() {
        ball.bMove();
        paddle.pMove();

        checkCollisions();
        repaint();
    }

    public void NewGame() {
        if (GameNew || GameOver || GamePaused) {
            Reset();
            GameRunning = true;
            GamePaused=false;
            GameNew=false;
            GameOver=false;
            timer.restart();
            repaint();
        }
    }

    public void ResumeGame() {
        if (GamePaused) {
            GameRunning = true;
            GamePaused=false;
            GameNew=false;
            GameOver=false;
            timer.restart();
            repaint();
        }
    }

    public void PauseGame() {
        GameRunning = false;
        GamePaused = true;
        if (timer != null) {
            timer.stop();
        }
        repaint();
    }

    public void EndGame() {
        GameRunning = false;
        GamePaused = false;
        GameNew = false;
        GameOver = true;

        if(timer!=null){
            timer.stop();
        }
        repaint();
    }

    public void exit() {
        System.exit(0);
    }

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

    public void paintGame(Graphics2D g2) {
        ball.draw(g2);
        paddle.draw(g2);
        for (int i = 0; i < bricks.length; i++) {
            if (!bricks[i].checkStatus()) {
                bricks[i].draw(g2);
            }
        }
    }

    public void GameText(String message, int x, int y,Color color,int size, Graphics g2) {
        Font gFont = new Font("ComicSans", Font.BOLD + Font.ITALIC, size);
        g2.setFont(gFont);
        g2.setColor(color);
        g2.drawString(message, x, y);
    }

    public void inGameMessage(Graphics2D g2) {
        String roundString = "Round: " + String.valueOf(round);
        GameText(roundString, 50, 40,menuText,30, g2);
        String scoreString = "Score: " + String.valueOf(score);
        GameText(scoreString, 300, 40,menuText,30, g2);
        String livesString = "Lives: ";
        GameText(livesString, 550, 40, menuText,30,g2);
        for (int i = 0; i < lives; i++) {
            heart.heartImage(650 + i * 60, 10, g2);
        }
    }
    public void MenuScreen(Graphics2D g2) {
        g2.setColor(menuScreen);
        g2.fillRect(275, 25, 450, 525);
        String gNew="Press Enter To Start";
        String gPaused="Game Paused";
        String gOver="Game Over";
        String gScore="Score: "+score;
        if(GameNew){
            GameText(gNew, 283, 225, menuText,45, g2);
        }
        if(GamePaused){
            GameText(gPaused, 340, 100, menuText,50, g2);
            g2.setColor(menuButton);
            g2.fillRect(400, 150, 200,100);
            GameText("Resume Game", 405, 190, menuText,28, g2);
            GameText("Press Space",430, 230, menuText, 24,g2);
        }
        if(GameOver){
            GameText(gOver, 370, 205, menuText,50, g2);
            GameText(gScore,330,245,menuText,30,g2);
        }
        g2.setColor(menuButton);
        
        g2.fillRect(400, 275, 200, 100);
        g2.fillRect(400, 400, 200, 100);
        GameText("New Game", 420, 315, menuText,30, g2);
        GameText("Press Enter",430, 355, menuText, 24,g2);
        GameText("Exit Game", 420, 440, menuText,30, g2);
        GameText("Press 0",450, 480, menuText, 24,g2);
    }

    class TimeEvents implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (GameRunning) {
                gameCycle();
            }
        }
    }

    class KeyEvents implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            paddle.setMoveSpeed(0);
            System.out.print("Stop");
        }

        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                paddle.setMoveSpeed(-pSPEED);
                System.out.print("Left");
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                paddle.setMoveSpeed(pSPEED);
                System.out.print("right");
            }

            if (e.getKeyCode() == KeyEvent.VK_ENTER && !GameRunning) {
                NewGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                ResumeGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                PauseGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_1) {
                ball.speedUp();
            }
            if (e.getKeyCode() == KeyEvent.VK_0){
                System.exit(0);
            }
        }
    }

}