package uk.co.lrnk.self_esteem_snake.ui;

import org.junit.Test;
import uk.co.lrnk.self_esteem_snake.Direction;
import uk.co.lrnk.self_esteem_snake.Snake;

import java.awt.event.KeyEvent;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KeyboardInputManagerTest {

    @Test
    public void testDirectionChanges() {
        assertKeyHasThisEffectOnDirection(Direction.RIGHT, KeyEvent.VK_UP, Direction.UP);
        assertKeyHasNoEffectOnDirection(Direction.DOWN, KeyEvent.VK_UP);

        assertKeyHasThisEffectOnDirection(Direction.RIGHT, KeyEvent.VK_DOWN, Direction.DOWN);
        assertKeyHasNoEffectOnDirection(Direction.UP, KeyEvent.VK_DOWN);

        assertKeyHasThisEffectOnDirection(Direction.UP, KeyEvent.VK_RIGHT, Direction.RIGHT);
        assertKeyHasNoEffectOnDirection(Direction.LEFT, KeyEvent.VK_RIGHT);

        assertKeyHasThisEffectOnDirection(Direction.UP, KeyEvent.VK_LEFT, Direction.LEFT);
        assertKeyHasNoEffectOnDirection(Direction.RIGHT, KeyEvent.VK_LEFT);

    }

    private void assertKeyHasThisEffectOnDirection(Direction startDirection, int keyPressed, Direction resultingDirection) {
        KeyboardInputManager manager = new KeyboardInputManager();
        Snake snake = getSnakeGoing(startDirection);
        manager.setSnake(snake);
        manager.keyPressed(getMockKeyEvent(keyPressed));
        assertEquals(resultingDirection, snake.getDirection());
    }

    private void assertKeyHasNoEffectOnDirection(Direction startDirection, int keyPressed) {
        KeyboardInputManager manager = new KeyboardInputManager();
        Snake snake = getSnakeGoing(startDirection);
        manager.setSnake(snake);
        manager.keyPressed(getMockKeyEvent(keyPressed));
        assertEquals(startDirection, snake.getDirection());
    }

    private KeyEvent getMockKeyEvent(int keyCode) {
        KeyEvent keyEvent = mock(KeyEvent.class);
        when(keyEvent.getKeyCode()).thenReturn(keyCode);
        return keyEvent;
    }

    private Snake getSnakeGoing(Direction direction) {
        Snake snake = new Snake();
        snake.setDirection(direction);
        return snake;
    }


}
