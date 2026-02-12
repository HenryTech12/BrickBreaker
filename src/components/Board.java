package components;

import constants.UIConstants;

import java.awt.*;
import java.util.Random;

public class Board extends Rectangle {

    private int x;
    private int y;
    private int width;
    private int height;

    public Board(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawBoard(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.fillRoundRect(x,y,width,height,30,30);

    }

    public int calculateBoardX() {
        return 0;
    }

    public int calculateBoardY() {
        return UIConstants.GAME_HEIGHT - 100;
    }

}
