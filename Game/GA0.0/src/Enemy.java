import java.awt.Graphics;
import java.awt.Image;

public class Enemy {
    public int x, y;
    public int width, height;
    public int speedY = 2; // inimigo descendo
    private Image img;

    public Enemy(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width  = width;
        this.height = height;
        this.img = img;
    }

    public void update() {
        y += speedY;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    public boolean isOffScreen(int boardHeight) {
        return y > boardHeight;
    }
}
