import java.awt.*;
import java.awt.geom.*;

public class Bricks extends SpriteBase {
    // This class is the object of the bricks
    // brick attributes
    private boolean isDestroyed; // indicates if the brick is destroyed or not (true = destroyed)
    // Dimensions of the brick
    private static int bWidth = 80;
    private static int bHeight = 30;

    // brick constructor
    public Bricks(int xPos, int yPos, Color color, GamePanel p) {
        // the GamePanel instance was not used
        super(new Rectangle2D.Double(xPos, yPos, bWidth, bHeight), color);
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
