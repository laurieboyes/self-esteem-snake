package uk.co.lrnk.self_esteem_snake.ui;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.lrnk.self_esteem_snake.SnakeGame;

import java.awt.*;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GamePanelTest {

    @Test
    public void testGetOffsetForCenteringTextEvenChars() {

        GamePanel gamePanel = new GamePanel(mock(SnakeGame.class));

        Graphics2D g = mock(Graphics2D.class);
        FontMetrics metrics = mock(FontMetrics.class);
        when(g.getFontMetrics()).thenReturn(metrics);
        when(metrics.getWidths()).thenReturn(new int[]{10});

        int result = ReflectionTestUtils.invokeMethod(gamePanel, "getLeftOffsetForCenteringText", g, "CLIPPING");

        assertEquals(40,result);

    }

    @Test
    public void testGetOffsetForCenteringTextOddChars() {

        GamePanel gamePanel = new GamePanel(mock(SnakeGame.class));

        Graphics2D g = mock(Graphics2D.class);
        FontMetrics metrics = mock(FontMetrics.class);
        when(g.getFontMetrics()).thenReturn(metrics);
        when(metrics.getWidths()).thenReturn(new int[]{10});

        int result = ReflectionTestUtils.invokeMethod(gamePanel, "getLeftOffsetForCenteringText", g, "CLIPPINGS");

        assertEquals(45,result);

    }

}
