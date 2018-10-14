package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteGroupCommand}.
 */
public class DeleteGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_groupExists_success() {
        Group groupToDelete = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(groupToDelete);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_SUCCESS, groupToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.delete(groupToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteGroupCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_groupDoesNotExist_throwsCommandException() {
        Group groupToDelete = new GroupBuilder().build();
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(groupToDelete);

        assertCommandFailure(deleteGroupCommand, model, commandHistory, Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
    }

    @Test
    public void executeUndoRedo_groupExists_success() throws Exception {
        Group groupToDelete = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(groupToDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.delete(groupToDelete);
        expectedModel.commitAddressBook();

        // delete -> first group deleted
        deleteGroupCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered group list to show all groups
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first group deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_groupDoesNotExists_failure() {
        Group groupToDelete = new GroupBuilder().build();
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(groupToDelete);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteGroupCommand, model, commandHistory, Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Group groupAtIndexFirst = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        Group groupAtIndexSecond = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        DeleteGroupCommand deleteFirstCommand = new DeleteGroupCommand(groupAtIndexFirst);
        DeleteGroupCommand deleteSecondCommand = new DeleteGroupCommand(groupAtIndexSecond);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteGroupCommand deleteFirstCommandCopy = new DeleteGroupCommand(groupAtIndexFirst);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));
        assertFalse(deleteFirstCommand.equals("deleteFirstCommand"));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different group deleted -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
