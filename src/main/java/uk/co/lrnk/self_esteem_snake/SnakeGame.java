package uk.co.lrnk.self_esteem_snake;

import uk.co.lrnk.self_esteem_snake.ui.ASCIIWorldGenerator;

public class SnakeGame  {

    World world;
    int stepTimeInMilliseconds = 1 * 1000;
    ASCIIWorldGenerator generator;

    public static void main(String[] args) throws InterruptedException {

        SnakeGame game = new SnakeGame();
        game.startGame();
    }

    private void startGame() throws InterruptedException {

        world = new World();
        Snake snake = new Snake();
        snake.placeInWorld(world);

        generator = new ASCIIWorldGenerator();


        while(true) {
            try{
                Thread.sleep(stepTimeInMilliseconds);
                snake.step();
                System.out.println("\n\n\n" + generator.getWorldString(world));
            } catch (SnakeHitTheWallException ex) {
                System.out.println("\n\nGAME OVER");
                break;
            }
        }

    }


}
