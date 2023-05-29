import java.awt.*;
import java.awt.geom.*;

public class Ball extends SpriteBase{
    //ball attributes
    private static int BALLSIZE=30;
    private static double ballSpeed=1;
    private double xDir=-ballSpeed;
    private double yDir=-ballSpeed;
    private GamePanel panel;

    //constructor for the ball
    public Ball(Color color, GamePanel p, int xPos, int yPos){
        super(new Ellipse2D.Double(xPos, yPos, BALLSIZE, BALLSIZE),color);
        this.panel=p;
    }
    
    public void bMove(){
        //if the ball will move outside the panel, the ball will go the other way
        if(getX()+xDir<0){
            Right();
        }
        if(getX()+xDir+BALLSIZE>panel.getWidth()){
            Left();
        }
        if(getY()+yDir<0){
            Down();
        }
        if(getY()+yDir+BALLSIZE>panel.getHeight()){
            Up();
        }
        super.move(xDir,yDir);
    }
    public void Left(){
        xDir=-ballSpeed;
    }
    public void Right(){
        xDir=ballSpeed;
    }
    public void Up(){
        yDir=-ballSpeed;
    }
    public void Down(){
        yDir=ballSpeed;
    }

    //checks the position of the ball relative to a brick.
    //since collisions are detected are after the intersection happens,
    //these methods will use the ball position of the previous cycle to check for relative location
    public boolean BallAbove(Ball ba, Bricks br){
        boolean isAbove=ba.getY()+getHeight()-yDir<=br.getY();
        return isAbove;
    }
    public boolean BallBelow(Ball ba, Bricks br){
        boolean isBelow=ba.getY()-yDir>=br.getY()+br.getHeight();
        return isBelow;
    }
    public boolean BallLeft(Ball ba, Bricks br){
        boolean isLeft=ba.getX()+ba.getWidth()-xDir<br.getX();
        return isLeft;
    }
    public boolean BallRight(Ball ba, Bricks br){
        boolean isRight=ba.getX()-xDir>br.getX()+br.getWidth();
        return isRight;
    }

    public void speedUp(){
        ballSpeed+=0.5;
    }
}
