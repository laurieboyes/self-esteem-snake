package uk.co.lrnk.self_esteem_snake.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameKeyListener extends KeyAdapter {

    private GamePanel panel;

    public GameKeyListener(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (panel.gameState == GameState.GAME_OVER) {
                    panel.gameState = GameState.READY_TO_START_GAME;
                }
                break;
            case KeyEvent.VK_P:
                panel.togglePauseGame();
                break;
            case KeyEvent.VK_ESCAPE:
                panel.returnToStartMenu();
        }
    }
}
