import java.awt.*;

public class Heart {
    private Image h;
    int xPos;
    int yPos;

    public Heart(Image img){
        this.h=img;
    }
    public void heartImage(int x, int y,Graphics2D g2){
        g2.drawImage(h, x, y,null);
    }
}
