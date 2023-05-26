import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel
{

	Ball ball = new Ball(this);
	Racquet racquet = new Racquet(this);
	int speed = 1;

	private int getScore()
	{
		return speed - 1;
	}

	// constructor of Game class
	// registering a Keylistener
	public Game()
	{
		// anonymous class used when only one instance is needed
		// cannont be used to create more instances
		// need to declare and instantiate at the same time
		// extending from KeyListener class
		addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e)
			{
				racquet.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				racquet.keyPressed(e);
			}
		});
		setFocusable(true);
	}

	private void move()
	{
		ball.move();
		racquet.move();
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		ball.paint(g2d);
		racquet.paint(g2d);

		g2d.setColor(Color.GRAY);
		g2d.setFont(new Font("Verdana", Font.BOLD, 30));
		g2d.drawString(String.valueOf(getScore()), 10, 30);
	}

	public void gameOver(Game game)
	{
		JOptionPane.showMessageDialog(this, "your score is: " + getScore(), "Game Over", JOptionPane.YES_NO_OPTION);
		newGame(game);
	}

	public static void newGame(Game game)
	{
		game.ball.x = 250;
		game.ball.y = 500;
		game.ball.xa = ballInitialDir();
		game.ball.ya = 1;
		game.racquet.x = 120;
		game.speed = 1;
	}

	public static void main(String[] args) throws InterruptedException
	{
		JFrame frame = new JFrame("Mini Tennis");
		Game game = new Game();
		frame.add(game);
		frame.setSize(500, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		game.racquet.y = frame.getHeight() - 60;

		newGame(game);

		while (true)
		{
			game.move();
			game.repaint();
			Thread.sleep(10);
		}
	}

	public static int ballInitialDir()
	{
		if (Math.random() > 0.5)
		{
			return 1;
		}else
		{
			return -1;
		}
	}
}