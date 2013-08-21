package uk.co.lrnk.self_esteem_snake;

import uk.co.lrnk.self_esteem_snake.ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class SnakeGameInPanelRunner {

    public static void main(String[] args) {

        GamePanel gamePanel = getGamePanel();
        gamePanel.start();
    }

    private static GamePanel getGamePanel() {
        GamePanel gamePanel = new GamePanel();
        JFrame frame = new JFrame("Self Esteem Snake");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(gamePanel);
        frame.setPreferredSize(new Dimension(325, 270));
        frame.pack();
        frame.setVisible(true);
        return gamePanel;
    }
}
