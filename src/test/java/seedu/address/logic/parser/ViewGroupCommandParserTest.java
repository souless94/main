package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_BOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_BOO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewGroupCommand;
import seedu.address.model.person.Name;

public class ViewGroupCommandParserTest {

    private ViewGroupCommandParser parser = new ViewGroupCommandParser();

    @Test
    public void parseValidArgsReturnsViewCommand() {
        assertParseSuccess(parser, GROUPNAME_DESC_BOO, new ViewGroupCommand(new Name(VALID_GROUP_NAME_BOO)));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewGroupCommand.MESSAGE_USAGE));
    }
}
