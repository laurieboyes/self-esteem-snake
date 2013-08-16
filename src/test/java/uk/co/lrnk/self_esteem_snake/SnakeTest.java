package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class SnakeTest {

    @Test
    public void testConstructSnake(){
        Snake snake = new Snake();

        assertEquals(Direction.RIGHT, getField(snake, "direction"));
    }
}
