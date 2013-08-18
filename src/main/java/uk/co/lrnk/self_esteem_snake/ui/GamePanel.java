package uk.co.lrnk.self_esteem_snake.ui;

import uk.co.lrnk.self_esteem_snake.GameState;
import uk.co.lrnk.self_esteem_snake.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements SnakeGameView {

    SnakeGame game;
    boolean startNewGame = true;
    ASCIIWorldGenerator generator;
    SnakeKeyListener snakeKeyListener;

    public GamePanel() {
        generator = new ASCIIWorldGenerator();

        snakeKeyListener = new SnakeKeyListener();

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(snakeKeyListener);

        KeyAdapter restartListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (game.getState() == GameState.GAME_OVER &&
                        e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startNewGame = true;
                }
            }
        };

        addKeyListener(restartListener);

    }

    public void start() {

        while (true) {
            while (!startNewGame) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }

            startNewGame = false;

            game = new SnakeGame();
            game.setView(this);
            game.initGame();
            snakeKeyListener.setSnake(game.getSnake());
            game.startGameAndPlayTillDeath();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (game.getState()) {
            case PLAYING:
                drawPlaying(g);
                break;
            case GAME_OVER:
                drawGameOver(g);
                break;
        }
    }

    private void drawPlaying(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        drawWorldString(g2, generator.getWorldString(game.getWorld()));
    }

    private void drawGameOver(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g2.setColor(Color.green);

        String gameOverMessage = "GAME OVER";
        int gameOverX = (getWidth() / 2) - getLeftOffsetForCenteringText(g2, gameOverMessage);
        int gameOverY = (getHeight() / 2) - (g.getFontMetrics().getHeight() / 2);

        g2.drawString(gameOverMessage, gameOverX, gameOverY);

    }

    private int getLeftOffsetForCenteringText(Graphics2D g, String text) {

        int characterWidth = g.getFontMetrics().getWidths()[0];
        double halfTheNumberOfCharacters = ((double) text.length() / 2);

        return (int) (characterWidth * halfTheNumberOfCharacters);
    }

    private void drawWorldString(Graphics2D g, String worldString) {
        int leftOffset = 10;
        int topOffset = 0;

        g.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g.setColor(Color.green);

        for (String line : worldString.split("\n"))
            g.drawString(line, leftOffset, topOffset += g.getFontMetrics().getHeight());
    }

    @Override
    public void refreshView() {
        repaint();
    }
}
