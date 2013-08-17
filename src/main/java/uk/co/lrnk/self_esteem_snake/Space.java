package uk.co.lrnk.self_esteem_snake;

public class Space {

    private int x;
    private int y;
    private SpaceState state;

    public Space(int x, int y) {
        this.x = x;
        this.y = y;
        state = SpaceState.EMPTY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public SpaceState getState() {
        return state;
    }

    public void setState(SpaceState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Space{" +
                "x=" + x +
                ", y=" + y +
                ", state=" + state +
                '}';
    }
}
