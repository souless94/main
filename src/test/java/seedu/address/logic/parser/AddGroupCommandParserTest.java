package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOO;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_BOO;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUPNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_DESCRIPTION_BOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_BOO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.testutil.GroupBuilder;

public class AddGroupCommandParserTest {
    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Group expectedGroup = new GroupBuilder().withName(VALID_GROUP_NAME_BOO)
                .withDescription(VALID_GROUP_DESCRIPTION_BOO).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GROUPNAME_DESC_BOO + DESCRIPTION_DESC_BOO,
                new AddGroupCommand(expectedGroup));

        // multiple group names - last name accepted
        assertParseSuccess(parser, GROUPNAME_DESC_FRIENDS + GROUPNAME_DESC_BOO + DESCRIPTION_DESC_BOO,
                new AddGroupCommand(expectedGroup));

        // multiple description - last description accepted
        assertParseSuccess(parser, GROUPNAME_DESC_BOO + DESCRIPTION_DESC_FRIENDS + DESCRIPTION_DESC_BOO,
                new AddGroupCommand(expectedGroup));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Group expectedGroup = new GroupBuilder().withName(VALID_GROUP_NAME_BOO).build();
        assertParseSuccess(parser, GROUPNAME_DESC_BOO, new AddGroupCommand(expectedGroup));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_GROUP_NAME_BOO + DESCRIPTION_DESC_BOO, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_GROUP_NAME_BOO + VALID_GROUP_DESCRIPTION, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_GROUPNAME_DESC + DESCRIPTION_DESC_BOO,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GROUPNAME_DESC_BOO + DESCRIPTION_DESC_BOO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
    }
}
