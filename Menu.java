import javax.swing.*;

public class Menu
{

    JButton resumeButton;
    JButton newGameButton;
    JButton exitButton;

	public Menu(JFrame f){
        resumeButton = new JButton("Resume Game");
        newGameButton = new JButton("New Game");
        exitButton = new JButton("Exit Game");

        resumeButton.setBounds(400, 100, 200, 100);
        newGameButton.setBounds(400, 250, 200, 100);
        exitButton.setBounds(400, 400, 200, 100);
	}
    public void addButtons(JFrame f){
        f.add(resumeButton);
        f.add(newGameButton);
        f.add(exitButton);
    }
    public void removeButtons(JFrame f){
        f.remove(resumeButton);
        f.remove(newGameButton);
        f.remove(exitButton);
    }

}