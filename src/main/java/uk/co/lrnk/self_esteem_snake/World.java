package uk.co.lrnk.self_esteem_snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {

    protected Space[][] spaces;
    private int numRows;
    private int numColumns;
    protected RandomNumberGenerator randomNumberGenerator;

    public World(int numColumns, int numRows) {

        if(numColumns < 8) {
            throw new GameInitException("World width " + numColumns + " too small. Must be at least 8");
        }
        if(numRows < 2) {
            throw new GameInitException("World height " + numRows + " too small. Must be at least 2");
        }

        this.numColumns = numColumns;
        this.numRows = numRows;
        randomNumberGenerator = new RandomNumberGenerator();
        initBoard(numColumns, numRows);
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    protected void initBoard(int xLength, int yLength) {

        spaces = new Space[xLength][yLength];

        for (int iY = 0; iY < yLength; iY++) {
            for (int iX = 0; iX < xLength; iX++) {
                spaces[iX][iY] = new Space(iX, iY);
            }
        }
    }

    public void step(){
        int numberOfFoodSpace = 0;
        for(Space space : getAllSpaces()) {
            if(space.getState() == SpaceState.FOOD) {
                numberOfFoodSpace++;
            }
        }

        if(numberOfFoodSpace == 0) {
            placeFoodInWorld();
        }
    }

    public void placeFoodInWorld() {

        List<Space> emptySpaces = getEmptySpaces();
        if(emptySpaces.isEmpty()) {
            throw new WorldFullException();
        }

        Space foodSpace = emptySpaces.get(randomNumberGenerator.getRandomNumber(0, emptySpaces.size() - 1));
        foodSpace.setState(SpaceState.FOOD);
    }

    public Space getInitialSnakeHeadSpace() {
        return getSpace(numColumns / 2, numRows - 1);
    }

    public Space getSpace(int x, int y) {
        return spaces[x][y];
    }

    public List<Space> getAllSpaces() {
        List<Space> spaceList = new ArrayList<Space>();

        for (Space[] row : spaces) {
            spaceList.addAll(Arrays.asList(row));
        }
        return spaceList;
    }

    public Space getNextSpace(Space space, Direction direction) {
        try {
            switch (direction) {
                case RIGHT:
                    return getSpace(space.getX() + 1, space.getY());
                case LEFT:
                    return getSpace(space.getX() - 1, space.getY());
                case UP:
                    return getSpace(space.getX(), space.getY() - 1);
                case DOWN:
                    return getSpace(space.getX(), space.getY() + 1);
                default:
                    throw new RuntimeException();
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new NoNextSpaceException();
        }

    }

    public List<Space> getEmptySpaces() {
        List<Space> emptySpaces = new ArrayList<Space>();
        for(Space space : getAllSpaces()) {
            if(space.getState() == SpaceState.EMPTY) {
                emptySpaces.add(space);
            }
        }
        return emptySpaces;
    }

}
