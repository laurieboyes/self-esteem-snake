package uk.co.lrnk.self_esteem_snake;

import uk.co.lrnk.self_esteem_snake.ui.ScoreSaver;

public class BookwormGame extends SnakeGame {

    @Override
    public void initGame() {
        scoreSaver = new ScoreSaver();
        previousHighScore = scoreSaver.getSavedScore();

        world = new BookwormWorld(20, 12);
        snake = new Snake();
        snake.placeInWorld(world);
    }
}
