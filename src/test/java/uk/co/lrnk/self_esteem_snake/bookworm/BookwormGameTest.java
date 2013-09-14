package uk.co.lrnk.self_esteem_snake.bookworm;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.lrnk.self_esteem_snake.Space;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookwormGameTest {

    @Test
    public void scoreIsCalculatedAs7TimesCharacterEatenSnakeEmpty() {
        BookwormGame game = new BookwormGame(null);
        BookwormSnake snake = new BookwormSnake();
        BookwormWorld world = new BookwormWorld(10,10);
        snake.placeInWorld(world);
        ReflectionTestUtils.setField(game, "snake", snake);

        assertEquals(0, game.getScore());
    }

    @Test
    public void scoreIsCalculatedAs7TimesCharacterEatenSnakePartFull() {
        BookwormGame game = new BookwormGame(null);
        BookwormSnake snake = new BookwormSnake();
        BookwormWorld world = new BookwormWorld(10,10);
        snake.placeInWorld(world);
        ReflectionTestUtils.setField(game, "snake", snake);

        ReflectionTestUtils.setField(snake.getSnakeSpaces().get(0),"character", 'A');
        ReflectionTestUtils.setField(snake.getSnakeSpaces().get(1),"character", 'A');

        assertEquals(14, game.getScore());
    }

    @Test
    public void testGetFoodStringFull() throws IOException {
        BookwormGame game = new BookwormGame(null);
        String foodString = "test food string";

        FoodStringFetcher mockFetcher = mock(FoodStringFetcher.class);
        when(mockFetcher.getFoodString()).thenReturn(foodString);
        game.fetcher = mockFetcher;

        game.initGame();
        assertEquals(foodString, game.getFoodStringEntire());
    }

    @Test
    public void testGetFoodStringEaten() throws IOException {
        String foodString = "12345678";
        int charsEaten = 4;
        String foodEatenString = "1234";

        assertEquals(foodEatenString, getMockGame(foodString, charsEaten).getFoodStringEaten());
    }


    @Test
    public void testGetFoodStringEatenNoCharsEaten() throws IOException {
        String foodString = "12345678";
        int charsEaten = 0;
        String foodEatenString = "";

        assertEquals(foodEatenString, getMockGame(foodString, charsEaten).getFoodStringEaten());
    }

    private BookwormGame getMockGame(String foodString, int charsEaten) throws IOException {

        BookwormGame game = new BookwormGame(null);

        List<Space> mockSpaces = new ArrayList<Space>();

        for (int i = 0; i < charsEaten; i++) {
            BookwormSpace mockSpace = mock(BookwormSpace.class);
            when(mockSpace.hasCharacter()).thenReturn(true);
            mockSpaces.add(mockSpace);
        }

        BookwormSnake mockSnake = mock(BookwormSnake.class);
        when(mockSnake.getSnakeSpaces()).thenReturn(mockSpaces);
        FoodStringFetcher mockFetcher = mock(FoodStringFetcher.class);
        when(mockFetcher.getFoodString()).thenReturn(foodString);
        game.fetcher = mockFetcher;

        game.initGame();
        ReflectionTestUtils.setField(game, "snake", mockSnake);

        return game;
    }
}
