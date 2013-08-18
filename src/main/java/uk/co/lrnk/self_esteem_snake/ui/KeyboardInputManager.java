package uk.co.lrnk.self_esteem_snake.ui;

import uk.co.lrnk.self_esteem_snake.Snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputManager implements KeyListener {

    Snake snake;

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                snake.tryToHeadUp();
                break;
            case KeyEvent.VK_DOWN:
                snake.tryToHeadDown();
                break;
            case KeyEvent.VK_RIGHT:
                snake.tryToHeadRight();
                break;
            case KeyEvent.VK_LEFT:
                snake.tryToHeadLeft();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
