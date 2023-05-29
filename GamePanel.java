import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel{
    
    private Ball ball;
    private Paddle paddle;
    private Bricks[]bricks; //an array for creating bricks
    private JFrame f;
    Menu m=new Menu(f);

    private static int ballSTARTX=200;
    private static int ballSTARTY=400;
    private int row=12;
    private int col=8;
    private int pSPEED=10;

    private Timer timer;
    private int delay=10;
    private boolean GameRunning=false;
    private int score;
    private int round;
    private int lives;

    public GamePanel(JFrame f){
        this.f=f;

        Reset();
        addKeyListener(new KeyEvents());
        setFocusable(true);
        
        timer.start();
    }
    private void Reset(){
        ball=new Ball(getRandomColor(),this,ballSTARTX,ballSTARTY);
        paddle=new Paddle(getRandomColor(),this);
        bricks=new Bricks[row*col];
        createBricks();
        timer=new Timer(delay,new TimeEvents());
        round=1;
        score=0;
        lives=3;
    }
    public void createBricks(){
        int b=0;
        for(int i=1;i<=col;i++)
        {
            for(int j=1;j<=row;j++){
                bricks[b]=new Bricks((j-1)*75+50,(i-1)*35+70,getRandomColor(),this);
                b++;
            }
        }
    }

    //all sprites will be in a random color for fun
    private Color getRandomColor(){
        Color color = new Color
        //the color will always be lighter since we want a dark background
        ((int)(Math.random()*120+135),(int)(Math.random()*120+135),(int)(Math.random()*120+135));
        return color;
    }

    public void checkCollisions(){
        Rectangle ba=ball.getBounds();
        Bricks brick;
        Rectangle br;
        Rectangle pa=paddle.getBounds();
        //bouncing ball off paddle - angles? randomization? - a little boring the way it is
        if(ba.intersects(pa)){
            ball.Up();
            if(ball.getCenterX()<=paddle.getX()+paddle.getWidth()/2){
                ball.Left();
            }
            else if(ball.getCenterX()>paddle.getX()+paddle.getWidth()/2){
                ball.Right();
            }
        }
        for(int i=0;i<bricks.length;i++){
            brick=bricks[i];
            br=brick.getBounds();
            if(!bricks[i].checkStatus()&&ba.intersects(br)){
                bounceBallBrick(ball, bricks[i]);
                bricks[i].Destroyed();
                score+=10;
            }
        }
    }
    public void bounceBallBrick(Ball ba, Bricks br){
        if(ball.BallAbove(ba,br)){
            ball.Up();
        }
        if(ball.BallBelow(ba, br)){
            ball.Down();
        }
        if(ball.BallLeft(ba, br)){
            ball.Left();
        }
        if(ball.BallRight(ba, br)){
            ball.Right();
        }
    }

    public void NewRound(){
        int flag=0;
        for(int i=0;i<bricks.length;i++){
            if(!bricks[i].checkStatus()){
                flag++;
            }
        }
        if(flag==0){
            bricks=new Bricks[row*col];
            createBricks();
            round++;
            ball.speedUp();
            score+=50;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        paintGame(g2);
        inGameMessage(g2);

    }
    
    public void paintGame(Graphics2D g2){
        ball.draw(g2);
        paddle.draw(g2);
        for(int i=0;i<bricks.length;i++){
            if(!bricks[i].checkStatus()){
                bricks[i].draw(g2);
            }
        }
    }
    public void GameText(String message, int x, int y, Graphics g2){
        Font gFont=new Font("ComicSans",Font.BOLD+Font.ITALIC,30);
        g2.setFont(gFont);
        g2.setColor(Color.CYAN);
        g2.drawString(message,x,y);
    }
    public void inGameMessage(Graphics2D g2){
        String roundString="Round: "+String.valueOf(round);
        GameText(roundString,50,40,g2);
        String scoreString="Score: "+String.valueOf(score);
        GameText(scoreString,300,40,g2);
    }
    
    public void gameCycle(){
        ball.bMove();
        paddle.pMove();
        checkCollisions();
        repaint();
    }

    public void NewGame(){
        Reset();
        ResumeGame();
    }
    public void ResumeGame(){
        m.removeButtons(f);
        GameRunning=true;
        timer.restart();
        repaint();
    }
    public void PauseGame(){
        GameRunning=false;
        timer.stop(); 
    }
    public void exit(){
        System.exit(0);
    }
    
    class TimeEvents implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(GameRunning){
                gameCycle();
                NewRound();
            }
            if(!GameRunning){

            }
        }
    }
    class KeyEvents implements KeyListener{
        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e)
        {
            paddle.setMoveSpeed(0);
            System.out.print("Stop");
        }
        public void keyPressed(KeyEvent e)
        {
            
            if (e.getKeyCode()==KeyEvent.VK_LEFT){
                paddle.setMoveSpeed(-pSPEED);
                System.out.print("Left");
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                paddle.setMoveSpeed(pSPEED);
                System.out.print("right");
            }

            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                NewGame();
            }
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                ResumeGame();
            }
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                PauseGame();
            }
        }
    }

    
}