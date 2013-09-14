package uk.co.lrnk.self_esteem_snake;

import uk.co.lrnk.self_esteem_snake.config.Config;
import uk.co.lrnk.self_esteem_snake.config.Difficulty;
import uk.co.lrnk.self_esteem_snake.ui.ScoreSaver;
import uk.co.lrnk.self_esteem_snake.ui.SnakeGameView;

public class SnakeGame {

    protected World world;
    protected Snake snake;
    private SnakeGameView view;
    protected int previousHighScore;
    protected ScoreSaver scoreSaver;
    private Difficulty difficulty = Difficulty.NORMAL;
    private int stepTimeInMilliseconds = (int) (0.15 * 1000);
    private boolean interrupt = false;
    private boolean paused = false;

    public void setView(SnakeGameView view) {
        this.view = view;
    }

    public void initGame() {

        scoreSaver = new ScoreSaver();
        previousHighScore = scoreSaver.getSavedScore();

        world = new World(20, 12);
        snake = new Snake();
        snake.placeInWorld(world);
    }

    public void applyConfig(Config config) {
        difficulty = (Difficulty)config.getConfigChoice("difficulty");

        switch (difficulty) {
            case NORMAL:
                stepTimeInMilliseconds = (int) (0.15 * 1000);
                break;
            case HARD:
                stepTimeInMilliseconds = (int) (0.10 * 1000);
                break;
        }
    }

    public void startGameAndPlayTillDeath() {

        while (true) {
            try {
                if(interrupt) {
                    break;
                }
                if(paused) {
                    Thread.sleep(10);
                    continue;
                }
                step();
                view.refreshView();
                Thread.sleep(stepTimeInMilliseconds);
            } catch (GameOverException ex) {
                int currentScore = getScore();
                if(previousHighScore < currentScore) {
                    scoreSaver.saveScore(currentScore);
                }
                view.refreshView();
                break;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }

    }

    private void step() {
        getSnake().step();
        getWorld().step();
    }

    public World getWorld() {
        return world;
    }

    public Snake getSnake() {
        return snake;
    }

    public int getScore(){
        return 7 * (snake.getLength() - snake.getStartingLength());
    }

    public int getPreviousHighScore() {
        return previousHighScore;
    }

    public void interrupt() {
        interrupt = true;
    }

    public boolean wasInterrupted() {
        return interrupt;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

}
