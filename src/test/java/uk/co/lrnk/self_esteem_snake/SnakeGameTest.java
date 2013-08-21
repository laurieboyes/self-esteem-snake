package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SnakeGameTest {

    @Test
    public void scoreIsCalculatedAs7TimesAddedLength() {
        SnakeGame game = new SnakeGame();
        Snake mockSnake = mock(Snake.class);
        ReflectionTestUtils.setField(game,"snake",mockSnake);

        when(mockSnake.getStartingLength()).thenReturn(6);
        when(mockSnake.getLength()).thenReturn(10);
        assertEquals(28,game.getScore());
    }
}
