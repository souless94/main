package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOO;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_BOO;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUPNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_DESCRIPTION_BOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_BOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddGroupCommandParserTest {
    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Group expectedGroup = new GroupBuilder().withName(VALID_GROUP_NAME_BOO)
                .withDescription(VALID_GROUP_DESCRIPTION_BOO).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GROUPNAME_DESC_BOO + DESCRIPTION_DESC_BOO
                , new AddGroupCommand(expectedGroup));

        // multiple group names - last name accepted
        assertParseSuccess(parser, GROUPNAME_DESC_FRIENDS + GROUPNAME_DESC_BOO + DESCRIPTION_DESC_BOO,
                new AddGroupCommand(expectedGroup));

        // multiple description - last description accepted
        assertParseSuccess(parser, GROUPNAME_DESC_BOO + DESCRIPTION_DESC_FRIENDS + DESCRIPTION_DESC_BOO
                , new AddGroupCommand(expectedGroup));
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
