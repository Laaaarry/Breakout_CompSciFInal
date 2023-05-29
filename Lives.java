import java.awt.*;
import java.awt.geom.*;


public class Lives extends SpriteBase {
    private static int size=30;
    GamePanel panel;
    public Lives(int xPos, int yPos, GamePanel p){
        super(new Ellipse2D.Double(xPos, yPos, size,size),Color.RED);
        this.panel=p;
    }
}
