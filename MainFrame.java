import javax.swing.*;

public class MainFrame extends JFrame{
    public static void main(String[]args){
        new MainFrame();
    }

    public MainFrame()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(100,50);
        setSize(1000,600);
        setResizable(false);
        setVisible(true);
    }
}