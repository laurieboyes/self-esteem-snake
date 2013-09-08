package uk.co.lrnk.self_esteem_snake.config;

import uk.co.lrnk.self_esteem_snake.GameInitException;

import java.util.List;

public class ConfigItem {

    private ConfigItemChoice selectedChoice;
    private List<ConfigItemChoice> choices;
    private String labelText;

    public ConfigItem(List<ConfigItemChoice> choices, String labelText) {
        if(choices == null || choices.isEmpty()) {
            throw new GameInitException("Attempt to create Config Item with no choices");
        }

        this.choices = choices;
        selectedChoice = choices.get(0);

        this.labelText = labelText;
    }

    public List<ConfigItemChoice> getChoices() {
        return choices;
    }

    public ConfigItemChoice getSelectedChoice() {
        return selectedChoice;
    }

    public String getLabelText() {
        return labelText;
    }

    public void nextChoice(){
        int selectedChoiceNumber = choices.indexOf(getSelectedChoice());
        selectedChoice = choices.get((selectedChoiceNumber + 1) % choices.size());
    }

    public void prevChoice(){
        int selectedChoiceNumber = choices.indexOf(getSelectedChoice());
        selectedChoice = choices.get(Math.abs(selectedChoiceNumber - 1) % choices.size());
    }

}
