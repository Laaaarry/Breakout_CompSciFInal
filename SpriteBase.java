import java.awt.*;
import java.awt.geom.*;

public class SpriteBase {
    //the parent class for game sprites (Ball, Paddle, Bricks)
    private Color color; //determines the color of the sprite
    private RectangularShape shape; //the actual shape of the sprite

    //constructor for class
    public SpriteBase(RectangularShape sprite,Color color){
        this.shape=sprite;
        this.color=color;
    }

    //returns the boundaries of the sprite. useful for checking for intersections
    public Rectangle getBounds(){
        return shape.getBounds();
    }
    //gets X and Y coordinates of sprite
    public double getX(){
        return shape.getX(); //methods of the parent Rectangle2D class
    }
    public double getY(){
        return shape.getY();
    }
    public double getWidth(){
        return shape.getWidth();
    }
    public double getHeight(){
        return shape.getHeight();
    }
    public double getCenterX(){
        return shape.getCenterX();
    }
    public double getCenterY(){
        return shape.getCenterY();
    }

    //draws the sprite
    public void draw(Graphics2D g2){
        g2.setColor(color);
        g2.fill(shape);
    }
    //moves the sprite by altering the sprite's coordinates while keeping its dimensions
    public void move(double xDir, double yDir){
        shape.setFrame(getX()+xDir,getY()+yDir,getWidth(),getHeight());
    }

}
