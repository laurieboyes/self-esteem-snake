package uk.co.lrnk.self_esteem_snake.config;

import uk.co.lrnk.self_esteem_snake.GameInitException;

import java.util.List;

public class ConfigItem {

    private ConfigItemChoice selectedChoice;
    private List<ConfigItemChoice> choices;

    public ConfigItem(List<ConfigItemChoice> choices) {
        if(choices == null || choices.isEmpty()) {
            throw new GameInitException("Attempt to create Config Item with no choices");
        }

        this.choices = choices;
        selectedChoice = choices.get(0);
    }

    public List<ConfigItemChoice> getChoices() {
        return choices;
    }

    public ConfigItemChoice getSelectedChoice() {
        return selectedChoice;
    }
}
