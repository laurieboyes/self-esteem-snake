package uk.co.lrnk.self_esteem_snake.ui;

import uk.co.lrnk.self_esteem_snake.Direction;
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
                tryToHeadUp();
                break;
            case KeyEvent.VK_DOWN:
                tryToHeadDown();
                break;
            case KeyEvent.VK_RIGHT:
                tryToHeadRight();
                break;
            case KeyEvent.VK_LEFT:
                tryToHeadLeft();
                break;
        }
    }

    private void tryToHeadUp() {
        if(snake.getDirection() != Direction.DOWN) {
            snake.setDirection(Direction.UP);
        }
    }

    private void tryToHeadDown() {
        if(snake.getDirection() != Direction.UP) {
            snake.setDirection(Direction.DOWN);
        }
    }

    private void tryToHeadRight() {
        if(snake.getDirection() != Direction.LEFT) {
            snake.setDirection(Direction.RIGHT);
        }
    }

    private void tryToHeadLeft() {
        if(snake.getDirection() != Direction.RIGHT) {
            snake.setDirection(Direction.LEFT);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
