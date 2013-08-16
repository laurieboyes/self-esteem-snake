package uk.co.lrnk.self_esteem_snake;

public class World {

    private Space[][] spaces;

    private Snake snake;

    public World(){

        int boardHeightYLength = 12;
        int boardWidthXLength = 20;

        initBoard(boardWidthXLength, boardHeightYLength);

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
