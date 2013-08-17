package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
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
    public void testGetInitialSnakeHeadSpace() {
        World world = new World(20,12);
        Space space = world.getInitialSnakeHeadSpace();

        assertEquals(10, space.getX());
        assertEquals(11, space.getY());
    }

//    TODO clean up this mess
    @Test
    public void testGetAllSpaces() {
        World world = new World();
        int w = 4;
        int h = 7;
        int totalSpaces = w * h;


        Space[][] spaces = new Space[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                spaces[i][j] = new Space(i,j);
            }
        }

        ReflectionTestUtils.setField(world,"spaces",spaces);

        List<Space> spaceList = world.getAllSpaces();


        assertEquals(totalSpaces,spaceList.size());

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                assertTrue(spaceList.contains(spaces[i][j]));
            }
        }
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
