package uk.co.lrnk.self_esteem_snake.bookworm;

import uk.co.lrnk.self_esteem_snake.SnakeGame;
import uk.co.lrnk.self_esteem_snake.Space;
import uk.co.lrnk.self_esteem_snake.ui.ScoreSaver;

import java.awt.*;

public class BookwormGame extends SnakeGame {

    FoodStringFetcher fetcher = new FoodStringFetcher();

    public BookwormGame(Font viewFont) {
        fetcher = new FoodStringFetcher();
        fetcher.setViewFont(viewFont);
    }

    @Override
    public void initGame() {
        scoreSaver = new ScoreSaver();
        previousHighScore = scoreSaver.getSavedScore();

        world = new BookwormWorld(20, 12);
        ((BookwormWorld)world).setFoodString(fetcher.getFoodString());
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
