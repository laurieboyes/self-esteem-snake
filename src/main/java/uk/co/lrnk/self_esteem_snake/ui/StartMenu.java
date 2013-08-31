package uk.co.lrnk.self_esteem_snake.ui;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import uk.co.lrnk.self_esteem_snake.GameInitException;

import java.io.IOException;

public class StartMenu {

    private StartMenuKeyListener startMenuKeyListener;
    private String title;

    public StartMenu(GamePanel gamePanel) {
        startMenuKeyListener = new StartMenuKeyListener(gamePanel);
        gamePanel.addKeyListener(startMenuKeyListener);
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
}
