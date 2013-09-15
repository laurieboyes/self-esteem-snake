package uk.co.lrnk.self_esteem_snake.ui;

import com.google.common.io.Resources;
import org.apache.commons.lang.WordUtils;
import uk.co.lrnk.self_esteem_snake.GameInitException;
import uk.co.lrnk.self_esteem_snake.SnakeGame;
import uk.co.lrnk.self_esteem_snake.bookworm.BookwormGame;
import uk.co.lrnk.self_esteem_snake.bookworm.BookwormWorld;
import uk.co.lrnk.self_esteem_snake.config.Config;
import uk.co.lrnk.self_esteem_snake.config.ConfigItem;
import uk.co.lrnk.self_esteem_snake.config.ConfigItemChoice;
import uk.co.lrnk.self_esteem_snake.config.GameType;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements SnakeGameView {

    Config config;

    SnakeGame game;
    GameState gameState = GameState.START_MENU;

    StartMenu startMenu;
    ASCIIWorldGenerator generator;
    SnakeKeyListener snakeKeyListener;

    Font font;

    Color activeColor = Color.GREEN;
    Color inactiveColor = Color.GRAY;
    Color backColor = Color.BLACK;

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();

        try {
            Font fontFromFile = Font.createFont(Font.TRUETYPE_FONT, Resources.getResource("uk/co/lrnk/self_esteem_snake/ui/LucidaTypewriterRegular.ttf").openStream());
            font = fontFromFile.deriveFont(Font.PLAIN, 11);
        } catch (Exception e) {
            throw new GameInitException("Couldn't load font from classpath: " + e.getMessage());
        }

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
            refreshView();

            switch ((GameType) config.getConfigChoice("gameType")) {
                case SELF_ESTEEM_SNAKE:
                    game = new SnakeGame();
                    break;
                case BOOKWORM:
                    game = new BookwormGame(font);
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
            g.setFont(font);
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
                drawLoadingScreen(g);
                break;
            case PLAYING:
                drawPlaying(g);
                break;
            case GAME_OVER:
                drawGameOver(g);
                break;
            case PAUSED:
                drawPausedScreen(g);
                break;
        }
    }

    private void drawStartMenu(Graphics2D g) {
        g.setColor(backColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        int leftOffset = 10;
        int topOffset = 160;
        int lineHeight = g.getFontMetrics().getHeight();

        g = (Graphics2D) g.create();
        g.setFont(font.deriveFont(13f));
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
                leftOffset += g.getFontMetrics().getWidths()[0] * (choiceLabelText.length() + 1);
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

    private void drawLoadingScreen(Graphics2D g) {
        g.setColor(backColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(activeColor);

        String loadingMessage = "LOADING";

        int drawAtX = getCenteredStringX(g, font, loadingMessage);
        int drawAtY = getCenteredStringY(g, font, loadingMessage);

        g.drawString(loadingMessage, drawAtX, drawAtY);
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

        g.setColor(activeColor);

        switch ((GameType) config.getConfigChoice("gameType")) {
            case SELF_ESTEEM_SNAKE:
                drawGameOverSnake(g);
                break;
            case BOOKWORM:
                drawGameOverBookworm(g);
                break;
        }
    }

    private void drawGameOverSnake(Graphics2D g) {
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

    private void drawGameOverBookworm(Graphics2D g) {
        BookwormGame bookwormGame = (BookwormGame)game;

        if(bookwormGame.getNumberOfCharacterEaten() > 0) {

            String foodString;

            if(!bookwormGame.foodStringIsErrorMessage()) {
                foodString = bookwormGame.getFoodStringEaten();
            } else {
                foodString = bookwormGame.getFoodStringEntire();
            }

            String[] lines = WordUtils.wrap(foodString, 38).split("\r\n");

            for (int i = 0; i < lines.length; i++) {
                int x = getCenteredStringX(g, font, lines[i]);
                int y = getStringInLineListY(g, font, i, lines.length);
                g.drawString(lines[i], x, y);
            }

        } else {

            String nothingEatenString = "Your bookworm died hungry";

            int x = getCenteredStringX(g, font, nothingEatenString);
            int y = getCenteredStringY(g, font, nothingEatenString);
            g.drawString(nothingEatenString, x, y);
        }
    }

    private void drawPausedScreen(Graphics2D g) {
        g.setColor(backColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        String pausedText = "PAUSED";

        int x = getCenteredStringX(g, font, pausedText);
        int y = getCenteredStringY(g, font, pausedText);

        g.setColor(activeColor);
        g.drawString(pausedText, x, y);

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

    private int getStringInLineListY(Graphics2D g, Font f, int lineNumber, int numLines) {
        FontMetrics fm = g.getFontMetrics(f);

        int lineHeight = (int) (fm.getHeight() * 1.2);
        int allLinesHeight = lineHeight * numLines;

        int panelHeight = this.getHeight();

        int allLinesY = (panelHeight - allLinesHeight) / 2 + fm.getAscent();

        return allLinesY + (lineHeight * lineNumber);
    }

    private void drawWorldString(Graphics2D g, String worldString) {
        int leftOffset = 10;
        int topOffset = 0;

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

    public void togglePauseGame() {
        switch (gameState) {
            case PAUSED:
                game.resume();
                gameState = GameState.PLAYING;
                addKeyListener(snakeKeyListener);
                refreshView();
                break;
            case PLAYING:
                game.pause();
                gameState = GameState.PAUSED;
                removeKeyListener(snakeKeyListener);
                refreshView();
                break;

        }
    }
}
