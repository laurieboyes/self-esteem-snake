package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class SnakeTest {

    @Test
    public void testConstructSnake(){
        Snake snake = new Snake();
        assertEquals(Direction.RIGHT, getField(snake, "direction"));
    }

    @Test
    public void testPlaceSnakeInWorld() {
        Snake snake = new Snake();
        assertEquals(6, ReflectionTestUtils.getField(snake,"startingLength"));
        World mockWorld = getMockWorld();

        snake.placeInWorld(mockWorld);

        assertEquals(SpaceState.SNAKE, snake.getHeadSpace().getState());

        List<Space> tailSpaces = (List<Space>) ReflectionTestUtils.getField(snake, "snakeSpaces");

        assertEquals(6,tailSpaces.size());

        for(Space space : tailSpaces) {
            assertEquals(SpaceState.SNAKE,space.getState());
        }
    }

    @Test
    public void testGetHeadSpace() {
        Snake snake = new Snake();
        World mockWorld = getMockWorld();
        snake.placeInWorld(mockWorld);

        assertEquals(10,snake.getHeadSpace().getX());
        assertEquals(10,snake.getHeadSpace().getY());

    }

    @Test
    public void testStepAllGood() {
        Snake snake = new Snake();
        World world = new World(20,20);
        snake.placeInWorld(world);

        assertEquals(10, snake.getHeadSpace().getX());
        assertEquals(11, snake.getHeadSpace().getY());

        List<Space> snakeSpaces = (List<Space>) ReflectionTestUtils.getField(snake, "snakeSpaces");
        assertEquals(6, snakeSpaces.size());

        snake.step();

        assertEquals(6, snakeSpaces.size());
        assertEquals(world.getSpace(11, 11),snake.getHeadSpace());
        assertEquals(SpaceState.SNAKE, world.getSpace(11,11).getState());
        assertEquals(SpaceState.EMPTY, world.getSpace(4,11).getState());

    }

    private World getMockWorld() {
        World mockWorld = mock(World.class);
        Space headSpace = new Space(10,10);

        for (int i = 1; i < 7; i++) {
            Space space = new Space(10 - i, 10);
            when(mockWorld.getSpace(10 - i, 10)).thenReturn(space);
        }

        when(mockWorld.getInitialSnakeHeadSpace()).thenReturn(headSpace);
        return mockWorld;
    }
}
