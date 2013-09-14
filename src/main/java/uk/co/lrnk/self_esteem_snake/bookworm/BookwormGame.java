package uk.co.lrnk.self_esteem_snake.bookworm;

import uk.co.lrnk.self_esteem_snake.SnakeGame;
import uk.co.lrnk.self_esteem_snake.Space;
import uk.co.lrnk.self_esteem_snake.ScoreSaver;

import java.awt.*;
import java.io.IOException;

public class BookwormGame extends SnakeGame {

    FoodStringFetcher fetcher = new FoodStringFetcher();
    boolean foodStringIsErrorMessage;

    public BookwormGame(Font viewFont) {
        fetcher = new FoodStringFetcher();
        fetcher.setViewFont(viewFont);
    }

    @Override
    public void initGame() {
        scoreSaver = new ScoreSaver();
        previousHighScore = scoreSaver.getSavedScore();

        world = new BookwormWorld(20, 12);

        try {
            ((BookwormWorld)world).setFoodString(fetcher.getFoodString());
        } catch (IOException e) {
            ((BookwormWorld)world).setFoodString("Couldn't get hold of a Wikipedia article. It's most likely you're not connected to the internet. Sorry!");
            foodStringIsErrorMessage = true;
        }


        snake = new BookwormSnake();
        snake.placeInWorld(world);
    }

    @Override
    public int getScore() {
        return 7 * getNumberOfCharacterEaten();
    }

    public int getNumberOfCharacterEaten() {
        int numLettersInSnake = 0;
        for(Space space : snake.getSnakeSpaces()) {
            if(((BookwormSpace)space).hasCharacter()) {
                numLettersInSnake++;
            }
        }

        return numLettersInSnake;
    }

    public String getFoodStringEntire() {
        return ((BookwormWorld) world).getFoodString();
    }

    public String getFoodStringEaten() {
        String foodString = ((BookwormWorld) world).getFoodString();
        return foodString.substring(0, getNumberOfCharacterEaten());
    }

    public boolean foodStringIsErrorMessage() {
        return foodStringIsErrorMessage;
    }

    @Override
    protected void saveScore(int score) {
//        do nothing
    }
}
