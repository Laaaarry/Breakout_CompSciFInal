import java.awt.*;

public class Background {
    private Image bgImage;

    // Constructor: gets image for background
    public Background(Image img) {
        this.bgImage = img;
    }

    // paints background
    public void paintBg(Graphics2D g2) {
        g2.drawImage(bgImage, 0, -100, null);
    }
}
