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
    boolean gameInitComplete = false;
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

            gameInitComplete = false;
            game = new SnakeGame();
            game.initGame();
            snakeKeyListener.setSnake(game.getSnake());
            game.setView(this);
            gameInitComplete = true;
            game.startGameAndPlayTillDeath();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(gameInitComplete){
            try {
                paintSnakeGame((Graphics2D) g);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void paintSnakeGame(Graphics2D g) {
        switch (game.getState()) {
            case PLAYING:
                drawPlaying(g);
                break;
            case GAME_OVER:
                drawGameOver(g);
                break;
        }
    }

    private void drawPlaying(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        drawWorldString(g, generator.getWorldString(game.getWorld()));
    }

    private void drawGameOver(Graphics2D g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        Font font = new Font("Monospaced", Font.PLAIN, 11);
        g.setFont(font);
        g.setColor(Color.green);

        String gameOverMessage = "GAME OVER";
        int gameOverX = getCenteredStringX(g,font,gameOverMessage);
        int gameOverY = getCenteredStringY(g,font,gameOverMessage) - g.getFontMetrics(font).getHeight();
        g.drawString(gameOverMessage, gameOverX, gameOverY);

        String score = "" + game.getScore();
        int scoreX = getCenteredStringX(g,font,score);
        int scoreY = getCenteredStringY(g,font,score);
        g.drawString(score, scoreX, scoreY);

    }

    private int getCenteredStringX(Graphics2D g, Font f, String string) {
        FontMetrics fm   = g.getFontMetrics(f);
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(string, g);

        int textWidth  = (int)(rect.getWidth());
        int panelWidth = this.getWidth();

        return (panelWidth  - textWidth) / 2;
    }

    private int getCenteredStringY(Graphics2D g, Font f, String string) {
        FontMetrics fm   = g.getFontMetrics(f);
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(string, g);

        int textHeight = (int)(rect.getHeight());
        int panelHeight= this.getHeight();

        return (panelHeight - textHeight) / 2  + fm.getAscent();
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
