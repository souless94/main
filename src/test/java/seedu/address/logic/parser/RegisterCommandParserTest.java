package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_BOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_BOO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.RegisterCommand;
import seedu.address.model.person.Name;

public class RegisterCommandParserTest {

    private RegisterCommandParser parser = new RegisterCommandParser();

    @Test
    public void parseValidArgsReturnsRegisterCommand() {
        assertParseSuccess(parser, " 1" + GROUPNAME_DESC_BOO, new RegisterCommand(
                new Name(VALID_GROUP_NAME_BOO), INDEX_FIRST));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RegisterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a" + GROUPNAME_DESC_BOO, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RegisterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RegisterCommand.MISSING_GROUP_NAME));
    }
}
