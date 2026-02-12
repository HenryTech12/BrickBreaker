package ui;

import components.Ball;
import components.Board;
import components.Bricks;
import constants.UIConstants;

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
    int brickX = 10, brickY = 10;
    private final Color[] BRICK_COLORS = UIConstants.brickColors();
    private Board gameBoard;
    private int boardX = 0, boardY = UIConstants.GAME_HEIGHT - 100;
    private Ball ball;
    private int ballX, ballY;
    private char boardDirection;

    public void init() {

        ballX = (UIConstants.BOARD_WIDTH / 2);
        ballY = boardY - UIConstants.BALL_HEIGHT;

        addUI();
        this.setSize(UIConstants.GAME_WIDTH, UIConstants.GAME_HEIGHT);
        this.setVisible(true);
        this.setLayout(null);
        this.setOpaque(true);
        this.setBackground(Color.BLACK);

        gameFrame = new JFrame();
        gameFrame.setSize(UIConstants.GAME_WIDTH, UIConstants.GAME_HEIGHT);
        gameFrame.setTitle("Brick Breaker");
        gameFrame.setVisible(true);
        gameFrame.setLayout(null);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.addKeyListener(new BoardActionKey());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(this);
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
        for (Bricks brick : bricks) {
            int rand = random.nextInt(BRICK_COLORS.length);
            g.setColor(BRICK_COLORS[rand]);
            System.out.println("BRICK X: " + brick.getX());
            brick.drawBrick(g);
        }

        g.setColor(Color.GRAY);
        gameBoard.drawBoard(g);

        g.setColor(Color.WHITE);
        ball.drawBall(g);
    }

    public void moveBoard() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(boardDirection);
    }

    class BoardActionKey implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

            System.out.println("UUUU");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                boardDirection = 'L';
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                boardDirection = 'R';
            }

            System.out.println("HHH");
        }

        @Override
        public void keyReleased(KeyEvent e) {

            System.out.println("BBBB");
        }
    }
}