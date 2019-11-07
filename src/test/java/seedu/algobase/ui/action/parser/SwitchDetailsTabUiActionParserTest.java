package seedu.algobase.ui.action.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SwitchDetailsTabUiActionParserTest {
    @Test
    public void parse_nullUiAction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchDetailsTabUiActionParser().parse(null));
    }

}
