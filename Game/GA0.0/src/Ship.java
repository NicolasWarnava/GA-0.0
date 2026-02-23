// Ship.java
import java.awt.Graphics;
import java.awt.Image;

public class Ship {
    public int x;
    public int y;
    public int width;
    public int height;
    private Image img;

    public Ship(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public void setImage(Image img) {
        this.img = img;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }
}
