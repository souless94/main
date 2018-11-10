package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RegisterCommand}.
 */
public class RegisterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeGroupExistsSuccess() {
        Group group = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        // Registering CARL to BESTFRIEND
        RegisterCommand registerCommand = new RegisterCommand(group.getName(), INDEX_THIRD);

        String expectedMessage = String.format(RegisterCommand.MESSAGE_SUCCESS, CARL);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        RegisterCommand.addMemberToGroup(expectedModel, group, CARL);
        expectedModel.commitAddressBook();

        assertCommandSuccess(registerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeGroupDoesNotExistThrowsCommandException() {
        Group group = new GroupBuilder().build();
        RegisterCommand registerCommand = new RegisterCommand(group.getName(), INDEX_FIRST);

        assertCommandFailure(registerCommand, model, commandHistory, Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
    }

    @Test
    public void executeDuplicatePersonInGroupThrowsCommandException() {
        Group group = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        // Registering ALICE to BESTFRIEND (duplicate person)
        RegisterCommand registerCommand = new RegisterCommand(group.getName(), INDEX_FIRST);
        assertCommandFailure(registerCommand, model, commandHistory, RegisterCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void executeUndoRedoGroupExistsSsuccess() throws Exception {
        Group group = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        RegisterCommand registerCommand = new RegisterCommand(group.getName(), INDEX_THIRD);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        RegisterCommand.addMemberToGroup(expectedModel, group, CARL);
        expectedModel.commitAddressBook();

        // register CARL into BESTFRIEND group
        registerCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered group list to show all groups
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> CARL added to BESTFRIEND again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedoGroupDoesNotExistsFailure() {
        Group group = new GroupBuilder().build();
        RegisterCommand registerCommand = new RegisterCommand(group.getName(), INDEX_FIRST);

        // execution failed -> address book state not added into model
        assertCommandFailure(registerCommand, model, commandHistory, Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedoDuplicatePersonFailure() {
        Group group = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        // Registering ALICE to BESTFRIEND (duplicate person)
        RegisterCommand registerCommand = new RegisterCommand(group.getName(), INDEX_FIRST);

        // execution failed -> address book state not added into model
        assertCommandFailure(registerCommand, model, commandHistory, RegisterCommand.MESSAGE_DUPLICATE_PERSON);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Group groupAtIndexFirst = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        Group groupAtIndexSecond = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        RegisterCommand registerFirstCommand = new RegisterCommand(groupAtIndexFirst.getName(),
                Index.fromOneBased(8));
        RegisterCommand registerSecondCommand = new RegisterCommand(groupAtIndexSecond.getName(),
                Index.fromOneBased(8));
        RegisterCommand registerThirdCommand = new RegisterCommand(groupAtIndexFirst.getName(),
                Index.fromOneBased(9));

        // same object -> returns true
        assertTrue(registerFirstCommand.equals(registerFirstCommand));

        // same values -> returns true
        RegisterCommand registerFirstCommandCopy = new RegisterCommand(groupAtIndexFirst.getName(),
                Index.fromOneBased(8));
        assertTrue(registerFirstCommandCopy.equals(registerFirstCommand));

        // different types -> returns false
        assertFalse(registerFirstCommand.equals(1));

        // null -> returns false
        assertFalse(registerFirstCommand.equals(null));

        // different group with same person added -> returns false
        assertFalse(registerFirstCommand.equals(registerSecondCommand));

        // different person added to same group --> returns false
        assertFalse(registerFirstCommand.equals(registerThirdCommand));
    }
}
