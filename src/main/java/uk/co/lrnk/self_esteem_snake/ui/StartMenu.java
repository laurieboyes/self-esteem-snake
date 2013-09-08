package uk.co.lrnk.self_esteem_snake.ui;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import uk.co.lrnk.self_esteem_snake.GameInitException;
import uk.co.lrnk.self_esteem_snake.config.Config;
import uk.co.lrnk.self_esteem_snake.config.ConfigItem;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

public class StartMenu extends KeyAdapter {

    private String title;
    private GamePanel gamePanel;
    private Config config;

    private int currentConfigItem;

    public StartMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gamePanel.addKeyListener(this);

        config = new Config();

        title = readTitleFromFile();
    }

    private String readTitleFromFile() {
        try {
            return Resources.toString(Resources.getResource("uk/co/lrnk/self_esteem_snake/ui/title.txt"), Charsets.UTF_8);
        } catch (IOException e) {
            throw new GameInitException("Failed to load start menu title: " + e.getMessage());
        }
    }

    public String getTitle() {
        return title;
    }

    public int getCurrentConfigItem() {
        return currentConfigItem;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                gamePanel.removeKeyListener(this);
                gamePanel.setConfig(config);
                gamePanel.gameState = GameState.READY_TO_START_GAME;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_DOWN:
                nextConfigItem();
                gamePanel.refreshView();
                break;
            case KeyEvent.VK_UP:
                prevConfigItem();
                gamePanel.refreshView();
                break;
            case KeyEvent.VK_RIGHT:
                nextConfigItemChoice();
                gamePanel.refreshView();
                break;
            case KeyEvent.VK_LEFT:
                prevConfigItemChoice();
                gamePanel.refreshView();
                break;
        }
    }

    private void nextConfigItem() {
        currentConfigItem = (currentConfigItem + 1) % config.getNumberOfConfigItems();
    }

    private void prevConfigItem() {
        currentConfigItem = Math.abs(currentConfigItem - 1) % config.getNumberOfConfigItems();
    }

    private void nextConfigItemChoice() {
        ConfigItem configItem = config.getConfigItems().get(currentConfigItem);
        configItem.nextChoice();
    }

    private void prevConfigItemChoice() {
        ConfigItem configItem = config.getConfigItems().get(currentConfigItem);
        configItem.prevChoice();
    }

    public List<ConfigItem> getConfigItems(){
        return config.getConfigItems();
    }
}
