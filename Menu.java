import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JPanel implements ActionListener
{
    private Game game;
    private JFrame frame;

    JButton playButton;
    JButton exitButton;

	public Menu(JFrame frame)
	{
        playButton = new JButton("Play Game");
        exitButton = new JButton("Exit Game");

        playButton.setBounds(200, 300, 100, 30);
        exitButton.setBounds(200, 400, 100, 30);

        playButton.addActionListener(this);
        exitButton.addActionListener(this);

        frame.add(playButton);
        frame.add(exitButton);
	}

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == playButton)
        {
            try
            {
                game.gameLoop(game, frame);
            } catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
        }else if (e.getSource() == exitButton)
        {
            System.exit(0);
        }
    }
}