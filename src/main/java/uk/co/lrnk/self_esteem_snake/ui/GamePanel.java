package uk.co.lrnk.self_esteem_snake.ui;

import uk.co.lrnk.self_esteem_snake.Snake;
import uk.co.lrnk.self_esteem_snake.World;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    World world;
    Snake snake;
    ASCIIWorldGenerator generator;
    KeyboardInputManager inputManager;

    public GamePanel(World world, Snake snake) {
        this.world = world;
        this.snake = snake;

        generator = new ASCIIWorldGenerator();

        inputManager = new KeyboardInputManager();
        inputManager.setSnake(snake);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(inputManager);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        drawWorldString(g2, generator.getWorldString(world));

    }

    private void drawWorldString(Graphics2D g, String worldString) {
        int leftOffset = 10;
        int topOffset = 0;

        g.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g.setColor(Color.green);

        for (String line : worldString.split("\n"))
            g.drawString(line, leftOffset, topOffset += g.getFontMetrics().getHeight());
    }
}
