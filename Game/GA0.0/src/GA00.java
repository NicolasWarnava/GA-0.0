
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.sound.sampled.Clip;
import javax.swing.*;


public class GA00 extends  JPanel implements ActionListener, KeyListener{
 int boardWidth = 360;
 int boardHeight = 640;
    //Images
    Image backgroundImg;
    Image shipImg;
    Image shipOn;

    //audios
    Clip moveSound;

    //Ship
    int shipX = boardWidth/8;
    int shipY = boardHeight/2;
    int shipWidth = 34;
    int shipHeight = 24;

    class Ship{
        int x = shipX;
        int y = shipY;
        int width  = shipWidth;
        int height = shipHeight;
        Image img;

        Ship(Image img){
            this.img = img;
        }
    }
    //game logic
    Ship ship;
    double velocityX = 0;
    double velocityY = 0;
    double inertia = 0.7;
    double acceleration = 0.5;

    //controls

    boolean up = false, down = false, left = false, right = false;

    Random random = new Random();         

    Timer gameLoop;
  
    boolean gameOver = false;
    double score = 0;



    GA00() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);
        //load images
        backgroundImg = new ImageIcon(getClass().getResource("Graphics/Levels/spaceBG.png")).getImage();
        shipImg = new ImageIcon(getClass().getResource("Graphics/Ship/ship.png")).getImage();
        shipOn = new ImageIcon(getClass().getResource("Graphics/Ship/shipOn.jpg")).getImage();
        //load audios
        AudioPlayer moveSound = new AudioPlayer();
        moveSound.loadSound("./ndem.wav");
        // deixar mais baixo (30% do volume máximo)
        moveSound.setVolume(0.6f);
        moveSound.play();
        //bird
        ship = new Ship(shipImg);
        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //background
        g.drawImage(backgroundImg, 0,0, boardWidth, boardHeight, null);

        //bird
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if(gameOver){
            g.drawString("Game Over: " + String.valueOf((int)score),10,35);
        }
        else{
            g.drawString(String.valueOf((int)score),10,35);
        }
    }
    public void move() {



        //acceleration 
        if(up){velocityY -=acceleration;}
        if(down){velocityY += acceleration;}
        if(left){velocityX -= acceleration;}
        if(right){velocityX += acceleration;}
        //realease inertia        
        if(!up && velocityY < 0){velocityY += inertia;}
        if(!down && velocityY > 0){velocityY -= inertia;}
        if(!left && velocityX < 0){velocityX +=inertia;}
        if(!right && velocityX >0){velocityX -=inertia;}


        //update values
        ship.y += (int) velocityY;
        ship.x += (int) velocityX;
        //fazer trava de velocidade máxima
        
        ship.y = Math.max(ship.y,0);
        ship.y = Math.min(ship.y, boardHeight - shipHeight);
        ship.x = Math.max(ship.x, 0);
        ship.x = Math.min(ship.x, boardWidth - shipWidth); //trava nave dentro do frame


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        ship.img = shipOn;
        if(e.getKeyCode() == KeyEvent.VK_W){

            up = true;
            
        }
        if(e.getKeyCode() == KeyEvent.VK_S){

            down = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_A){

            left = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){

            right = true;
        }
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            up = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            left = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            right = false;
        }
        if(!up && !down && !left && !right) {
        ship.img = shipImg;
        }
    }

 
}
