package uk.co.lrnk.self_esteem_snake.bookworm;

import org.junit.Test;
import uk.co.lrnk.self_esteem_snake.GameOverRanOutOfFoodException;
import uk.co.lrnk.self_esteem_snake.RandomNumberGenerator;
import uk.co.lrnk.self_esteem_snake.SpaceState;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookwormWorldTest {

    @Test
    public void testFoodInWorldString() {
        BookwormWorld world = new BookwormWorld(10,10);

        world.setFoodString("Some test food");

        RandomNumberGenerator mockRandom = mock(RandomNumberGenerator.class);
        world.setRandomNumberGenerator(mockRandom);

        when(mockRandom.getRandomNumber(anyInt(), anyInt())).thenReturn(10);

        BookwormSpace randomlyChosenFoodSpace = (BookwormSpace) world.getEmptySpaces().get(10);

        assertEquals(SpaceState.EMPTY, randomlyChosenFoodSpace.getState());

        world.placeFoodInWorld();
        assertEquals(SpaceState.FOOD, randomlyChosenFoodSpace.getState());
        assertEquals('S', randomlyChosenFoodSpace.getCharacter());

        randomlyChosenFoodSpace = (BookwormSpace) world.getEmptySpaces().get(10);
        world.placeFoodInWorld();
        assertEquals(SpaceState.FOOD, randomlyChosenFoodSpace.getState());
        assertEquals('o', randomlyChosenFoodSpace.getCharacter());

        randomlyChosenFoodSpace = (BookwormSpace) world.getEmptySpaces().get(10);
        world.placeFoodInWorld();
        assertEquals(SpaceState.FOOD, randomlyChosenFoodSpace.getState());
        assertEquals('m', randomlyChosenFoodSpace.getCharacter());

        randomlyChosenFoodSpace = (BookwormSpace) world.getEmptySpaces().get(10);
        world.placeFoodInWorld();
        assertEquals(SpaceState.FOOD, randomlyChosenFoodSpace.getState());
        assertEquals('e', randomlyChosenFoodSpace.getCharacter());

        randomlyChosenFoodSpace = (BookwormSpace) world.getEmptySpaces().get(10);
        world.placeFoodInWorld();
        assertEquals(SpaceState.FOOD, randomlyChosenFoodSpace.getState());
        assertEquals(' ', randomlyChosenFoodSpace.getCharacter());

        randomlyChosenFoodSpace = (BookwormSpace) world.getEmptySpaces().get(10);
        world.placeFoodInWorld();
        assertEquals(SpaceState.FOOD, randomlyChosenFoodSpace.getState());
        assertEquals('t', randomlyChosenFoodSpace.getCharacter());
    }


    @Test
    public void testGetAllBookwormSpaces() {
        BookwormWorld world = new BookwormWorld(8,3);
        int w = 8;
        int h = 3;
        int totalSpaces = w * h;

        List<BookwormSpace> spaceList = world.getAllBookwormSpaces();

        assertEquals(totalSpaces,spaceList.size());
    }

    @Test
    public void exceptionWhenStringFinished() {
        BookwormWorld world = new BookwormWorld(10,10);

        world.setFoodString("123");

        RandomNumberGenerator mockRandom = mock(RandomNumberGenerator.class);
        world.setRandomNumberGenerator(mockRandom);

        when(mockRandom.getRandomNumber(anyInt(), anyInt())).thenReturn(0);

        BookwormSpace randomlyChosenFoodSpace;

        randomlyChosenFoodSpace = (BookwormSpace) world.getEmptySpaces().get(0);
        world.placeFoodInWorld();
        assertEquals(SpaceState.FOOD, randomlyChosenFoodSpace.getState());
        assertEquals('1', randomlyChosenFoodSpace.getCharacter());

        randomlyChosenFoodSpace = (BookwormSpace) world.getEmptySpaces().get(0);
        world.placeFoodInWorld();
        assertEquals(SpaceState.FOOD, randomlyChosenFoodSpace.getState());
        assertEquals('2', randomlyChosenFoodSpace.getCharacter());

        randomlyChosenFoodSpace = (BookwormSpace) world.getEmptySpaces().get(0);
        world.placeFoodInWorld();
        assertEquals(SpaceState.FOOD, randomlyChosenFoodSpace.getState());
        assertEquals('3', randomlyChosenFoodSpace.getCharacter());

        try{
            world.placeFoodInWorld();
            fail();
        } catch (GameOverRanOutOfFoodException e) {
        }

    }
}
