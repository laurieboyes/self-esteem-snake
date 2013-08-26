package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
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
        assertEquals(6, snake.getStartingLength());

        World world = new World(10,10);

        assertEquals(world.getAllSpaces().size(), world.getEmptySpaces().size());
        snake.placeInWorld(world);
        assertEquals(world.getAllSpaces().size() - 6, world.getEmptySpaces().size());

        assertEquals(SpaceState.SNAKE, snake.getHeadSpace().getState());

        List<Space> tailSpaces = (List<Space>) ReflectionTestUtils.getField(snake, "snakeSpaces");

        assertEquals(6,tailSpaces.size());

        for(Space space : tailSpaces) {
            assertEquals(SpaceState.SNAKE,space.getState());
        }
    }

    @Test
    public void testPlaceSpaceInMinimumSize8By2World(){
        World world = new World(8,2);
        Snake snake = new Snake();
        assertEquals(6, snake.getStartingLength());

        assertEquals(world.getAllSpaces().size(), world.getEmptySpaces().size());
        snake.placeInWorld(world);
        assertEquals(world.getAllSpaces().size() - 6, world.getEmptySpaces().size());

        assertEquals(SpaceState.SNAKE, world.getSpace(6,1).getState());
        assertEquals(SpaceState.SNAKE, world.getSpace(5,1).getState());
        assertEquals(SpaceState.SNAKE, world.getSpace(4,1).getState());
        assertEquals(SpaceState.SNAKE, world.getSpace(3,1).getState());
        assertEquals(SpaceState.SNAKE, world.getSpace(2,1).getState());
        assertEquals(SpaceState.SNAKE, world.getSpace(1,1).getState());

    }

    @Test
    public void testGetHeadSpace() {
        Snake snake = new Snake();
        World world = new World(10,10);
        snake.placeInWorld(world);

        assertEquals(6,snake.getHeadSpace().getX());
        assertEquals(9,snake.getHeadSpace().getY());

    }

    @Test
    public void testStepSnakeSpaces() {
        Snake snake = new Snake();
        World world = new World(20,12);
        snake.placeInWorld(world);

        int originalSnakeHeadX = 6;
        int originalSnakeHeadY = 11;
        int originalSnakeTailEndX = 1;

        assertEquals(originalSnakeHeadX, snake.getHeadSpace().getX());
        assertEquals(originalSnakeHeadY, snake.getHeadSpace().getY());
        List<Space> snakeSpaces = (List<Space>) ReflectionTestUtils.getField(snake, "snakeSpaces");
        assertEquals(originalSnakeHeadX, snakeSpaces.size());

        snake.step();

        int expectedNewSnakeHeadX = originalSnakeHeadX + 1;
        int expectedNewSnakeHeadY = originalSnakeHeadY;
        int expectedSnakeTailEndX = originalSnakeTailEndX + 1;

        assertEquals(originalSnakeHeadX, snakeSpaces.size());
        assertEquals(world.getSpace(expectedNewSnakeHeadX, expectedNewSnakeHeadY),snake.getHeadSpace());
        assertEquals(SpaceState.SNAKE, world.getSpace(expectedNewSnakeHeadX,expectedNewSnakeHeadY).getState());
        assertEquals(SpaceState.SNAKE, world.getSpace(expectedSnakeTailEndX, expectedNewSnakeHeadY).getState());
        assertEquals(SpaceState.EMPTY, world.getSpace(expectedSnakeTailEndX - 1,expectedNewSnakeHeadY).getState());

    }

    @Test
    public void testStepDirection() {
        Snake snake = new Snake();
        snake.placeInWorld(new World(20, 12));
        snake.setCurrentDirection(Direction.RIGHT);
        snake.setNextStepDirection(Direction.RIGHT);

        snake.setNextStepDirection(Direction.UP);

        assertEquals(Direction.RIGHT,snake.getCurrentDirection());

        snake.step();

        assertEquals(Direction.UP,snake.getCurrentDirection());

    }

    @Test
    public void testStepHitAWall() {
        Snake snake = new Snake();
        World world = new World(20,10);
        snake.placeInWorld(world);

        assertEquals(6,snake.getHeadSpace().getX());

        int numberToStepToGetJustInFrontOfWall = 20 - 7;

        for (int i = 0; i < numberToStepToGetJustInFrontOfWall; i++) {
            snake.step();
        }

        assertEquals(19,snake.getHeadSpace().getX());

        try{
            snake.step();
            fail();
        } catch (GameOverHitWallException ex) {
        }
    }

    @Test
    public void testStepHitSelf() {
        Snake snake = new Snake();
        World world = new World(20,20);
        snake.placeInWorld(world);

        assertEquals(6, snake.getHeadSpace().getX());

        snake.tryToHeadUp();
        snake.step();
        snake.tryToHeadLeft();
        snake.step();
        snake.tryToHeadDown();

        assertEquals(5, snake.getHeadSpace().getX());
        assertEquals(18, snake.getHeadSpace().getY());
        assertEquals(SpaceState.SNAKE, world.getNextSpace(snake.getHeadSpace(),snake.getNextStepDirection()).getState());

        try{
            snake.step();
            fail();
        } catch (GameOverHitSelfException ex) {
        }
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

    @Test
    public void growWhenEatFood() {
        World world = new World(20,20);
        Snake snake = new Snake();
        snake.placeInWorld(world);

        assertEquals(6,((List<Space>)ReflectionTestUtils.getField(snake,"snakeSpaces")).size());
        assertEquals(394,world.getEmptySpaces().size());


        assertEquals(Direction.RIGHT,snake.getNextStepDirection());
        Space nextSpace = world.getNextSpace(snake.getHeadSpace(),Direction.RIGHT);

        assertEquals(SpaceState.EMPTY, nextSpace.getState());
        nextSpace.setState(SpaceState.FOOD);
        assertEquals(SpaceState.FOOD, nextSpace.getState());

        snake.step();

        assertEquals(SpaceState.SNAKE, nextSpace.getState());
        assertEquals(7,((List<Space>)ReflectionTestUtils.getField(snake,"snakeSpaces")).size());
        assertEquals(393,world.getEmptySpaces().size());
    }

    @Test
    public void testGetLength() {
        Snake snake = new Snake();
        LinkedList<Space> snakeSpaces = new LinkedList<Space>(Arrays.asList(new Space[]{new Space(0, 0), new Space(0, 1), new Space(0, 2)}));
        assertEquals(3,snakeSpaces.size());

        ReflectionTestUtils.setField(snake,"snakeSpaces", snakeSpaces);

        assertEquals(3,snake.getLength());
    }

    @Test(expected = RuntimeException.class)
    public void testMoveIntoEmptySpaceEmptyCheck() {
        World world = new World(10,10);
        Snake snake = new Snake();
        snake.placeInWorld(world);

        Space fullSpace = new Space(1,1);
        fullSpace.setState(SpaceState.FOOD);

        snake.moveIntoEmptySpace(fullSpace);
    }
}
