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

        removePreviousTailEndSpace();
    }

    private void keepCharactersInSnakePosition() {

        LinkedList<BookwormSpace> snakeSpacesCopy = (LinkedList<BookwormSpace>) snakeSpaces.clone();
        BookwormSpace segmentToShiftCharInto = snakeSpacesCopy.pop();
        while(true) {
            BookwormSpace previousSegmentSpace = snakeSpacesCopy.peekFirst();

            if(previousSegmentSpace != null && previousSegmentSpace.hasCharacter()) {
                char charOfPrevSegment = snakeSpacesCopy.peekFirst().getCharacter();
                segmentToShiftCharInto.setCharacter(charOfPrevSegment);

                snakeSpacesCopy.peekFirst().clearCharacter();

                segmentToShiftCharInto = snakeSpacesCopy.pop();
            } else {
                break;
            }
        }
    }

    protected void eatSpace(Space space) {
        super.eatSpace(space);

        boolean containsSnakeSpacesWithoutLetters = false;
        for(Space snakeSpace : snakeSpaces) {
            if(!((BookwormSpace) snakeSpace).hasCharacter()) {
                containsSnakeSpacesWithoutLetters = true;
            }
        }

        if(containsSnakeSpacesWithoutLetters) {
            removePreviousTailEndSpace();
        }
    }
}
