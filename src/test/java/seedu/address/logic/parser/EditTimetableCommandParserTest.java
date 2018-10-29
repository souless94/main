package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DAY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DAY_AND_TIMING;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIMING;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_UNIQUE_PREFIX_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIMING_AND_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIMING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDIT_TIMETABLE_COMMAND_WITHOUT_DETAILS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDIT_TIMETABLE_COMMAND_WITH_DETAILS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMING;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTimetableCommand;

public class EditTimetableCommandParserTest {

    private EditTimetableCommandParser parser = new EditTimetableCommandParser();

    @Test
    public void parseCompulsoryFieldMissingFailure() {
        String expectedMessage = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, EditTimetableCommand.MESSAGE_USAGE);
        String targetIndex = String.valueOf(INDEX_SECOND.getOneBased()) + " ";

        //missing both day and timing prefix
        assertParseFailure(parser, targetIndex
                + PREFIX_DETAILS + VALID_DETAILS,
            expectedMessage);

        // missing timing prefix
        assertParseFailure(parser,
            targetIndex
                + PREFIX_DAY + VALID_DAY + " "
                + PREFIX_DETAILS + VALID_DETAILS,
            expectedMessage);

        // missing day prefix
        assertParseFailure(parser,
            targetIndex
                + PREFIX_TIMING + VALID_TIMING + " "
                + PREFIX_DETAILS + VALID_DETAILS,
            expectedMessage);

        // missing timing prefix and details prefix
        assertParseFailure(parser,
            targetIndex
                + PREFIX_DAY + VALID_DAY + " "
                + VALID_DETAILS,
            expectedMessage);

        // missing day prefix and details prefix
        assertParseFailure(parser,
            targetIndex
                + PREFIX_TIMING + VALID_TIMING + " "
                + VALID_DETAILS,
            expectedMessage);

        // missing all prefixes for editTimetableCommand
        assertParseFailure(parser,
            targetIndex
                + VALID_DAY + " "
                + VALID_TIMING + " "
                + VALID_DETAILS,
            expectedMessage);

        //missing both day and timing prefix
        assertParseFailure(parser, targetIndex,
            expectedMessage);

        //missing index
        assertParseFailure(parser, PREFIX_DAY
                + VALID_DAY + " "
                + PREFIX_TIMING + VALID_TIMING + " "
                + PREFIX_DETAILS + VALID_DETAILS,
            expectedMessage);

    }

    @Test
    public void parseInvalidTimingFailure() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + INVALID_TIMING_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_TIMING);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parseInvalidDayFailure() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + INVALID_DAY_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_DAY);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parseInvalidTimingAndInvalidDayFailure() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + INVALID_TIMING_AND_DAY_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_DAY_AND_TIMING);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parseAllFieldsSpecifiedSuccess() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + VALID_EDIT_TIMETABLE_COMMAND_WITH_DETAILS;
        EditTimetableCommand expectedCommand = new EditTimetableCommand(targetIndex, VALID_DAY,
            VALID_TIMING, VALID_DETAILS);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseNoDetailsSuccess() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + VALID_EDIT_TIMETABLE_COMMAND_WITHOUT_DETAILS;
        EditTimetableCommand expectedCommand = new EditTimetableCommand(targetIndex, "friday",
            "0900", " ");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseEditTimetableWithTwoInputsFailure() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + VALID_EDIT_TIMETABLE_COMMAND_WITH_DETAILS
            + " " + PREFIX_DAY + VALID_DAY;
        String expectedMessage = MESSAGE_NOT_UNIQUE_PREFIX_INPUT;
        assertParseFailure(parser, userInput, expectedMessage);

    }
}
