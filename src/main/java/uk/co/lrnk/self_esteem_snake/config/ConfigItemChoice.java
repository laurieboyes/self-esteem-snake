package uk.co.lrnk.self_esteem_snake.config;

public class ConfigItemChoice {

    ConfigItemChoiceState state;
    private String labelText;

    public ConfigItemChoice(ConfigItemChoiceState state, String labelText) {
        this.state = state;
        this.labelText = labelText;
    }

    public ConfigItemChoiceState getState() {
        return state;
    }

    public String getLabelText() {
        return labelText;
    }
}
