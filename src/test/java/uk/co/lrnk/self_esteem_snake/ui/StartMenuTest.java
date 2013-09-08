package uk.co.lrnk.self_esteem_snake.ui;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.lrnk.self_esteem_snake.config.Config;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class StartMenuTest {

    @Test
    public void testNextConfigItem() {
        StartMenu startMenu = new StartMenu(mock(GamePanel.class));

        assertEquals(0, ReflectionTestUtils.getField(startMenu, "currentConfigItem"));
        assertEquals(2, ((Config) ReflectionTestUtils.getField(startMenu, "config")).getNumberOfConfigItems());

        ReflectionTestUtils.invokeMethod(startMenu, "nextConfigItem");
        assertEquals(1, ReflectionTestUtils.getField(startMenu, "currentConfigItem"));
    }

    @Test
    public void testNextConfigItemLastItemInList() {
        StartMenu startMenu = new StartMenu(mock(GamePanel.class));

        ReflectionTestUtils.invokeMethod(startMenu, "nextConfigItem");
        assertEquals(1, ReflectionTestUtils.getField(startMenu, "currentConfigItem"));
        assertEquals(2, ((Config) ReflectionTestUtils.getField(startMenu, "config")).getNumberOfConfigItems());

        ReflectionTestUtils.invokeMethod(startMenu, "nextConfigItem");
        assertEquals(0, ReflectionTestUtils.getField(startMenu, "currentConfigItem"));
    }

    @Test
    public void testPrevConfigItem() {
        StartMenu startMenu = new StartMenu(mock(GamePanel.class));

        ReflectionTestUtils.invokeMethod(startMenu, "nextConfigItem");
        assertEquals(1, ReflectionTestUtils.getField(startMenu, "currentConfigItem"));
        assertEquals(2, ((Config) ReflectionTestUtils.getField(startMenu, "config")).getNumberOfConfigItems());

        ReflectionTestUtils.invokeMethod(startMenu, "prevConfigItem");
        assertEquals(0, ReflectionTestUtils.getField(startMenu, "currentConfigItem"));
    }

    @Test
    public void testPrevConfigItemFirstInList() {
        StartMenu startMenu = new StartMenu(mock(GamePanel.class));

        assertEquals(0, ReflectionTestUtils.getField(startMenu, "currentConfigItem"));
        assertEquals(2, ((Config) ReflectionTestUtils.getField(startMenu, "config")).getNumberOfConfigItems());

        ReflectionTestUtils.invokeMethod(startMenu, "prevConfigItem");
        assertEquals(1, ReflectionTestUtils.getField(startMenu, "currentConfigItem"));
    }
}
