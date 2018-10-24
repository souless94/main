package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddGroupCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newGroup_success() {
        Group validGroup = new GroupBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.add(validGroup);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddGroupCommand(validGroup), model, commandHistory,
                String.format(AddGroupCommand.MESSAGE_SUCCESS, validGroup.getName()), expectedModel);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        Group groupInList = model.getAddressBook().getGroupList().get(0);
        assertCommandFailure(new AddGroupCommand(groupInList), model, commandHistory,
                AddGroupCommand.MESSAGE_DUPLICATE_GROUP);
    }

}
