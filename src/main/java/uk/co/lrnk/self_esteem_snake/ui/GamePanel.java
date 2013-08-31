package uk.co.lrnk.self_esteem_snake.ui;

import uk.co.lrnk.self_esteem_snake.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements SnakeGameView {

    SnakeGame game;
    GameState gameState = GameState.START_MENU;

    StartMenu startMenu;
    ASCIIWorldGenerator generator;
    SnakeKeyListener snakeKeyListener;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();

        startMenu = new StartMenu(this);

        generator = new ASCIIWorldGenerator();
        snakeKeyListener = new SnakeKeyListener();

        addKeyListener(new GameKeyListener(this));
    }

    public void start() {

        while (true) {
            while (gameState != GameState.READY_TO_START_GAME) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }

            gameState = GameState.LOADING;
//            TODO make dynamic
            game = new BookwormGame();
            game.initGame();
            snakeKeyListener.setSnake(game.getSnake());
            addKeyListener(snakeKeyListener);
            game.setView(this);
            gameState = GameState.PLAYING;
            game.startGameAndPlayTillDeath();
            gameState = GameState.GAME_OVER;
            removeKeyListener(snakeKeyListener);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            doPaint((Graphics2D) g);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void doPaint(Graphics2D g) {
        switch (gameState) {
            case START_MENU:
                drawStartMenu(g);
                break;
            case LOADING:
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                break;
            case PLAYING:
                drawPlaying(g);
                break;
            case GAME_OVER:
                drawGameOver(g);
                break;
        }
    }

    private void drawStartMenu(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        int leftOffset = 10;
        int lineHeight = g.getFontMetrics().getHeight();

        g.setFont(new Font("Monospaced", Font.PLAIN, 13));
        g.setColor(Color.green);

        g.scale(1, 0.75);
        drawTitle(g, leftOffset, 0, lineHeight);
        g.scale(1, 1.25);
        drawGameTypeSelect(g, leftOffset, 210, lineHeight);
        drawDifficultySelect(g, leftOffset, 230, lineHeight);

    }

    private void drawTitle(Graphics2D g, int leftOffset, int topOffset, int lineHeight) {
        for (String line : startMenu.getTitle().split("\n")) {
            g.drawString(line, leftOffset, topOffset += lineHeight);
        }
        topOffset += lineHeight;
    }

    private void drawGameTypeSelect(Graphics2D g, int leftOffset, int topOffset, int lineHeight) {
        String gameTypeDescription = "Game type: ";
        g.drawString(gameTypeDescription, leftOffset, topOffset);
        leftOffset += g.getFontMetrics().getWidths()[0] * (gameTypeDescription.length() + 1);
        int menuItemNumber = 0;
        for(GameType type : GameType.values()) {
            menuItemNumber++;

            g.drawString(type.name(), leftOffset, topOffset);

            if(menuItemNumber != GameType.values().length) {
                leftOffset += g.getFontMetrics().getWidths()[0] * (type.name().length() + 1);
                g.drawString("/", leftOffset, topOffset);
                leftOffset += g.getFontMetrics().getWidths()[0] * 2;
            }
        }
    }

    private void drawDifficultySelect(Graphics2D g, int leftOffset, int topOffset, int lineHeight) {
        String difficultyDescription = "Difficulty: ";
        g.drawString(difficultyDescription, leftOffset, topOffset);
        leftOffset += g.getFontMetrics().getWidths()[0] * (difficultyDescription.length() + 1);
        int menuItemNumber = 0;
        for(Difficulty difficulty : Difficulty.values()) {
            menuItemNumber++;

            g.drawString(difficulty.name(), leftOffset, topOffset);

            if(menuItemNumber != Difficulty.values().length) {
                leftOffset += g.getFontMetrics().getWidths()[0] * (difficulty.name().length() + 1);
                g.drawString("/", leftOffset, topOffset);
                leftOffset += g.getFontMetrics().getWidths()[0] * 2;
            }
        }
    }


    private void drawPlaying(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

//        TODO make dynamic
        drawWorldString(g, generator.getWorldString((BookwormWorld) game.getWorld()));
    }

    private void drawGameOver(Graphics2D g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        Font font = new Font("Monospaced", Font.PLAIN, 11);
        g.setFont(font);
        g.setColor(Color.yellow);

        if (game.getPreviousHighScore() < game.getScore()) {

            String gameOverMessage = "NEW HIGH SCORE: " + game.getScore();
            int gameOverX = getCenteredStringX(g, font, gameOverMessage);
            int gameOverY = getCenteredStringY(g, font, gameOverMessage);
            g.drawString(gameOverMessage, gameOverX, gameOverY);

        } else {

            String score = "" + game.getScore();
            int scoreX = getCenteredStringX(g, font, score);
            int scoreY = getCenteredStringY(g, font, score) - g.getFontMetrics(font).getHeight();
            g.drawString(score, scoreX, scoreY);

            String gameOverMessage = "HIGH SCORE: " + game.getPreviousHighScore();
            int gameOverX = getCenteredStringX(g, font, gameOverMessage);
            int gameOverY = getCenteredStringY(g, font, gameOverMessage);
            g.drawString(gameOverMessage, gameOverX, gameOverY);

        }

    }

    private int getCenteredStringX(Graphics2D g, Font f, String string) {
        FontMetrics fm = g.getFontMetrics(f);
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(string, g);

        int textWidth = (int) (rect.getWidth());
        int panelWidth = this.getWidth();

        return (panelWidth - textWidth) / 2;
    }

    private int getCenteredStringY(Graphics2D g, Font f, String string) {
        FontMetrics fm = g.getFontMetrics(f);
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(string, g);

        int textHeight = (int) (rect.getHeight());
        int panelHeight = this.getHeight();

        return (panelHeight - textHeight) / 2 + fm.getAscent();
    }

    private void drawWorldString(Graphics2D g, String worldString) {
        int leftOffset = 10;
        int topOffset = 0;

        g.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g.setColor(Color.yellow);

        for (String line : worldString.split("\n")) {
            g.drawString(line, leftOffset, topOffset += g.getFontMetrics().getHeight());
        }
    }

    @Override
    public void refreshView() {
        repaint();
    }
}
