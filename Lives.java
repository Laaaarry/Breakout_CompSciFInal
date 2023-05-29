import java.awt.*;
import java.awt.geom.*;


public class Lives extends SpriteBase{
    private static int size=20;
    int[]diamondX=new int[4];
    int[]diamondY=new int[4]
    
    GamePanel panel;

    int xPos;
    int yPos;

    public Lives(int xPos, int yPos, GamePanel p){
        super(new Ellipse2D.Double(xPos,yPos,size,size),Color.RED);
        this.xPos=xPos;
        this.yPos=yPos;
        this.panel=p;
        
        
    }
}
