package uk.co.lrnk.self_esteem_snake;

import java.util.ArrayList;
import java.util.List;

public class BookwormWorld extends World {

//    temp
    String foodText = "Hello, world!";
    int foodCharsEaten = 0;

    public BookwormWorld(int numColumns, int numRows) {
        super(numColumns, numRows);
    }

    @Override
    protected void initBoard(int xLength, int yLength) {

        spaces = new Space[xLength][yLength];

        for (int iY = 0; iY < yLength; iY++) {
            for (int iX = 0; iX < xLength; iX++) {
                spaces[iX][iY] = new BookwormSpace(iX, iY);
            }
        }
    }

    @Override
    public void placeFoodInWorld() {

        List<Space> emptySpaces = getEmptySpaces();
        if(emptySpaces.isEmpty()) {
            throw new WorldFullException();
        }

        BookwormSpace foodSpace = (BookwormSpace) emptySpaces.get(randomNumberGenerator.getRandomNumber(0, emptySpaces.size() - 1));
        foodSpace.setState(SpaceState.FOOD);

        try {
            foodSpace.setCharacter(foodText.charAt(foodCharsEaten++));
        } catch (StringIndexOutOfBoundsException e) {
            throw new GameOverRanOutOfFoodException();
        }
    }

    public List<BookwormSpace> getAllBookwormSpaces() {
        List<BookwormSpace> spaceList = new ArrayList<BookwormSpace>();

        for (Space[] row : spaces) {
            for(Space space : row) {
                spaceList.add((BookwormSpace) space);
            }
        }
        return spaceList;
    }

    public void setFoodText(String foodText) {
        this.foodText = foodText;
    }

}
