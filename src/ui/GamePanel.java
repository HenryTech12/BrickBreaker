package ui;

import components.Ball;
import components.Board;
import components.Bricks;
import constants.UIConstants;
import music.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private JFrame gameFrame;
    private List<Bricks> bricks = new ArrayList<>();
    private Random random = new Random();
    int brickX = 10, brickY = 50;
    private final Color[] BRICK_COLORS = UIConstants.brickColors();
    private Board gameBoard;
    private int boardX = 0, boardY = UIConstants.GAME_HEIGHT - 100;
    private Ball ball;
    private int ballX, ballY;
    private char boardDirection;
    private MusicPlayer musicPlayer;

    private Timer timer;
    private int ballSpeedX = 2; // horizontal speed
    private int ballSpeedY = -2; // vertical speed
    private boolean boardMoved;
    private int lives = 3;
    private int score = 0;

    public void init() {

        ballX = (UIConstants.BOARD_WIDTH / 2);
        ballY = boardY - UIConstants.BALL_HEIGHT;

        addUI();
        this.setSize(UIConstants.GAME_WIDTH, UIConstants.GAME_HEIGHT);
        this.setVisible(true);
        this.setLayout(null);
        this.setOpaque(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(new BoardActionKey());

        gameFrame = new JFrame();
        gameFrame.setSize(UIConstants.GAME_WIDTH, UIConstants.GAME_HEIGHT);
        gameFrame.setTitle("Brick Breaker - ");
        gameFrame.setVisible(true);
        gameFrame.setLayout(null);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setFocusable(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(this);

        this.setFocusable(true);
        this.requestFocusInWindow();

        timer = new Timer(10,this);
        timer.start();
        musicPlayer.playLoop("C:\\Users\\DELL\\Downloads\\mixkit-game-level-music-689.wav");

    }

    public void addUI() {
        for (int i = 0; i < UIConstants.BRICK_AMOUNT; i++) {
            Bricks brick = new Bricks(brickX, brickY, UIConstants.BRICK_WIDTH, UIConstants.BRICK_HEIGHT);
            brickX += UIConstants.BRICK_WIDTH + 20;
            if (brickX >= UIConstants.GAME_WIDTH-UIConstants.BALL_WIDTH) {
                brickX = 10;
                brickY += UIConstants.BRICK_HEIGHT + 20;
            }
            brick.setBounds(brickX, brickY, UIConstants.BRICK_WIDTH, UIConstants.BRICK_HEIGHT);
            bricks.add(brick);

        }

        gameBoard = new Board(boardX, boardY, UIConstants.BOARD_WIDTH, UIConstants.BOARD_HEIGHT);

        ball = new Ball(ballX, ballY, UIConstants.BALL_WIDTH, UIConstants.BALL_HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("LIVES: " + lives, UIConstants.GAME_WIDTH - 100, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("SCORE: " + score, UIConstants.GAME_WIDTH - 300, 20);

        for (Bricks brick : bricks) {
            int rand = random.nextInt(BRICK_COLORS.length);
            g.setColor(BRICK_COLORS[rand]);
            brick.drawBrick(g);
            if (!brick.isDestroyed()) {
                brick.drawBrick(g);
            }
        }

        g.setColor(Color.GRAY);
        System.out.println(boardX);
        gameBoard.drawBoard(g);

        g.setColor(Color.WHITE);
        ball.drawBall(g);
    }

    public void moveBoard() {
        final int BOARD_SPEED = 5;
        if(boardDirection == 'L') {
            boardX -= BOARD_SPEED;
            if(boardX < 0) boardX = 0;
        } else if(boardDirection == 'R') {
            boardX += BOARD_SPEED;
            if(boardX > UIConstants.GAME_WIDTH - UIConstants.BOARD_WIDTH)
                boardX = UIConstants.GAME_WIDTH - UIConstants.BOARD_WIDTH;
        }
        gameBoard.setX(boardX); // important! update the board object
    }

    public void moveBall() {
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Bounce off left/right walls
        if (ballX <= 0) {
            ballX = 0;
            ballSpeedX = -ballSpeedX;
        } else if (ballX >= UIConstants.GAME_WIDTH - UIConstants.BALL_WIDTH) {
            ballX = UIConstants.GAME_WIDTH - UIConstants.BALL_WIDTH;
            ballSpeedX = -ballSpeedX;
        }

        // Bounce off top
        if (ballY <= 0) {
            ballY = 0;
            ballSpeedY = -ballSpeedY;
        }

        // Bounce off the board (paddle)
        if (ballY + UIConstants.BALL_HEIGHT >= boardY &&
                ballX + UIConstants.BALL_WIDTH >= boardX &&
                ballX <= boardX + UIConstants.BOARD_WIDTH) {

            ballY = boardY - UIConstants.BALL_HEIGHT; // prevent sticking
            ballSpeedY = -ballSpeedY; // bounce up
        }

        // Check if ball goes below the screen (missed)
        if (ballY > UIConstants.GAME_HEIGHT) {
            // reset ball to start position
            ballX = UIConstants.BOARD_WIDTH / 2;
            ballY = boardY - UIConstants.BALL_HEIGHT;
            ballSpeedY = -2; // start moving up again
            lives--; // lose a life
            boardMoved = false; // stop ball movement until player moves the board again
            if (lives <= 0) {
                timer.stop();
            }
        }

        ball.setX(ballX); // update Ball object
        ball.setY(ballY);
    }

    public void checkBrickCollision() {
        for (Bricks brick : bricks) {
            // Skip already "destroyed" bricks
            if (brick.isDestroyed()) continue;

            // Get brick bounds
            int brickX = (int) brick.getX();
            int brickY = (int) brick.getY();
            int brickWidth = UIConstants.BRICK_WIDTH;
            int brickHeight = UIConstants.BRICK_HEIGHT;

            // Ball bounds
            int ballRight = ballX + UIConstants.BALL_WIDTH;
            int ballBottom = ballY + UIConstants.BALL_HEIGHT;

            boolean hitX = ballRight >= brickX && ballX <= brickX + brickWidth;
            boolean hitY = ballBottom >= brickY && ballY <= brickY + brickHeight;

            if (hitX && hitY) {
                // Ball hit the brick
                brick.setDestroyed(true); // mark brick as destroyed
                bricks.remove(brick); // remove brick from the list
                ballSpeedY = -ballSpeedY; // bounce the ball
                score += 2;
                break; // only handle one collision per frame
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        moveBoard();
        System.out.println(boardMoved);
        if(boardMoved) {
            moveBall();
        }
        checkBrickCollision();
        repaint();
    }

    class BoardActionKey implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                boardDirection = 'L';
                System.out.println("MOVE BOARD TO THE LEFT");
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                boardDirection = 'R';
                System.out.println("MOVE BOARD TO THE RIGHT");
            }
            boardMoved = true;
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}