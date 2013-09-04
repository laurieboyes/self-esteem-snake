package uk.co.lrnk.self_esteem_snake.config;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class ConfigTest {

    @Test
    public void testConstruct() {
        Config config = new Config();

        assertNotNull(config.configItemMap.get("difficulty"));
        assertNotNull(config.configItemMap.get("gameType"));

    }

    @Test
    public void testGetValidConfigChoice() {
        Config config = new Config();
        assertEquals(Difficulty.NORMAL, config.getConfigChoice("difficulty"));
    }

    @Test(expected = ConfigException.class)
    public void testGetInvalidConfigChoice() {
        Config config = new Config();
        config.getConfigChoice("arbitraryInvalidStuff");
    }

    @Test
    public void testGetDifficultyConfig() {
        Config config = new Config();

        ConfigItem difficulty = ReflectionTestUtils.invokeMethod(config, "getDifficultyConfig");

        assertEquals(2, difficulty.getChoices().size());
        assertEquals(Difficulty.NORMAL, difficulty.getChoices().get(0).getState());
        assertEquals(Difficulty.HARD, difficulty.getChoices().get(1).getState());

        assertEquals(Difficulty.NORMAL, difficulty.getSelectedChoice().getState());

    }

    @Test
    public void testGetGameTypeConfig() {
        Config config = new Config();

        ConfigItem gameType = ReflectionTestUtils.invokeMethod(config, "getGameTypeConfig");

        assertEquals(2, gameType.getChoices().size());
        assertEquals(GameType.SELF_ESTEEM_SNAKE, gameType.getChoices().get(0).getState());
        assertEquals(GameType.BOOKWORM, gameType.getChoices().get(1).getState());

        assertEquals(GameType.SELF_ESTEEM_SNAKE, gameType.getSelectedChoice().getState());

    }

}
