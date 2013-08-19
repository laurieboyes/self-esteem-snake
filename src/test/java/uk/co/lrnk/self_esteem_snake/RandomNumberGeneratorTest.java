package uk.co.lrnk.self_esteem_snake;

import org.junit.Test;

public class RandomNumberGeneratorTest {


    @Test(expected = RuntimeException.class)
    public void testInvalidArguments() {
        RandomNumberGenerator generator = new RandomNumberGenerator();
        generator.getRandomNumber(10,5);
    }

}
