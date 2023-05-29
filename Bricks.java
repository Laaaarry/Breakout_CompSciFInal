import java.awt.*;
import java.awt.geom.*;

public class Bricks extends SpriteBase{
    //brick attributes
    private boolean isDestroyed;
    private static int bWidth=65;
    private static int bHeight=20;
    private GamePanel panel;

    //brick constructor
    public Bricks(int xPos, int yPos,Color color,GamePanel p){
        super(new Rectangle2D.Double(xPos, yPos, bWidth, bHeight),color);
        this.panel=p;
        initiate(); //setting isDestroyed to false outside of constructor
    }
    public void initiate(){
        isDestroyed=false;
    }
    public void Destroyed(){
        isDestroyed=true;
    }
    public boolean checkStatus(){
        return isDestroyed;
    }

}
