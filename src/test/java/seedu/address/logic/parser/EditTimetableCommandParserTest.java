package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTimetableCommand;

public class EditTimetableCommandParserTest {

    private EditTimetableCommandParser parser = new EditTimetableCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String day = " day/friday";
        String timing = " timing/0900";
        String details = " m/cs2103";
        String userInput = targetIndex.getOneBased() + day + timing + details;
        EditTimetableCommand expectedCommand = new EditTimetableCommand(targetIndex, "friday",
            "0900", "cs2103");
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}