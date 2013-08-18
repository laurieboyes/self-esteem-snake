package uk.co.lrnk.self_esteem_snake.ui;

import uk.co.lrnk.self_esteem_snake.SnakeGame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    SnakeGame game;
    ASCIIWorldGenerator generator;
    KeyboardInputManager inputManager;

    public GamePanel(SnakeGame game) {
        this.game = game;

        generator = new ASCIIWorldGenerator();

        inputManager = new KeyboardInputManager();
        inputManager.setSnake(game.getSnake());

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(inputManager);

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
}
