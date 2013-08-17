package uk.co.lrnk.self_esteem_snake;

import java.util.LinkedList;

public class Snake {

    private Direction direction;
    private World world;
    private int startingLength = 6;
    private LinkedList<Space> snakeSpaces;

    public Snake() {
        setDirection(Direction.RIGHT);
    }

    public void placeInWorld(World world) {
        this.world = world;
        Space headSpace = world.getInitialSnakeHeadSpace();

        snakeSpaces = new LinkedList<Space>();
        snakeSpaces.add(headSpace);
        headSpace.setState(SpaceState.SNAKE);

        for (int i = 1; i < startingLength; i++) {
            Space tailSpace = world.getSpace(getHeadSpace().getX() - i, getHeadSpace().getY());
            snakeSpaces.add(tailSpace);
            tailSpace.setState(SpaceState.SNAKE);
        }
    }

    public Space getHeadSpace() {
        return snakeSpaces.peekFirst();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void step() {
        snakeSpaces.addFirst(world.getNextSpace(getHeadSpace(),getDirection()));
        getHeadSpace().setState(SpaceState.SNAKE);

        snakeSpaces.peekLast().setState(SpaceState.EMPTY);
        snakeSpaces.removeLast();

    }
}
