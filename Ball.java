import java.awt.*;
import java.awt.geom.*;

public class Ball extends SpriteBase {
    // ball attributes
    private static int BALLSIZE = 20;
    private static double ballSpeed = 3;
    private static double speedCap = 20;
    private double xDir = ballSpeed;
    private double yDir = -ballSpeed;
    private GamePanel panel;

    // constructor for the ball
    public Ball(Color color, GamePanel p, int xPos, int yPos) {
        super(new Ellipse2D.Double(xPos, yPos, BALLSIZE, BALLSIZE), color);
        this.panel = p;
    }

    public void bMove() {
        // if the ball will move outside the panel, the ball will go the other way
        if (getX() + xDir < 0) {
            Right();
        }
        if (getX() + xDir + BALLSIZE > panel.getWidth()) {
            Left();
        }
        if (getY() + yDir < 0) {
            Down();
        }
        if (getY() + yDir + BALLSIZE > panel.getHeight()) {
            Up();
        }
        super.move(xDir, yDir);
        speedCounter();
    }

    public void Left() {
        xDir = -Math.abs(xDir);
    }

    public void Right() {
        xDir = Math.abs(xDir);
    }

    public void Up() {
        yDir = -Math.abs(yDir);
    }

    public void Down() {
        yDir = Math.abs(yDir);
    }

    // checks the position of the ball relative to a brick.
    // since collisions are detected are after the intersection happens,
    // these methods will use the ball position of the previous cycle to check for
    // relative location
    public boolean BallAbove(Ball ba, Bricks br) {
        boolean isAbove = ba.getY() + getHeight() - yDir <= br.getY();
        return isAbove;
    }

    public boolean BallBelow(Ball ba, Bricks br) {
        boolean isBelow = ba.getY() - yDir >= br.getY() + br.getHeight();
        return isBelow;
    }

    public boolean BallLeft(Ball ba, Bricks br) {
        boolean isLeft = ba.getCenterX() - xDir < br.getX();
        return isLeft;
    }

    public boolean BallRight(Ball ba, Bricks br) {
        boolean isRight = ba.getCenterX() - xDir > br.getX() + br.getWidth();
        return isRight;
    }

    public void speedUp() {
        if (ballSpeed + 1 <= speedCap) {
            ballSpeed += 1;
            xDir = xDir / (Math.abs(xDir)) * ballSpeed;
            yDir = yDir / (Math.abs(yDir)) * ballSpeed;
        }
    }

    public void speedReset() {
        ballSpeed = 3;
        xDir = ballSpeed;
        yDir = -ballSpeed;
    }

    private int count = 0;

    public void speedCounter() {
        count++;
        if (count > 500) {
            speedRandomizeX();
            speedRandomizeY();
            System.out.println("Randomized");
            count = 0;
        }
    }

    public void speedRandomizeX() {
        double rand = Math.random() + 1;
        if (rand < 1.5 && xDir > 3) {
            xDir -= 0.5;
        } else {
            xDir += 0.4;
        }
    }

    public void speedRandomizeY() {
        double rand = Math.random() + 1;
        if (rand < 1.5 && yDir > 3) {
            yDir -= 0.5;
        } else {
            yDir += 0.4;
        }
    }
}
