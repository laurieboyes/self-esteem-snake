package uk.co.lrnk.self_esteem_snake.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StartMenuKeyListener extends KeyAdapter {

    GamePanel gamePanel;

    public StartMenuKeyListener(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                gamePanel.removeKeyListener(this);
                gamePanel.gameState = GameState.READY_TO_START_GAME;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
    }
}
