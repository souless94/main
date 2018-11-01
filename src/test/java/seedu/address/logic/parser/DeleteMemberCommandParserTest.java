package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_BOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_BOO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.model.person.Name;

public class DeleteMemberCommandParserTest {

    private DeleteMemberCommandParser parser = new DeleteMemberCommandParser();

    @Test
    public void parseValidArgsReturnsDeleteMemberCommand() {
        assertParseSuccess(parser, " 1" + GROUPNAME_DESC_BOO, new DeleteMemberCommand(
                new Name(VALID_GROUP_NAME_BOO), INDEX_FIRST));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteMemberCommand.MESSAGE_USAGE));
    }
}
