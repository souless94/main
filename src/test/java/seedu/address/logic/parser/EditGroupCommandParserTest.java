package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOO;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_BOO;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUPNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_DESCRIPTION_BOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_BOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_FRIENDS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.model.person.Name;

public class EditGroupCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);

    private EditGroupCommandParser parser = new EditGroupCommandParser();

    @Test
    public void parseMissingPartsFailure() {
        // no old group name specified
        assertParseFailure(parser, GROUPNAME_DESC_BOO, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_GROUP_NAME_BOO, EditGroupCommand.MESSAGE_NOT_EDITED);

        // no old name and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidPreambleFailure() {
        // no symbols in name
        assertParseFailure(parser, "-Eleven" + GROUPNAME_DESC_BOO, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "Booo%" + GROUPNAME_DESC_BOO, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parseInvalidValueFailure() {
        assertParseFailure(parser, VALID_GROUP_NAME_BOO + INVALID_GROUPNAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
    }

    @Test
    public void parseAllFieldsSpecifiedSuccess() {
        Name name = new Name(VALID_GROUP_NAME_FRIENDS);
        String userInput = VALID_GROUP_NAME_BOO + GROUPNAME_DESC_FRIENDS + DESCRIPTION_DESC_BOO;

        EditGroupCommand.EditGroupDescriptor descriptor = new EditGroupCommand.EditGroupDescriptor();
        descriptor.setName(name);
        descriptor.setDescription(VALID_GROUP_DESCRIPTION_BOO);
        EditGroupCommand expectedCommand = new EditGroupCommand(new Name(VALID_GROUP_NAME_BOO), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseSomeFieldsSpecifiedSuccess() {
        Name name = new Name(VALID_GROUP_NAME_FRIENDS);
        String userInput = VALID_GROUP_NAME_BOO + GROUPNAME_DESC_FRIENDS;

        EditGroupCommand.EditGroupDescriptor descriptor = new EditGroupCommand.EditGroupDescriptor();
        descriptor.setName(name);
        EditGroupCommand expectedCommand = new EditGroupCommand(new Name(VALID_GROUP_NAME_BOO), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseMultipleRepeatedFieldsAcceptsLast() {
        Name name = new Name(VALID_GROUP_NAME_FRIENDS);
        String userInput = VALID_GROUP_NAME_BOO + GROUPNAME_DESC_BOO + GROUPNAME_DESC_FRIENDS;

        EditGroupCommand.EditGroupDescriptor descriptor = new EditGroupCommand.EditGroupDescriptor();
        descriptor.setName(name);
        EditGroupCommand expectedCommand = new EditGroupCommand(new Name(VALID_GROUP_NAME_BOO), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseInvalidValueFollowedByValidValueSuccess() {
        Name name = new Name(VALID_GROUP_NAME_FRIENDS);
        String userInput = VALID_GROUP_NAME_BOO + INVALID_GROUPNAME_DESC + GROUPNAME_DESC_FRIENDS;

        EditGroupCommand.EditGroupDescriptor descriptor = new EditGroupCommand.EditGroupDescriptor();
        descriptor.setName(name);
        EditGroupCommand expectedCommand = new EditGroupCommand(new Name(VALID_GROUP_NAME_BOO), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
