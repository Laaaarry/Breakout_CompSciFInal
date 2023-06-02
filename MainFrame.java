import javax.swing.*;

public class MainFrame extends JFrame {
    // the main JFrame the game will run on
    // main method
    public static void main(String[] args) {
        new MainFrame();

    }

    // constructor for the JFrame and setting its characteristics
    public MainFrame() {
        // Adds the JPanel, which is where almost everything is
        GamePanel gp = new GamePanel();
        // Troubleshooting, unecessary for code to run
        System.out.println(gp.getHeight() + " " + gp.getWidth());
        // Sets attributes for the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Breakout!!");
        setSize(1000, 600);
        setResizable(false);
        add(gp);

        setVisible(true);
    }
}
