import java.awt.*;
import java.awt.geom.*;

public class SpriteBase {
    // the parent class for game sprites (Ball, Paddle, Bricks)
    // We found this style of creating a parent class for all sprites from an
    // example online
    // We thought it was a very effective way of combining common methods that all
    // sprites used
    // link:
    // http://what-when-how.com/Tutorial/topic-513jic/Learning-Java-Through-Games-263.html
    private Color color; // determines the color of the sprite
    private RectangularShape shape; // the actual shape of the sprite

    // constructor for class
    public SpriteBase(RectangularShape sprite, Color color) {
        this.shape = sprite;
        this.color = color;
    }

    // returns the boundaries of the sprite. useful for checking for intersections
    public Rectangle getBounds() {
        return shape.getBounds();
    }

    // gets attributes of sprite using methods of the RectangularShape class
    // gets X-coordinate of the top right corner of the sprite
    public double getX() {
        return shape.getX();
    }

    // gets the Y-coordinate of the top right corner of the sprite
    public double getY() {
        return shape.getY();
    }

    // gets width (x) of object
    public double getWidth() {
        return shape.getWidth();
    }

    // gets height (y) of object
    public double getHeight() {
        return shape.getHeight();
    }

    // gets the X-coordinate of the center of the sprite
    public double getCenterX() {
        return shape.getCenterX();
    }

    // gets the Y-coordinate of the center of the sprite
    public double getCenterY() {
        return shape.getCenterY();
    }

    // draws the sprite
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(shape);
    }

    // moves the sprite by altering the sprite's coordinates while keeping its
    // dimensions
    public void move(double xDir, double yDir) {
        shape.setFrame(getX() + xDir, getY() + yDir, getWidth(), getHeight());
    }

}
