package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.lrnk.self_esteem_snake.ui.ScoreSaver;
import uk.co.lrnk.self_esteem_snake.ui.SnakeGameView;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SnakeGameTest {

    @Test
    public void scoreIsCalculatedAs7TimesAddedLength() {
        SnakeGame game = new SnakeGame();
        Snake mockSnake = mock(Snake.class);
        ReflectionTestUtils.setField(game, "snake", mockSnake);

        when(mockSnake.getStartingLength()).thenReturn(6);
        when(mockSnake.getLength()).thenReturn(10);
        assertEquals(28, game.getScore());
    }

    @Test
    public void newHighScoresAreSaved() {
        SnakeGame game = new SnakeGame();
        Snake mockSnake = mock(Snake.class);
        ReflectionTestUtils.setField(game, "snake", mockSnake);
        SnakeGameView mockView = mock(SnakeGameView.class);
        game.setView(mockView);

        doThrow(new GameOverException()).when(mockSnake).step();

        int oldHighScore = 100;
        ReflectionTestUtils.setField(game, "previousHighScore", oldHighScore);

        int newHighScore = 140;

        when(mockSnake.getStartingLength()).thenReturn(0);
        when(mockSnake.getLength()).thenReturn(newHighScore / 7);

        ScoreSaver mockScoreSaver = mock(ScoreSaver.class);
        ReflectionTestUtils.setField(game, "scoreSaver", mockScoreSaver);

        game.startGameAndPlayTillDeath();

        verify(mockScoreSaver).saveScore(140);

    }

    @Test
    public void nonHighScoresAreNotSaved() {
        SnakeGame game = new SnakeGame();
        Snake mockSnake = mock(Snake.class);
        ReflectionTestUtils.setField(game, "snake", mockSnake);
        SnakeGameView mockView = mock(SnakeGameView.class);
        game.setView(mockView);

        doThrow(new GameOverException()).when(mockSnake).step();

        int oldHighScore = 140;
        ReflectionTestUtils.setField(game, "previousHighScore", oldHighScore);

        int newHighScore = 100;

        when(mockSnake.getStartingLength()).thenReturn(0);
        when(mockSnake.getLength()).thenReturn(newHighScore / 7);

        ScoreSaver mockScoreSaver = mock(ScoreSaver.class);
        ReflectionTestUtils.setField(game, "scoreSaver", mockScoreSaver);

        game.startGameAndPlayTillDeath();

        verify(mockScoreSaver, never()).saveScore(anyInt());

    }
}
