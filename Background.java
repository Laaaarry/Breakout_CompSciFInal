import java.awt.*;
import javax.swing.*;

public class Background {
    private Image bgImage;

    public Background(String img) {
        this(new ImageIcon(img).getImage());
    }

    public Background(Image img) {
        this.bgImage = img;
    }

    public void paintBg(Graphics2D g2) {
        g2.drawImage(bgImage, 0, -100, null);
    }
}
