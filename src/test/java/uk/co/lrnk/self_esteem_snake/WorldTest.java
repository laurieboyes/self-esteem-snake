package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class WorldTest {

    @Test
    public void testConstructWorld(){
        World world = new World();

        Space[][] spaces = (Space[][]) getField(world,"spaces");

        assertEquals(20, spaces.length);
        assertEquals(12, spaces[0].length);

        for(Space[] row : spaces) {
            for(Space space : row) {
                assertNotNull(space);
            }
        }

    }

    @Test
    public void moveSnakeHeadAllGood() {
        World world = new World();

        Snake snake = new Snake();
        snake.setHeadSpace(world.getSpace(10,10));
        snake.setDirection(Direction.RIGHT);

        world.setSnake(snake);

        world.step();

        assertEquals(snake.getHeadSpace(), world.getSpace(11,10));
    }

    @Test
    public void getNextSpaceAllGood(){
        World world = new World();

        Space startSpace = world.getSpace(5,10);

        assertEquals(world.getSpace(6,10), world.getNextSpace(startSpace, Direction.RIGHT));
        assertEquals(world.getSpace(4,10), world.getNextSpace(startSpace, Direction.LEFT));
        assertEquals(world.getSpace(5,9), world.getNextSpace(startSpace, Direction.UP));
        assertEquals(world.getSpace(5,11), world.getNextSpace(startSpace, Direction.DOWN));
    }

    @Test(expected = SnakeHitTheWallException.class)
    public void getNextSpaceHitsTheBottomWall(){
        World world = new World();

        Space startSpace = world.getSpace(5,11);
        world.getNextSpace(startSpace, Direction.DOWN);
    }

    @Test(expected = SnakeHitTheWallException.class)
    public void getNextSpaceHitsTheTopWall(){
        World world = new World();

        Space startSpace = world.getSpace(5,0);
        world.getNextSpace(startSpace, Direction.UP);
    }

    @Test(expected = SnakeHitTheWallException.class)
    public void getNextSpaceHitsTheLeftWall(){
        World world = new World();

        Space startSpace = world.getSpace(0,11);
        world.getNextSpace(startSpace, Direction.LEFT);
    }

    @Test(expected = SnakeHitTheWallException.class)
    public void getNextSpaceHitsTheRightWall(){
        World world = new World();

        Space startSpace = world.getSpace(19,11);
        world.getNextSpace(startSpace, Direction.RIGHT);
    }




}
