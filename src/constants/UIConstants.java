package constants;

import java.awt.*;

public class UIConstants {

    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 500;
    public static final int BRICK_WIDTH = 100;
    public static final int BRICK_HEIGHT = 30;

    public static final int BRICK_AMOUNT = 15;
    public static final int BOARD_WIDTH = 100;
    public static final int BOARD_HEIGHT = 20;

    public static final int BALL_WIDTH = 20;
    public static final int BALL_HEIGHT = 20;

    public static Color[] brickColors() {
        return new Color[] {Color.RED,Color.ORANGE,Color.BLUE,Color.YELLOW};
    }
}
