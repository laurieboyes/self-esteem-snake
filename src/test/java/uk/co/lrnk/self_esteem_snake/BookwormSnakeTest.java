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
    public void charactersArePushedDownSnakeWhenNewCharacterEaton() {

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

}
