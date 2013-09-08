package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.LinkedList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class BookwormSnakeTest {

    @Test
    public void testEatBookwormSpace() {
        BookwormWorld world = new BookwormWorld(10,10);

        BookwormSnake snake = new BookwormSnake();
        snake.placeInWorld(world);

        BookwormSpace space = new BookwormSpace(5,5);
        space.setCharacter('L');
        snake.eatSpace(space);

        assertEquals('L',((BookwormSpace)snake.getHeadSpace()).getCharacter());
    }

    @Test
    public void testEatenCharactersStayAtSamePositionRelativeToSnakeHead() {
        BookwormWorld world = new BookwormWorld(10,10);

        BookwormSnake snake = new BookwormSnake();
        snake.placeInWorld(world);

        BookwormSpace space = new BookwormSpace(5,5);
        space.setCharacter('L');
        snake.eatSpace(space);

        assertEquals('L',((BookwormSpace)snake.getHeadSpace()).getCharacter());

        snake.step();

        assertEquals('L', ((BookwormSpace) snake.getHeadSpace()).getCharacter());
        BookwormSpace spaceBeforeHeadSpace =((LinkedList<BookwormSpace>) ReflectionTestUtils.getField(snake,"snakeSpaces")).get(1);
        assertFalse(spaceBeforeHeadSpace.hasCharacter());

        snake.step();

        assertEquals('L',((BookwormSpace)snake.getHeadSpace()).getCharacter());
    }

    @Test
    public void testNoNullPointerWhenShiftSnakeWithNoNonCharacterSpaces() {
        BookwormWorld world = new BookwormWorld(10,10);

        BookwormSnake snake = new BookwormSnake();
        snake.placeInWorld(world);

        assertEquals(6,snake.getLength());
        for(Space space : (LinkedList<Space>)ReflectionTestUtils.getField(snake, "snakeSpaces")) {
            ((BookwormSpace) space).setCharacter(' ');
        }

        snake.step();
    }

    @Test
    public void charactersArePushedDownSnakeWhenNewCharacterEaten() {

        BookwormWorld world = new BookwormWorld(10,10);

        BookwormSnake snake = new BookwormSnake();
        snake.placeInWorld(world);

        BookwormSpace lSpace = new BookwormSpace(5,5);
        lSpace.setCharacter('L');
        snake.eatSpace(lSpace);

        assertEquals('L',((BookwormSpace)snake.getHeadSpace()).getCharacter());

        BookwormSpace aSpace = new BookwormSpace(6,5);
        aSpace.setCharacter('A');
        snake.eatSpace(aSpace);

        assertEquals('A',((BookwormSpace)snake.getHeadSpace()).getCharacter());

        BookwormSpace spaceBeforeHeadSpace =((LinkedList<BookwormSpace>) ReflectionTestUtils.getField(snake,"snakeSpaces")).get(1);
        assertEquals('L', spaceBeforeHeadSpace.getCharacter());

    }

    @Test
    public void snakeLengthStaysConstantUntilAllInitialDotsReplaced() {

        BookwormWorld world = new BookwormWorld(10,10);

        BookwormSnake snake = new BookwormSnake();
        snake.placeInWorld(world);

        assertEquals(6, snake.getStartingLength());

        for(BookwormSpace space: (LinkedList<BookwormSpace>) ReflectionTestUtils.getField(snake, "snakeSpaces")) {
            assertFalse(space.hasCharacter());
        }

        for (int i = 1; i < 7; i++) {
            BookwormSpace space = new BookwormSpace(1 + i,1);
            space.setCharacter(("" + i).charAt(0));
            snake.eatSpace(space);
        }

        assertEquals(6, snake.getLength());

        BookwormSpace space = new BookwormSpace(9,1);
        space.setCharacter('7');
        snake.eatSpace(space);

        assertEquals(7, snake.getLength());

    }
}
