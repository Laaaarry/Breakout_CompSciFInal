import java.awt.*;

public class Background {
    // This class contains methods that creates and draws the background
    private Image bgImage; // Variable for the image of the background

    // Constructor: gets image for background
    public Background(Image img) {
        this.bgImage = img;
    }

    // draws background
    public void paintBg(Graphics2D g2) {
        g2.drawImage(bgImage, 0, -100, null);
    }
}
