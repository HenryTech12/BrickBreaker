package components;

import java.awt.*;
import java.util.Random;

public class Bricks extends Rectangle {

    private int x;
    private int y;
    private int width;
    private int height;
    private Random random = new Random();

    public Bricks(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Bricks() {

    }

    public Color[] brickColors() {
        return new Color[] {Color.RED,Color.ORANGE,Color.BLUE,Color.YELLOW};
    }

    public void drawBrick(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.fillRect(x,y,width,height);

    }
}
