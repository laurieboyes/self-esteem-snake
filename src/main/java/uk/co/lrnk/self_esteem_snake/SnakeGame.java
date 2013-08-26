package uk.co.lrnk.self_esteem_snake;

import uk.co.lrnk.self_esteem_snake.ui.ScoreSaver;
import uk.co.lrnk.self_esteem_snake.ui.SnakeGameView;

public class SnakeGame {

    protected World world;
    protected Snake snake;
    private GameState state = GameState.PLAYING;
    private SnakeGameView view;
    protected int previousHighScore;
    protected ScoreSaver scoreSaver;

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

    public void startGameAndPlayTillDeath() {
        int stepTimeInMilliseconds = (int) (0.15 * 1000);

        state = GameState.PLAYING;
        while (true) {
            try {
                Thread.sleep(stepTimeInMilliseconds);
                step();
                view.refreshView();
            } catch (GameOverException ex) {
                state = GameState.GAME_OVER;
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

    public GameState getState() {
        return state;
    }

    public int getScore(){
        return 7 * (snake.getLength() - snake.getStartingLength());
    }

    public int getPreviousHighScore() {
        return previousHighScore;
    }
}
