// GamePanel.java
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    int boardWidth = 360;
    int boardHeight = 640;

    // Imagens
    Image backgroundImg;
    Image shipImg;
    Image shipOn;
    Image enemyImg;

    // Lógica/Fisica

    double velocityX = 0;
    double velocityY = 0;
    double inertia = 0.7;
    double acceleration = 0.5;

    Random random = new Random();
    //Objetos moveis
    Ship ship;
    private List<Bullet> bullets = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();


    //controles
    boolean up = false, down = false, left = false, right = false;

    //game Values
    Timer gameLoop;
    boolean gameOver = false;
    double score = 0;
    
    // Áudio
    AudioPlayer moveSound;

    public GamePanel() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Carrega imagens
        backgroundImg = new ImageIcon(getClass().getResource("Graphics/Levels/spaceBG.png")).getImage();
        shipImg = new ImageIcon(getClass().getResource("Graphics/Ship/ship.png")).getImage();
        shipOn = new ImageIcon(getClass().getResource("Graphics/Ship/shipOn.jpg")).getImage();
        enemyImg = new ImageIcon(getClass().getResource("Graphics/Enemys/esplendido.jpg")).getImage();
        // Carrega áudio
        moveSound = new AudioPlayer();
        moveSound.loadSound("Sound/ndem.wav");   // ajuste o caminho conforme seu recurso
        moveSound.setVolume(0.6f);

        // Cria nave
        int shipX = boardWidth / 8;
        int shipY = boardHeight / 2;
        int shipWidth = 34;
        int shipHeight = 24;
        ship = new Ship(shipX, shipY, shipWidth, shipHeight, shipImg);

        //cria inimigos
        enemies.add(new Enemy(250, -40, 32, 32, enemyImg));

        // Loop do jogo
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }
    private void updateGame() {
        // Entrada → altera velocidades
        if (up)    velocityY -= acceleration;
        if (down)  velocityY += acceleration;
        if (left)  velocityX -= acceleration;
        if (right) velocityX += acceleration;

        // Inércia
        if (!up && velocityY < 0)  velocityY += inertia;
        if (!down && velocityY > 0) velocityY -= inertia;
        if (!left && velocityX < 0) velocityX += inertia;
        if (!right && velocityX > 0) velocityX -= inertia;

        // Aplica movimento à nave
        ship.y += (int) velocityY;
        ship.x += (int) velocityX;

        // Limites
        ship.y = Math.max(ship.y, 0);
        ship.y = Math.min(ship.y, boardHeight - ship.height);
        ship.x = Math.max(ship.x, 0);
        ship.x = Math.min(ship.x, boardWidth - ship.width);

        // Atualizar balas
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.update();
            if (b.isOffScreen(boardHeight)) {
                bullets.remove(i);
                i--;
            }
        }   
        // atualizar inimigos
        for (int i = 0; i < enemies.size(); i++) {
            Enemy en = enemies.get(i);
            en.update();
            if (en.isOffScreen(boardHeight)) {
                enemies.remove(i);
                i--;
            }
        }

        checkCollisions();
    }
     @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // Fundo
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // Nave
        ship.draw(g);

        // inimigos
        for (Enemy en : enemies) {
            en.draw(g);
        }

        // desenhar balas
        for (Bullet b : bullets) {
            b.draw(g);
        }

        // Placar
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + (int) score, 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }

    private void fire() {
    int bulletX = ship.x + ship.width / 2 - 2;   // centro da nave
    int bulletY = ship.y;                        // saindo da frente
    bullets.add(new Bullet(bulletX, bulletY));
    // aqui depois pode tocar som de tiro

    }
    private void checkCollisions() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            Rectangle rb = new Rectangle(b.x, b.y, b.width, b.height);

            for (int j = 0; j < enemies.size(); j++) {
                Enemy en = enemies.get(j);
                Rectangle re = new Rectangle(en.x, en.y, en.width, en.height);

                if (rb.intersects(re)) {
                    // remove os dois, soma pontos etc.
                    bullets.remove(i);
                    enemies.remove(j);
                    i--;    
                    break;
                }
            }
        }
    }


    private void playMoveSound() {
        if (moveSound != null) {
            moveSound.play();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ship.setImage(shipOn);

        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = true;
           // playMoveSound();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = true;
           // playMoveSound();
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = true;
            //playMoveSound();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = true;
            //playMoveSound();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = false;
        if (e.getKeyCode() == KeyEvent.VK_S) down = false;
        if (e.getKeyCode() == KeyEvent.VK_A) left = false;
        if (e.getKeyCode() == KeyEvent.VK_D) right = false;

        if (!up && !down && !left && !right) {
            ship.setImage(shipImg);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }
}
