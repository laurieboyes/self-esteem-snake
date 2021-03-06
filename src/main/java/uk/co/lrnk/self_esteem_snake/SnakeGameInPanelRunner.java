package uk.co.lrnk.self_esteem_snake;

import uk.co.lrnk.self_esteem_snake.ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class SnakeGameInPanelRunner {

    public static void main(String[] args) {

        GamePanel gamePanel = createGamePanel();
        gamePanel.start();
    }

    private static GamePanel createGamePanel() {
        GamePanel gamePanel = new GamePanel();
        JFrame frame = new JFrame("Self Esteem Snake");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(gamePanel);
        frame.setPreferredSize(new Dimension(313, 230));
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        return gamePanel;
    }
}
