package uk.co.lrnk.self_esteem_snake;

public class RandomNumberGenerator {

    public int getRandomNumber(int min, int max) {
        if (min > max) {
            throw new RuntimeException("Invalid arguments passed to RandomNumberGenerator.getRandomNumber: min=" + min + " max=" + max);
        }
        return min + (int) (Math.random() * ((max - min) + 1));
    }

}
