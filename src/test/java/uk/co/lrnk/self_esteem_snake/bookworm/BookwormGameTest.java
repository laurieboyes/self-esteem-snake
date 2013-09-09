package uk.co.lrnk.self_esteem_snake.bookworm;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static junit.framework.Assert.assertEquals;

public class BookwormGameTest {

    @Test
    public void scoreIsCalculatedAs7TimesCharacterEatenSnakeEmpty() {
        BookwormGame game = new BookwormGame();
        BookwormSnake snake = new BookwormSnake();
        BookwormWorld world = new BookwormWorld(10,10);
        snake.placeInWorld(world);
        ReflectionTestUtils.setField(game, "snake", snake);

        assertEquals(0, game.getScore());
    }

    @Test
    public void scoreIsCalculatedAs7TimesCharacterEatenSnakePartFull() {
        BookwormGame game = new BookwormGame();
        BookwormSnake snake = new BookwormSnake();
        BookwormWorld world = new BookwormWorld(10,10);
        snake.placeInWorld(world);
        ReflectionTestUtils.setField(game, "snake", snake);

        ReflectionTestUtils.setField(snake.getSnakeSpaces().get(0),"character", 'A');
        ReflectionTestUtils.setField(snake.getSnakeSpaces().get(1),"character", 'A');

        assertEquals(14, game.getScore());
    }
}
