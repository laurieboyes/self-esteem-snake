package uk.co.lrnk.self_esteem_snake;

import java.util.LinkedList;

public class Snake {

    private Direction currentDirection;
    private Direction nextStepDirection;
    private World world;
    private int startingLength = 6;
    private LinkedList<Space> snakeSpaces;

    public Snake() {
        setCurrentDirection(Direction.RIGHT);
        setNextStepDirection(Direction.RIGHT);
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

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getNextStepDirection() {
        return nextStepDirection;
    }

    public void setNextStepDirection(Direction nextStepDirection) {
        this.nextStepDirection = nextStepDirection;
    }

    public void step() {

        try {
            setCurrentDirection(getNextStepDirection());

            Space nextHeadSpace = world.getNextSpace(getHeadSpace(), getCurrentDirection());

            if(nextHeadSpace.getState() == SpaceState.SNAKE) {
                throw new GameOverHitSelfException();
            }

            snakeSpaces.addFirst(nextHeadSpace);
            getHeadSpace().setState(SpaceState.SNAKE);

            snakeSpaces.peekLast().setState(SpaceState.EMPTY);
            snakeSpaces.removeLast();

        } catch (NoNextSpaceException ex) {
            throw new GameOverHitWallException();
        }

    }

    public void tryToHeadUp() {
        if(getCurrentDirection() != Direction.DOWN) {
            setNextStepDirection(Direction.UP);
        }
    }

    public void tryToHeadDown() {
        if(getCurrentDirection() != Direction.UP) {
            setNextStepDirection(Direction.DOWN);
        }
    }

    public void tryToHeadRight() {
        if(getCurrentDirection() != Direction.LEFT) {
            setNextStepDirection(Direction.RIGHT);
        }
    }

    public void tryToHeadLeft() {
        if(getCurrentDirection() != Direction.RIGHT) {
            setNextStepDirection(Direction.LEFT);
        }
    }
    
}
