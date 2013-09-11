package uk.co.lrnk.self_esteem_snake.ui;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.lrnk.self_esteem_snake.*;
import uk.co.lrnk.self_esteem_snake.bookworm.BookwormSpace;
import uk.co.lrnk.self_esteem_snake.bookworm.BookwormWorld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ASCIIWorldGeneratorTest {

    @Test
    public void testTopBorder() {
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();
        StringBuilder sb = new StringBuilder();

        ReflectionTestUtils.invokeMethod(generator, "addTopBorder", 5, sb);

        assertEquals(" _ _ _ _ _", sb.toString());
    }

    @Test
    public void testBottomBorder() {
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();
        StringBuilder sb = new StringBuilder();

        ReflectionTestUtils.invokeMethod(generator, "addBottomBorder", 5, sb);


        assertEquals(" \u00AF \u00AF \u00AF \u00AF \u00AF", sb.toString());
    }

    @Test
    public void testRowWithPlaceholders() {
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();
        StringBuilder sb = new StringBuilder();

        int width = 5;
        int rowNumber = 2;

        ReflectionTestUtils.invokeMethod(generator, "addRowWithPlaceholders", width, rowNumber, sb);

        assertEquals("|0-2 1-2 2-2 3-2 4-2|", sb.toString());
    }

    @Test
    public void testGetPlaceholderWorld() throws IOException {
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();

        int w = 7;
        int h = 4;

        String expected = Resources.toString(Resources.getResource("uk/co/lrnk/self_esteem_snake/ui/7x4placeholder-world.txt"), Charsets.UTF_8);
        expected = normaliseLineEndings(expected);

        String placeholderWorld = ReflectionTestUtils.invokeMethod(generator, "getPlaceholderWorld", w, h);

        assertEquals(expected, placeholderWorld);

    }

    @Test
    public void testFillInWorldEmpty() {
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();
        World world = mock(World.class);

        List<Space> spaceList = new ArrayList<Space>();
        spaceList.add(new Space(2, 3));
        spaceList.add(new Space(5, 7));
        spaceList.add(new Space(8, 1));
        when(world.getAllSpaces()).thenReturn(spaceList);

        String placeHolderString = " |8-1| |5-7| |2-3| derp derp";
        String expectedResult = " | | | | | | derp derp";
        String filledInWorld = ReflectionTestUtils.invokeMethod(generator, "fillInWorld", placeHolderString, world);

        assertEquals(expectedResult, filledInWorld);
    }

    @Test
    public void testFillInWorldWithSnake() {
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();
        World world = mock(World.class);

        List<Space> spaceList = new ArrayList<Space>();
        spaceList.add(new Space(2, 3));
        Space snakeSpace = new Space(5, 7);
        snakeSpace.setState(SpaceState.SNAKE);
        spaceList.add(snakeSpace);
        when(world.getAllSpaces()).thenReturn(spaceList);

        String placeHolderString = "|2-3|5-7|";
        String expectedResult = "| |O|";
        String filledInWorld = ReflectionTestUtils.invokeMethod(generator, "fillInWorld", placeHolderString, world);

        assertEquals(expectedResult, filledInWorld);
    }

    @Test
    public void testEmptyWorld() throws IOException {

        World world = new World(20, 12);
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();

        String expected = Resources.toString(Resources.getResource("uk/co/lrnk/self_esteem_snake/ui/empty-world.txt"), Charsets.UTF_8);
        expected = normaliseLineEndings(expected);

        assertEquals(expected, generator.getWorldString(world));
    }

    @Test
    public void testFillInBookwormWorld() {
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();
        BookwormWorld world = mock(BookwormWorld.class);

        List<BookwormSpace> spaceList = new ArrayList<BookwormSpace>();

        spaceList.add(new BookwormSpace(2, 3));

        BookwormSpace snakeSpace = new BookwormSpace(5, 7);
        snakeSpace.setState(SpaceState.SNAKE);
        spaceList.add(snakeSpace);

        BookwormSpace foodSpace = new BookwormSpace(5, 8);
        foodSpace.setState(SpaceState.FOOD);
        foodSpace.setCharacter('L');
        spaceList.add(foodSpace);

        when(world.getAllBookwormSpaces()).thenReturn(spaceList);

        String placeHolderString = "|2-3|5-7|5-8|";
        String expectedResult = "| |\u2022|L|";
        String filledInWorld = ReflectionTestUtils.invokeMethod(generator, "fillInWorld", placeHolderString, world);

        assertEquals(expectedResult, filledInWorld);
    }

    @Test
    public void testReplaceSpaceCharacterWithSomethingVisible() {
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();
        BookwormWorld world = mock(BookwormWorld.class);

        List<BookwormSpace> spaceList = new ArrayList<BookwormSpace>();

        BookwormSpace foodSpace = new BookwormSpace(5, 8);
        foodSpace.setState(SpaceState.FOOD);
        foodSpace.setCharacter(' ');
        spaceList.add(foodSpace);

        when(world.getAllBookwormSpaces()).thenReturn(spaceList);

        String placeHolderString = "|5-8|";
        String expectedResult = "|\u2022|";
        String filledInWorld = ReflectionTestUtils.invokeMethod(generator, "fillInWorld", placeHolderString, world);

        assertEquals(expectedResult, filledInWorld);
    }

    private String normaliseLineEndings(String str) {
        return str.replaceAll("\r\n", "\n");
    }

}
