package uk.co.lrnk.self_esteem_snake;

import uk.co.lrnk.self_esteem_snake.ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class SnakeGame {

    World world;
    int stepTimeInMilliseconds = (int) (0.15 * 1000);

    public static void main(String[] args) throws InterruptedException {

        SnakeGame game = new SnakeGame();
        game.startGame();
    }

    private void startGame() throws InterruptedException {

        world = new World();
        Snake snake = new Snake();
        snake.placeInWorld(world);

        GamePanel gamePanel = getGamePanel(world, snake);

        while (true) {
            try {
                Thread.sleep(stepTimeInMilliseconds);
                snake.step();
                gamePanel.repaint();
            } catch (SnakeHitTheWallException ex) {
                System.out.println("\n\nGAME OVER");
                break;
            }
        }

    }

    private GamePanel getGamePanel(World world, Snake snake) {
        GamePanel gamePanel = new GamePanel(world, snake);
        JFrame frame = new JFrame("Self Esteem Snake");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(gamePanel);
        frame.setPreferredSize(new Dimension(325, 275));
        frame.pack();
        frame.setVisible(true);
        return gamePanel;
    }


}
