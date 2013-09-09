package uk.co.lrnk.self_esteem_snake.bookworm;

import uk.co.lrnk.self_esteem_snake.SnakeGame;
import uk.co.lrnk.self_esteem_snake.Space;
import uk.co.lrnk.self_esteem_snake.ui.ScoreSaver;

public class BookwormGame extends SnakeGame {

    @Override
    public void initGame() {
        scoreSaver = new ScoreSaver();
        previousHighScore = scoreSaver.getSavedScore();

        world = new BookwormWorld(20, 12);
        snake = new BookwormSnake();
        snake.placeInWorld(world);
    }

    @Override
    public int getScore() {
        int numLettersInSnake = 0;
        for(Space space : snake.getSnakeSpaces()) {
            if(((BookwormSpace)space).hasCharacter()) {
                numLettersInSnake++;
            }
        }

        return 7 * numLettersInSnake;
    }
}
