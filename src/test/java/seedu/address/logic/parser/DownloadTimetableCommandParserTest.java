package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.logic.commands.DownloadTimetableCommand;

public class DownloadTimetableCommandParserTest {

    private DownloadTimetableCommandParser parser = new DownloadTimetableCommandParser();


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadTimetableCommand.MESSAGE_USAGE);
        //missing index
        assertParseFailure(parser, "random",
            expectedMessage);

    }

    @Test
    public void parse_downloadTimetable_success() {
        String targetIndex = INDEX_SECOND.getOneBased() + " ";
        String userInput = targetIndex;
        DownloadTimetableCommand downloadTimetableCommand = new DownloadTimetableCommand(
            INDEX_SECOND);
        assertParseSuccess(parser, userInput, downloadTimetableCommand);
    }
}
