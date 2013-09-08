package uk.co.lrnk.self_esteem_snake;

import uk.co.lrnk.self_esteem_snake.config.Config;
import uk.co.lrnk.self_esteem_snake.config.Difficulty;
import uk.co.lrnk.self_esteem_snake.config.GameType;
import uk.co.lrnk.self_esteem_snake.ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class SnakeGameInPanelRunner {

    public static void main(String[] args) {

        GamePanel gamePanel = createGamePanel();

        Config config = new Config();
        config.setConfigChoice("gameType", GameType.SELF_ESTEEM_SNAKE);
        config.setConfigChoice("difficulty", Difficulty.NORMAL);

        gamePanel.setConfig(config);

        gamePanel.start();
    }

    private static GamePanel createGamePanel() {
        GamePanel gamePanel = new GamePanel();
        JFrame frame = new JFrame("Self Esteem Snake");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(gamePanel);
        frame.setPreferredSize(new Dimension(313, 260));
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        return gamePanel;
    }
}
