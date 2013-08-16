package uk.co.lrnk.self_esteem_snake.ui;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.lrnk.self_esteem_snake.Space;
import uk.co.lrnk.self_esteem_snake.World;

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

        assertEquals("|---------|", sb.toString());
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
        String placeholderWorld = ReflectionTestUtils.invokeMethod(generator, "getPlaceholderWorld", w, h);

        assertEquals(expected,placeholderWorld);

    }

    @Test
    public void testFillInWorld() {
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();
        World world = mock(World.class);

        List<Space> spaceList = new ArrayList<Space>();
        spaceList.add(new Space(2,3));
        spaceList.add(new Space(5,7));
        spaceList.add(new Space(8,1));
        when(world.getAllSpaces()).thenReturn(spaceList);

        String placeHolderString = " |8-1| |5-7| |2-3| derp derp";
        String expectedResult = " | | | | | | derp derp";
        String filledInWorld = ReflectionTestUtils.invokeMethod(generator, "fillInWorld", placeHolderString, world);


        assertEquals(expectedResult,filledInWorld);

    }

    @Test
    public void testEmptyWorld() throws IOException {

        World world = new World();
        ASCIIWorldGenerator generator = new ASCIIWorldGenerator();

        String expected = Resources.toString(Resources.getResource("uk/co/lrnk/self_esteem_snake/ui/empty-world.txt"), Charsets.UTF_8);

        assertEquals(expected, generator.getWorldString(world));
    }

}
