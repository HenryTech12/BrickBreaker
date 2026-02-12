package components;

import constants.UIConstants;

import java.awt.*;
import java.util.Random;

public class Bricks extends Rectangle {

    private int x;
    private int y;
    private int width;
    private int height;
    private final Random random = new Random();
    private final Color[] BRICK_COLORS = UIConstants.brickColors();

    public Bricks(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Bricks() {

    }


    private boolean destroyed = false;

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }


    public Color[] brickColors() {
        return new Color[] {Color.RED,Color.ORANGE,Color.BLUE,Color.YELLOW};
    }

    public void drawBrick(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.fillRect(x,y,width,height);
        int rand = random.nextInt(BRICK_COLORS.length);
        graphics2D.setBackground(BRICK_COLORS[rand]);

    }
}
