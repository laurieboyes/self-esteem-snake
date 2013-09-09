package uk.co.lrnk.self_esteem_snake.ui;

import uk.co.lrnk.self_esteem_snake.bookworm.BookwormGame;
import uk.co.lrnk.self_esteem_snake.bookworm.BookwormWorld;
import uk.co.lrnk.self_esteem_snake.SnakeGame;
import uk.co.lrnk.self_esteem_snake.config.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements SnakeGameView {

    Config config;

    SnakeGame game;
    GameState gameState = GameState.START_MENU;

    StartMenu startMenu;
    ASCIIWorldGenerator generator;
    SnakeKeyListener snakeKeyListener;

    Color activeColor = Color.GREEN;
    Color inactiveColor = Color.GRAY;
    Color backColor = Color.BLACK;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();

        startMenu = new StartMenu(this);
        generator = new ASCIIWorldGenerator();
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void start() {

        snakeKeyListener = new SnakeKeyListener();
        addKeyListener(new GameKeyListener(this));

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

            switch ((GameType) config.getConfigChoice("gameType")) {
                case SELF_ESTEEM_SNAKE:
                    game = new SnakeGame();
                    break;
                case BOOKWORM:
                    game = new BookwormGame();
                    break;
            }

            game.applyConfig(config);
            game.initGame();
            snakeKeyListener.setSnake(game.getSnake());
            addKeyListener(snakeKeyListener);
            game.setView(this);
            gameState = GameState.PLAYING;
            game.startGameAndPlayTillDeath();
            removeKeyListener(snakeKeyListener);
            if(!game.wasInterrupted()) {
                gameState = GameState.GAME_OVER;
                refreshView();
            }
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
                g.setColor(backColor);
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
        g.setColor(backColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        int leftOffset = 10;
        int topOffset = 180;
        int lineHeight = g.getFontMetrics().getHeight();

        g.setFont(new Font("Monospaced", Font.PLAIN, 13));
        g.setColor(activeColor);

        Graphics2D gTitle = (Graphics2D) g.create();
        gTitle.scale(0.9, 0.75);
        drawTitle(gTitle, leftOffset, 0, lineHeight);

        int i = 0;
        for (ConfigItem item : startMenu.getConfigItems()) {
            drawConfigItem(item, (Graphics2D) g.create(), leftOffset, topOffset, startMenu.getCurrentConfigItem() == i);
            topOffset += lineHeight * 1.5;
            i++;
        }

    }

    private void drawTitle(Graphics2D g, int leftOffset, int topOffset, int lineHeight) {
        for (String line : startMenu.getTitle().split("\n")) {
            g.drawString(line, leftOffset, topOffset += lineHeight);
        }
        topOffset += lineHeight;
    }


    private void drawConfigItem(ConfigItem configItem, Graphics2D g, int leftOffset, int topOffset, boolean active) {
        String itemLabelText = configItem.getLabelText();
        drawConfigItemLabel(itemLabelText, g, leftOffset, topOffset, active);
        leftOffset += g.getFontMetrics().getWidths()[0] * (itemLabelText.length() + 4);

        int choiceNumber = 0;
        for (ConfigItemChoice choice : configItem.getChoices()) {

            choiceNumber++;
            String choiceLabelText = choice.getLabelText();

            if (choice == configItem.getSelectedChoice()) {
                g.setColor(activeColor);
            } else {
                g.setColor(inactiveColor);
            }

            g.drawString(choiceLabelText, leftOffset, topOffset);

            if (choiceNumber != configItem.getChoices().size()) {
                g.setColor(inactiveColor);
                leftOffset += g.getFontMetrics().getWidths()[0] * (choiceLabelText.length() + 2);
                g.drawString("/", leftOffset, topOffset);
                leftOffset += g.getFontMetrics().getWidths()[0] * 2;
            }
        }
    }

    private void drawConfigItemLabel(String labelText, Graphics2D g, int leftOffset, int topOffset, boolean active) {
        if (active) {
            g.setColor(activeColor);
        } else {
            g.setColor(inactiveColor);
        }
        g.drawString(labelText + ": ", leftOffset, topOffset);
    }

    private void drawPlaying(Graphics2D g) {
        g.setColor(backColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        switch ((GameType) config.getConfigChoice("gameType")) {
            case SELF_ESTEEM_SNAKE:
                drawWorldString(g, generator.getWorldString(game.getWorld()));
                break;
            case BOOKWORM:
                drawWorldString(g, generator.getWorldString((BookwormWorld) game.getWorld()));
                break;
        }
    }

    private void drawGameOver(Graphics2D g) {

        g.setColor(backColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        Font font = new Font("Monospaced", Font.PLAIN, 11);
        g.setFont(font);
        g.setColor(activeColor);

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
        g.setColor(activeColor);

        for (String line : worldString.split("\n")) {
            g.drawString(line, leftOffset, topOffset += g.getFontMetrics().getHeight());
        }
    }

    @Override
    public void refreshView() {
        repaint();
    }

    public void returnToStartMenu() {
        game.interrupt();
        gameState = GameState.START_MENU;
        addKeyListener(startMenu);
        refreshView();
    }
}
