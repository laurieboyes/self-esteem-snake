package uk.co.lrnk.self_esteem_snake.config;

import org.junit.Test;
import uk.co.lrnk.self_esteem_snake.GameInitException;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ConfigItemTest {

    @Test
    public void testConstructWithThreeChoice() {

        ArrayList<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();
        ConfigItemChoice choice1 = new ConfigItemChoice(null,"");
        ConfigItemChoice choice2 = new ConfigItemChoice(null,"");
        ConfigItemChoice choice3 = new ConfigItemChoice(null,"");

        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);

        ConfigItem configItem = new ConfigItem(choices, "");

        assertEquals(3, configItem.getChoices().size());

        assertTrue(configItem.getChoices().contains(choice1));
        assertTrue(configItem.getChoices().contains(choice2));
        assertTrue(configItem.getChoices().contains(choice3));

        assertEquals(choice1, configItem.getSelectedChoice());
    }

    @Test
     public void testConstructWithOneChoice() {

        ArrayList<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();
        ConfigItemChoice choice1 = new ConfigItemChoice(null,"");

        choices.add(choice1);

        ConfigItem configItem = new ConfigItem(choices, "");

        assertEquals(1, configItem.getChoices().size());

        assertTrue(configItem.getChoices().contains(choice1));

        assertEquals(choice1, configItem.getSelectedChoice());
    }

    @Test(expected = GameInitException.class)
    public void testConstructWithEmptyChoiceList() {

        ArrayList<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();

        ConfigItem configItem = new ConfigItem(choices, "");

    }

    @Test
    public void testConstructSetsLabel() {
        ConfigItem item = new ConfigItem(mock(List.class), "hello");

        assertEquals("hello", item.getLabelText());
    }

    @Test
    public void testNextChoice() {

        ConfigItemChoice choice0 = new ConfigItemChoice(mock(ConfigItemChoiceState.class),"");
        ConfigItemChoice choice1 = new ConfigItemChoice(mock(ConfigItemChoiceState.class),"");
        List<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();
        choices.add(choice0);
        choices.add(choice1);

        ConfigItem configItem = new ConfigItem(choices,"");

        assertEquals(choice0, configItem.getSelectedChoice());
        configItem.nextChoice();
        assertEquals(choice1, configItem.getSelectedChoice());
    }

    @Test
    public void testNextChoiceLastInList() {

        ConfigItemChoice choice0 = new ConfigItemChoice(mock(ConfigItemChoiceState.class),"");
        ConfigItemChoice choice1 = new ConfigItemChoice(mock(ConfigItemChoiceState.class),"");
        List<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();
        choices.add(choice0);
        choices.add(choice1);

        ConfigItem configItem = new ConfigItem(choices,"");

        configItem.nextChoice();
        assertEquals(choice1, configItem.getSelectedChoice());
        configItem.nextChoice();
        assertEquals(choice0, configItem.getSelectedChoice());
    }

    @Test
    public void testPrevChoice() {

        ConfigItemChoice choice0 = new ConfigItemChoice(mock(ConfigItemChoiceState.class),"");
        ConfigItemChoice choice1 = new ConfigItemChoice(mock(ConfigItemChoiceState.class),"");
        List<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();
        choices.add(choice0);
        choices.add(choice1);

        ConfigItem configItem = new ConfigItem(choices,"");

        configItem.nextChoice();
        assertEquals(choice1, configItem.getSelectedChoice());
        configItem.prevChoice();
        assertEquals(choice0, configItem.getSelectedChoice());
    }

    @Test
    public void testPrevChoiceFirstInList() {

        ConfigItemChoice choice0 = new ConfigItemChoice(mock(ConfigItemChoiceState.class),"");
        ConfigItemChoice choice1 = new ConfigItemChoice(mock(ConfigItemChoiceState.class),"");
        List<ConfigItemChoice> choices = new ArrayList<ConfigItemChoice>();
        choices.add(choice0);
        choices.add(choice1);

        ConfigItem configItem = new ConfigItem(choices,"");

        assertEquals(choice0, configItem.getSelectedChoice());
        configItem.prevChoice();
        assertEquals(choice1, configItem.getSelectedChoice());
    }
}
