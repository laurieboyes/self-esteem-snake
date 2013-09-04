package uk.co.lrnk.self_esteem_snake.config;

public class ConfigItemChoice {

    ConfigItemChoiceState state;

    public ConfigItemChoice(ConfigItemChoiceState state) {
        this.state = state;
    }

    public ConfigItemChoiceState getState() {
        return state;
    }

}
