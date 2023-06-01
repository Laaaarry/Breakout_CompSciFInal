import java.awt.*;

public class Heart {
    private Image h;
    int xPos;
    int yPos;

    // Constructor for the image that shows the lives
    public Heart(Image img) {
        this.h = img;
    }

    // Draws the heart
    public void heartImage(int x, int y, Graphics2D g2) {
        g2.drawImage(h, x, y, null);
    }
}
