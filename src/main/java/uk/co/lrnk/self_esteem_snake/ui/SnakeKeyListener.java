package uk.co.lrnk.self_esteem_snake.ui;

import uk.co.lrnk.self_esteem_snake.Snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeKeyListener extends KeyAdapter {

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
}
