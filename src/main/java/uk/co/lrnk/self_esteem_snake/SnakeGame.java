package uk.co.lrnk.self_esteem_snake;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeGame implements ActionListener {

    World world;
    Timer stepTimer;

    public static void main(String[] args) {

        SnakeGame game = new SnakeGame();
        game.startGame();
    }

    private void startGame() {

        world = new World();
        Snake snake = new Snake();
        world.setSnake(snake);

        stepTimer = new Timer(1 * 1000, this);

        stepTimer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        world.step();
    }
}
