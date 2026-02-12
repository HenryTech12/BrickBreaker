package components;

import java.awt.*;

public class Ball extends Rectangle {

    private int x;
    private int y;
    private int width;
    private int height;

    public Ball(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawBall(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setBackground(Color.WHITE);
        graphics2D.fillRoundRect(x,y,width,height,45,45);
    }
}
