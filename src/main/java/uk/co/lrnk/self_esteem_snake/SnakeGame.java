package uk.co.lrnk.self_esteem_snake;

import uk.co.lrnk.self_esteem_snake.ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class SnakeGame {

    World world;
    Snake snake;
    GameState state;
    int stepTimeInMilliseconds = (int) (0.15 * 1000);

    public static void main(String[] args) throws InterruptedException {

        SnakeGame game = new SnakeGame();
        game.startGame();
    }

    private void startGame() throws InterruptedException {

        state = GameState.PLAYING;

        world = new World();
        snake = new Snake();
        snake.placeInWorld(world);

        GamePanel gamePanel = getGamePanel();

        while (true) {
            try {
                Thread.sleep(stepTimeInMilliseconds);
                snake.step();
                gamePanel.repaint();
            } catch (SnakeHitTheWallException ex) {
                state = GameState.GAME_OVER;
                gamePanel.repaint();
                break;
            }
        }

    }

    private GamePanel getGamePanel() {
        GamePanel gamePanel = new GamePanel(this);
        JFrame frame = new JFrame("Self Esteem Snake");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(gamePanel);
        frame.setPreferredSize(new Dimension(325, 275));
        frame.pack();
        frame.setVisible(true);
        return gamePanel;
    }

    public World getWorld() {
        return world;
    }

    public Snake getSnake() {
        return snake;
    }

    public GameState getState() {
        return state;
    }
}
