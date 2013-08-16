package uk.co.lrnk.self_esteem_snake;

public class Snake {

    Direction direction;
    Space headSpace;

    public Snake() {
        setDirection(Direction.RIGHT);
    }

    public void setHeadSpace(Space headSpace) {
        this.headSpace = headSpace;
    }

    public Space getHeadSpace() {
        return headSpace;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
