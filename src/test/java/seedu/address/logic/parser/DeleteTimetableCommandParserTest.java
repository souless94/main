package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTimetableCommand;

public class DeleteTimetableCommandParserTest {

    private DeleteTimetableCommandParser parser = new DeleteTimetableCommandParser();


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimetableCommand.MESSAGE_USAGE);
        BOB.getTimetable().downloadTimetableAsCsv();
        boolean doesFileExists = new File(BOB.getStoredLocation()).exists();
        assertTrue(doesFileExists);
        //missing index
        assertParseFailure(parser, "random",
            expectedMessage);

    }

    @Test
    public void parse_deleteTimetable_success() {
        String targetIndex = INDEX_SECOND.getOneBased() + " ";
        BOB.getTimetable().downloadTimetableAsCsv();
        File timetable = new File(BOB.getStoredLocation());
        String userInput = targetIndex;
        assertTrue(timetable.exists());
        DeleteTimetableCommand deleteTimetableCommand = new DeleteTimetableCommand(INDEX_SECOND);
        assertParseSuccess(parser, userInput, deleteTimetableCommand);
    }

    @Test
    public void parse_resetTimetable_success() {
        String targetIndex = INDEX_SECOND.getOneBased() + " ";
        File timetable = new File(BOB.getStoredLocation());
        if (timetable.exists()) {
            timetable.delete();
        }
        String userInput = targetIndex;
        assertFalse(timetable.exists());
        DeleteTimetableCommand deleteTimetableCommand = new DeleteTimetableCommand(INDEX_SECOND);
        assertParseSuccess(parser, userInput, deleteTimetableCommand);
        assertFalse(timetable.exists());
    }

}
