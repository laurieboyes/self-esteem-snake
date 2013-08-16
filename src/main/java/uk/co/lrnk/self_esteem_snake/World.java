package uk.co.lrnk.self_esteem_snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {

    private Space[][] spaces;
    private int heightYLength = 12;
    private int widthXLength = 20;


    private Snake snake;

//    todo remove?
    public World(){
        heightYLength = 12;
        widthXLength = 20;
        initBoard(widthXLength, heightYLength);
    }

    public World(int widthXLength, int heightYLength) {
        initBoard(widthXLength, heightYLength);
    }

    public int getHeight() {
        return heightYLength;
    }

    public int getWidth() {
        return widthXLength;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    private void initBoard(int xLength, int yLength) {

        spaces = new Space[xLength][yLength];

        for (int iY = 0; iY < yLength; iY++) {
            for (int iX = 0; iX < xLength; iX++) {
                spaces[iX][iY] = new Space(iX, iY);
            }
        }
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

    public void step(){

        snake.setHeadSpace(getNextSpace(snake.getHeadSpace(), snake.getDirection()));

    }

    public Space getNextSpace(Space space, Direction direction) {
        try{
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
             throw new SnakeHitTheWallException();
        }

    }

}
