import java.awt.*;
import java.awt.geom.*;
public class Paddle extends SpriteBase{
    //attributes for the paddle
    private static int STARTX=425;
    private static int STARTY=500;
    private static int pWidth=150;
    private static int pHeight=50;
    private GamePanel panel;

    private static double moveSpeed=0;

    //constructor, creates paddle
    public Paddle(Color color, GamePanel p){
        super(new Rectangle2D.Double(STARTX,STARTY,pWidth,pHeight),color);
        this.panel=p;
    }

    public void pMove(){
        if((getX()+moveSpeed>=0) && (getX()+getWidth()+moveSpeed<=panel.getWidth()))
        super.move(moveSpeed,0);
    }


    public void setMoveSpeed(double speed){
        moveSpeed=speed;
    }
    
}
