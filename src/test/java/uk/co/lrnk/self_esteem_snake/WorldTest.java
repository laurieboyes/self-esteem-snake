package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static junit.framework.Assert.*;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class WorldTest {

    @Test
    public void testConstructWorld(){
        World world = new World(20, 12);

        Space[][] spaces = (Space[][]) getField(world,"spaces");

        assertEquals(20, spaces.length);
        assertEquals(12, spaces[0].length);

        for(Space[] row : spaces) {
            for(Space space : row) {
                assertNotNull(space);
            }
        }

    }

    @Test(expected = GameInitException.class)
    public void testConstructWorldWhichViolatesMinWidthOf8(){
        new World(7,10);
    }

    @Test
    public void testConstructWorldBoundaryWidth8(){
        new World(8,10);
    }

    @Test(expected = GameInitException.class)
    public void testConstructWorldWhichViolatesMinHeightOf2(){
        new World(10,1);
    }

    @Test
    public void testConstructWorldBoundaryHeight2(){
        new World(10,2);
    }

    @Test(expected = GameInitException.class)
    public void testConstructWorldWhichViolatesBothMinimums(){
        new World(1,1);
    }

//    TODO clean up this mess
    @Test
    public void testGetAllSpaces() {
        World world = new World(8,3);
        int w = 8;
        int h = 3;
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
        World world = new World(20, 12);

        Space startSpace = world.getSpace(5,10);

        assertEquals(world.getSpace(6,10), world.getNextSpace(startSpace, Direction.RIGHT));
        assertEquals(world.getSpace(4,10), world.getNextSpace(startSpace, Direction.LEFT));
        assertEquals(world.getSpace(5,9), world.getNextSpace(startSpace, Direction.UP));
        assertEquals(world.getSpace(5,11), world.getNextSpace(startSpace, Direction.DOWN));
    }

    @Test(expected = NoNextSpaceException.class)
    public void getNextSpaceHitsTheBottomWall(){
        World world = new World(20, 12);

        Space startSpace = world.getSpace(5,11);
        world.getNextSpace(startSpace, Direction.DOWN);
    }

    @Test(expected = NoNextSpaceException.class)
    public void getNextSpaceHitsTheTopWall(){
        World world = new World(20, 12);

        Space startSpace = world.getSpace(5,0);
        world.getNextSpace(startSpace, Direction.UP);
    }

    @Test(expected = NoNextSpaceException.class)
    public void getNextSpaceHitsTheLeftWall(){
        World world = new World(20, 12);

        Space startSpace = world.getSpace(0,11);
        world.getNextSpace(startSpace, Direction.LEFT);
    }

    @Test(expected = NoNextSpaceException.class)
    public void getNextSpaceHitsTheRightWall(){
        World world = new World(20, 12);

        Space startSpace = world.getSpace(19,11);
        world.getNextSpace(startSpace, Direction.RIGHT);
    }


    @Test
    public void getEmptySpacesAllEmpty() {
        World world = new World(10,10);
        List<Space> emptySpaces = world.getEmptySpaces();
        assertEquals(100,emptySpaces.size());
        for(Space space : emptySpaces) {
            assertEquals(SpaceState.EMPTY, space.getState());
        }
    }

    @Test
    public void getEmptySpacesFromWorldWithSnake() {
        World world = new World(10,10);
        for (int i = 0; i < 6; i++) {
            world.getSpace(i,3).setState(SpaceState.SNAKE);
        }
        List<Space> emptySpaces = world.getEmptySpaces();
        assertEquals(94,emptySpaces.size());
        for(Space space : emptySpaces) {
            assertEquals(SpaceState.EMPTY, space.getState());
        }
    }

    @Test
    public void getEmptySpacesFromFullWorld() {
        World world = new World(10,10);
        for(Space space : world.getAllSpaces()) {
            space.setState(SpaceState.SNAKE);
        }
        List<Space> emptySpaces = world.getEmptySpaces();
        assertEquals(0,emptySpaces.size());
    }

    @Test(expected = WorldFullException.class)
    public void placeFoodInWorldFullWorld() {
        World world = new World(10,10);
        for(Space space : world.getAllSpaces()) {
            space.setState(SpaceState.SNAKE);
        }
        world.placeFoodInWorld();
    }

    @Test
    public void testStepAddNewFood(){
        World world = getTenByTenWorldWithSnake();
        for(Space space : world.getAllSpaces()) {
            if(space.getState() == SpaceState.FOOD) {
                fail();
            }
        }

        world.step();
        int numberOfFoodSpace = 0;
        for(Space space : world.getAllSpaces()) {
            if(space.getState() == SpaceState.FOOD) {
                numberOfFoodSpace++;
            }
        }
        assertEquals(1, numberOfFoodSpace);
    }


    private World getTenByTenWorldWithSnake() {
        World world = new World(10,10);
        for (int i = 0; i < 6; i++) {
            world.getSpace(i,3).setState(SpaceState.SNAKE);
        }
        return world;
    }
}
