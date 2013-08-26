package uk.co.lrnk.self_esteem_snake;

import java.util.LinkedList;

public class BookwormSnake extends Snake {

    @Override
    protected void moveIntoEmptySpace(Space plainSpace) {
        if(plainSpace.getState() != SpaceState.EMPTY) {
            throw new RuntimeException("BookwormSnake.moveIntoEmptySpace failed. Space state was: " + plainSpace.getState());
        }
        BookwormSpace space = (BookwormSpace) plainSpace;

        snakeSpaces.addFirst(space);
        space.setState(SpaceState.SNAKE);

        keepCharactersInSnakePosition();

        snakeSpaces.peekLast().setState(SpaceState.EMPTY);
        snakeSpaces.removeLast();
    }

    private void keepCharactersInSnakePosition() {

        LinkedList<BookwormSpace> snakeSpacesCopy = (LinkedList<BookwormSpace>) snakeSpaces.clone();
        BookwormSpace segmentToShiftCharInto = snakeSpacesCopy.pop();
        while(true) {
            BookwormSpace previousSegmentSpace = snakeSpacesCopy.peekFirst();

            if(previousSegmentSpace.hasCharacter()) {
                char charOfPrevSegment = snakeSpacesCopy.peekFirst().getCharacter();
                segmentToShiftCharInto.setCharacter(charOfPrevSegment);

                snakeSpacesCopy.peekFirst().clearCharacter();

                segmentToShiftCharInto = snakeSpacesCopy.pop();
            } else {
                break;
            }
        }
    }
}
