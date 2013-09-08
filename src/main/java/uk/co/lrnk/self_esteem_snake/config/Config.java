package uk.co.lrnk.self_esteem_snake.config;

import java.util.*;

public class Config {

    Map<String,ConfigItem> configItemMap = new HashMap<String,ConfigItem>();

    public Config() {
        configItemMap.put("difficulty", getDifficultyConfig());
        configItemMap.put("gameType", getGameTypeConfig());
    }

    public ConfigItemChoiceState getConfigChoice(String configItemKey) {
        if(!configItemMap.containsKey(configItemKey)) {
            throw new ConfigException("Tried to get a config item that didn't exist: " + configItemKey);
        }
        return configItemMap.get(configItemKey).getSelectedChoice().getState();
    }

    private ConfigItem getGameTypeConfig() {

        List<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();

        choices.add(new ConfigItemChoice(GameType.SELF_ESTEEM_SNAKE, "Snake"));
        choices.add(new ConfigItemChoice(GameType.BOOKWORM, "Bookworm"));

        return new ConfigItem(choices, "GAME TYPE");
    }

    private ConfigItem getDifficultyConfig() {

        List<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();

        choices.add(new ConfigItemChoice(Difficulty.NORMAL, "Normal"));
        choices.add(new ConfigItemChoice(Difficulty.HARD, "Hard"));

        return new ConfigItem(choices, "DIFFICULTY");
    }

    public int getNumberOfConfigItems() {
        return configItemMap.size();
    }

    public List<ConfigItem> getConfigItems() {
        return new ArrayList<ConfigItem>(configItemMap.values());
    }


}
