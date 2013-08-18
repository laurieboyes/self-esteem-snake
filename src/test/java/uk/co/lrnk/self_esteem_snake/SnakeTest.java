package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SnakeTest {

    @Test
    public void testConstructSnake(){
        Snake snake = new Snake();

        assertEquals(Direction.RIGHT, snake.getCurrentDirection());
        assertEquals(Direction.RIGHT, snake.getNextStepDirection());
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
    public void testStepSnakeSpaces() {
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

    @Test
    public void testStepDirection() {
        Snake snake = new Snake();
        snake.placeInWorld(new World());
        snake.setCurrentDirection(Direction.RIGHT);
        snake.setNextStepDirection(Direction.RIGHT);

        snake.setNextStepDirection(Direction.UP);

        assertEquals(Direction.RIGHT,snake.getCurrentDirection());

        snake.step();

        assertEquals(Direction.UP,snake.getCurrentDirection());

    }

    @Test
     public void testTryToHeadUpSuccess(){

        Snake snake = getSnakeGoing(Direction.RIGHT);
        snake.tryToHeadUp();

        assertEquals(Direction.UP,snake.getNextStepDirection());
        assertEquals(Direction.RIGHT,snake.getCurrentDirection());
    }

    @Test
    public void testTryToHeadUpFail(){

        Snake snake = getSnakeGoing(Direction.DOWN);
        snake.tryToHeadUp();

        assertEquals(Direction.DOWN,snake.getNextStepDirection());
        assertEquals(Direction.DOWN,snake.getCurrentDirection());
    }

    @Test
    public void testTryToHeadDownSuccess(){

        Snake snake = getSnakeGoing(Direction.RIGHT);
        snake.tryToHeadDown();

        assertEquals(Direction.DOWN,snake.getNextStepDirection());
        assertEquals(Direction.RIGHT,snake.getCurrentDirection());
    }

    @Test
    public void testTryToHeadDownFail(){

        Snake snake = getSnakeGoing(Direction.UP);
        snake.tryToHeadDown();

        assertEquals(Direction.UP,snake.getNextStepDirection());
        assertEquals(Direction.UP,snake.getCurrentDirection());
    }

    @Test
    public void testTryToHeadLeftSuccess(){

        Snake snake = getSnakeGoing(Direction.UP);
        snake.tryToHeadLeft();

        assertEquals(Direction.LEFT,snake.getNextStepDirection());
        assertEquals(Direction.UP,snake.getCurrentDirection());
    }

    @Test
    public void testTryToHeadLeftFail(){

        Snake snake = getSnakeGoing(Direction.RIGHT);
        snake.tryToHeadLeft();

        assertEquals(Direction.RIGHT,snake.getNextStepDirection());
        assertEquals(Direction.RIGHT,snake.getCurrentDirection());
    }

    @Test
    public void testTryToHeadRightSuccess(){

        Snake snake = getSnakeGoing(Direction.UP);
        snake.tryToHeadRight();

        assertEquals(Direction.RIGHT,snake.getNextStepDirection());
        assertEquals(Direction.UP,snake.getCurrentDirection());
    }

    @Test
    public void testTryToHeadRightFail(){

        Snake snake = getSnakeGoing(Direction.LEFT);
        snake.tryToHeadRight();

        assertEquals(Direction.LEFT,snake.getNextStepDirection());
        assertEquals(Direction.LEFT,snake.getCurrentDirection());
    }


    private Snake getSnakeGoing(Direction direction) {
        Snake snake = new Snake();
        snake.setCurrentDirection(direction);
        snake.setNextStepDirection(direction);
        return snake;
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
