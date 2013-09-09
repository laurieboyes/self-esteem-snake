package uk.co.lrnk.self_esteem_snake.bookworm;

import uk.co.lrnk.self_esteem_snake.Space;

public class BookwormSpace extends Space {

    private Character character;

    public BookwormSpace(int x, int y) {
        super(x, y);
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public boolean hasCharacter() {
        return character != null;
    }

    public void clearCharacter(){
        character = null;
    }
}
