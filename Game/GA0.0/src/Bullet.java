// Bullet.java
import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
    public int x, y;
    public int width = 4;
    public int height = 10;
    public int speed = -12; // negativo = sobe

    public Bullet(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }

    public boolean isOffScreen(int boardHeight) {
        return y + height < 0;
    }
}
