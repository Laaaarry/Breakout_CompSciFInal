import javax.swing.*;

public class MainFrame extends JFrame {
    //the main JFrame the game will run on
    public static void main(String[]args){
        new MainFrame();
        
    }

    //constructor for the JFrame and setting its characteristics
    public MainFrame()
    {
        GamePanel gp=new GamePanel();
        System.out.println(gp.getHeight()+" "+gp.getWidth());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Breakout!!");
        add(gp);

        setSize(1000,600);
        setResizable(false);
        setVisible(true);
    }
}
