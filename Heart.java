import java.awt.*;
import javax.swing.*;


public class Heart {
    private Image h;
    int xPos;
    int yPos;

    public Heart(String img){
        this(new ImageIcon(img).getImage());
    }

    public Heart(Image img){
        this.h=img;
    }
    public void heartImage(int x, int y,Graphics2D g2){
        g2.drawImage(h, x, y,null);
    }
}
