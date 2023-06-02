import java.awt.*;

public class Heart {
    // class for the heart sprite
    private Image h; // image of the sprite
    int xPos; // X position where it will be drawn at
    int yPos; // Y position where it will be drawn at

    // Constructor for the image that shows the lives
    public Heart(Image img) {
        this.h = img;
    }

    // Draws the heart
    public void heartImage(int x, int y, Graphics2D g2) {
        g2.drawImage(h, x, y, null);
    }
}
