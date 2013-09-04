package uk.co.lrnk.self_esteem_snake.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    todo temporary? currently used only for testing config
    public ConfigItemChoiceState setConfigChoice(String configItemKey, ConfigItemChoiceState state) {
        return configItemMap.get(configItemKey).getSelectedChoice().state = state;
    }

    private ConfigItem getGameTypeConfig() {

        List<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();

        choices.add(new ConfigItemChoice(GameType.SELF_ESTEEM_SNAKE));
        choices.add(new ConfigItemChoice(GameType.BOOKWORM));

        return new ConfigItem(choices);
    }

    private ConfigItem getDifficultyConfig() {

        List<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();

        choices.add(new ConfigItemChoice(Difficulty.NORMAL));
        choices.add(new ConfigItemChoice(Difficulty.HARD));

        return new ConfigItem(choices);
    }


}
