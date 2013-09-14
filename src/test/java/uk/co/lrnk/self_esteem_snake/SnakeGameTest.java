package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.lrnk.self_esteem_snake.config.Config;
import uk.co.lrnk.self_esteem_snake.config.ConfigItemChoice;
import uk.co.lrnk.self_esteem_snake.config.Difficulty;
import uk.co.lrnk.self_esteem_snake.ui.SnakeGameView;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SnakeGameTest {

    @Test
    public void onNormalDifficultyScoreIsCalculatedAs7TimesAddedLength() {
        SnakeGame game = new SnakeGame();
        ReflectionTestUtils.setField(game, "difficulty", Difficulty.NORMAL);
        Snake mockSnake = mock(Snake.class);
        ReflectionTestUtils.setField(game, "snake", mockSnake);

        when(mockSnake.getStartingLength()).thenReturn(6);
        when(mockSnake.getLength()).thenReturn(10);
        assertEquals(12, game.getScore());
    }

    @Test
    public void onHardDifficultyScoreIsCalculatedAs7TimesAddedLength() {
        SnakeGame game = new SnakeGame();
        ReflectionTestUtils.setField(game, "difficulty", Difficulty.HARD);
        Snake mockSnake = mock(Snake.class);
        ReflectionTestUtils.setField(game, "snake", mockSnake);

        when(mockSnake.getStartingLength()).thenReturn(6);
        when(mockSnake.getLength()).thenReturn(10);
        assertEquals(28, game.getScore());
    }

    @Test
    public void newHighScoresAreSaved() {
        SnakeGame game = new SnakeGame();
        ReflectionTestUtils.setField(game, "difficulty", Difficulty.HARD);
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
        ReflectionTestUtils.setField(game, "difficulty", Difficulty.HARD);
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

    @Test
    public void testApplyConfig() {

        Config config = new Config();

        int indexOfDifficultyConfigItem = 1;
        ConfigItemChoice choice = config.getConfigItems().get(indexOfDifficultyConfigItem).getSelectedChoice();
        ReflectionTestUtils.setField(choice,"state", Difficulty.HARD);

        SnakeGame game = new SnakeGame();

        assertEquals(Difficulty.NORMAL, ReflectionTestUtils.getField(game, "difficulty"));

        game.applyConfig(config);

        assertEquals(Difficulty.HARD, ReflectionTestUtils.getField(game, "difficulty"));

    }
}
