package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

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
 * {@code DeleteMemberCommand}.
 */
public class DeleteMemberCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeGroupExistsSuccess() {
        Group group = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        // Deleting ALICE from BESTFRIENDS
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(group.getName(), INDEX_FIRST);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Group newGroup = DeleteMemberCommand.deleteMemberFromGroup(expectedModel, group, INDEX_FIRST);
        expectedModel.commitAddressBook();

        String expectedMessage = String.format(DeleteMemberCommand.MESSAGE_SUCCESS, newGroup);

        assertCommandSuccess(deleteMemberCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeGroupDoesNotExistThrowsCommandException() {
        Group group = new GroupBuilder().build();
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(group.getName(), INDEX_FIRST);
        assertCommandFailure(deleteMemberCommand, model, commandHistory, Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
    }

    @Test
    public void executeInvalidIndexInGroupThrowsCommandException() {
        Group group = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        // BESTFRIEND only has 3 members
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(group.getName(), Index.fromOneBased(4));
        assertCommandFailure(deleteMemberCommand, model, commandHistory,
                DeleteMemberCommand.NO_MEMBER_WITH_GIVEN_INDEX);
    }

    @Test
    public void executeUndoRedoGroupExistsSsuccess() throws Exception {
        Group group = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(group.getName(), INDEX_FIRST);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Group newGroup = DeleteMemberCommand.deleteMemberFromGroup(expectedModel, group, INDEX_FIRST);
        expectedModel.commitAddressBook();


        // deleting ALICE from BESTFRIENDS group
        deleteMemberCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered group list to show only that group
        // and remaining its members
        expectedModel.undoAddressBook();
        expectedModel.updateFilteredGroupList(Model.PREDICATE_SHOW_ALL_GROUPS);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> ALICE deleted from BESTFRIENDS again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedoGroupDoesNotExistsFailure() {
        Group group = new GroupBuilder().build();
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(group.getName(), INDEX_FIRST);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteMemberCommand, model, commandHistory, Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedoInvalidIndexFailure() {
        Group group = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        // BESTFRIEND only has 3 members
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(group.getName(), Index.fromOneBased(4));

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteMemberCommand, model, commandHistory,
                DeleteMemberCommand.NO_MEMBER_WITH_GIVEN_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Group groupAtIndexFirst = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        Group groupAtIndexSecond = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        DeleteMemberCommand deleteFirstCommand = new DeleteMemberCommand(groupAtIndexFirst.getName(), INDEX_FIRST);
        DeleteMemberCommand deleteSecondCommand = new DeleteMemberCommand(groupAtIndexSecond.getName(), INDEX_FIRST);
        DeleteMemberCommand deleteThirdCommand = new DeleteMemberCommand(groupAtIndexFirst.getName(), INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMemberCommand deleteFirstCommandCopy = new DeleteMemberCommand(groupAtIndexFirst.getName(), INDEX_FIRST);
        assertTrue(deleteFirstCommandCopy.equals(deleteFirstCommand));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different group with same person deleted -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different person deleted from same group --> returns false
        assertFalse(deleteFirstCommand.equals(deleteThirdCommand));
    }
}
