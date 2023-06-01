import java.awt.*;
import java.awt.geom.*;

public class Bricks extends SpriteBase {
    // brick attributes
    private boolean isDestroyed;
    private static int bWidth = 80;
    private static int bHeight = 30;
    private GamePanel panel;

    // brick constructor
    public Bricks(int xPos, int yPos, Color color, GamePanel p) {
        super(new Rectangle2D.Double(xPos, yPos, bWidth, bHeight), color);
        this.panel = p;
        initiate(); // setting isDestroyed to false outside of constructor
    }

    // Each brick starts off not destroyed
    public void initiate() {
        isDestroyed = false;
    }

    // Is called when the brick is destroyed
    public void Destroyed() {
        isDestroyed = true;
    }

    // Returns if the brick is destroyed or not
    public boolean checkStatus() {
        return isDestroyed;
    }

}
