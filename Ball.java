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

    // Methods for direction changes
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

    // Increases the speed of the ball
    public void speedUp() {
        if (ballSpeed + 1 <= speedCap) {
            ballSpeed += 1;
            xDir = xDir / (Math.abs(xDir)) * ballSpeed;
            yDir = yDir / (Math.abs(yDir)) * ballSpeed;
        }
    }

    // Returns the speed to its inital value
    public void speedReset() {
        ballSpeed = 3;
        xDir = ballSpeed;
        yDir = -ballSpeed;
    }

    // Counter for every 500 game cycles
    private int count = 0;

    public void speedCounter() {
        count++;
        if (count > 500) {
            // Every 500 cycles, changes ball speed
            speedRandomizeX();
            speedRandomizeY();
            count = 0;
        }
    }

    // Randomly increases/decreases the ball speed slightly
    public void speedRandomizeX() {
        double rand = Math.random() + 1;
        if (rand < 1.5 && Math.abs(xDir) > ballSpeed) {
            if (xDir > 0) {
                xDir -= 0.5;
            }
            if (xDir < 0) {
                xDir += 0.5;
            }
            System.out.println("Random slow");
        } else if (rand >= 1.5 && Math.abs(xDir) < ballSpeed + 2) {
            if (xDir > 0) {
                xDir += 0.5;
            }
            if (xDir < 0) {
                xDir -= 0.5;
            }
            System.out.println("Random fast");
        }
    }

    public void speedRandomizeY() {
        double rand = Math.random() + 1;
        if (rand < 1.5 && Math.abs(yDir) > ballSpeed) {
            if (yDir > 0) {
                yDir -= 0.5;
            }
            if (yDir < 0) {
                yDir += 0.5;
            }
            System.out.println("Random slow");
        } else if (rand >= 1.5 && Math.abs(yDir) < ballSpeed + 2) {
            if (yDir > 0) {
                yDir += 0.5;
            }
            if (yDir < 0) {
                yDir -= 0.5;
            }
            System.out.println("Random fast");
        }
    }
}
