package uk.co.lrnk.self_esteem_snake;

public class BookwormSpace extends Space {

    private char character;

    public BookwormSpace(int x, int y) {
        super(x, y);
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }
}
