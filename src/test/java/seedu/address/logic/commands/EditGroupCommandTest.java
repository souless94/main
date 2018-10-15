package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_BOO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroups.FAMILY;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditGroupCommand.EditGroupDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.testutil.EditGroupDescriptorBuilder;
import seedu.address.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditGroupCommand.
 */
public class EditGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Group editedGroup = new GroupBuilder().build();
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder(editedGroup).build();
        EditGroupCommand editGroupCommand = new EditGroupCommand(FAMILY.getName(), descriptor);

        String expectedMessage = String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS, editedGroup);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.update(model.getFilteredGroupList().get(0), editedGroup);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editGroupCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastGroup = Index.fromOneBased(model.getFilteredGroupList().size());
        Group lastGroup = model.getFilteredGroupList().get(indexLastGroup.getZeroBased());

        GroupBuilder groupInList = new GroupBuilder(lastGroup);
        Group editedGroup = groupInList.withName(VALID_GROUP_NAME_BOO).build();

        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder().withName(VALID_GROUP_NAME_BOO).build();
        EditGroupCommand editGroupCommand = new EditGroupCommand(lastGroup.getName(), descriptor);

        String expectedMessage = String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS, editedGroup);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.update(lastGroup, editedGroup);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editGroupCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Index indexLastGroup = Index.fromOneBased(model.getFilteredGroupList().size());
        Group lastGroup = model.getFilteredGroupList().get(indexLastGroup.getZeroBased());

        EditGroupCommand editGroupCommand = new EditGroupCommand(lastGroup.getName(), new EditGroupDescriptor());

        String expectedMessage = String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS, lastGroup);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editGroupCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGroupUnfilteredList_failure() {
        Group firstPerson = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        Group secondPerson = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder(firstPerson).build();
        EditGroupCommand editGroupCommand = new EditGroupCommand(secondPerson.getName(), descriptor);

        assertCommandFailure(editGroupCommand, model, commandHistory, EditGroupCommand.MESSAGE_DUPLICATE_GROUP);
    }

    @Test
    public void execute_duplicateGroupNameUnfilteredList_failure() {
        // edit group in filtered list to have a duplicate name in address book
        Group groupInList = model.getAddressBook().getGroupList().get(INDEX_SECOND.getZeroBased());
        Group firstPerson = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());

        EditGroupCommand editGroupCommand = new EditGroupCommand(firstPerson.getName(),
                new EditGroupDescriptorBuilder().withName(groupInList.getName().fullName).build());

        assertCommandFailure(editGroupCommand, model, commandHistory, EditGroupCommand.MESSAGE_DUPLICATE_GROUP);
    }

    @Test
    public void execute_invalidGroupNameUnfilteredList_failure() {
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder().withName(VALID_GROUP_NAME_BOO).build();
        EditGroupCommand editGroupCommand = new EditGroupCommand(new Name(VALID_GROUP_NAME_BOO), descriptor);

        assertCommandFailure(editGroupCommand, model, commandHistory, Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
    }


    @Test
    public void executeUndoRedo_validGroupUnfilteredList_success() throws Exception {
        Group editedGroup = new GroupBuilder().build();
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder(editedGroup).build();

        String expectedMessage = String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS, editedGroup);

        Group groupToEdit = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        EditGroupCommand editGroupCommand = new EditGroupCommand(groupToEdit.getName(), descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.update(groupToEdit, editedGroup);
        expectedModel.commitAddressBook();

        // edit -> first person edited
        editGroupCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidGroupUnfilteredList_failure() {
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder().withName(VALID_GROUP_NAME_BOO).build();
        EditGroupCommand editGroupCommand = new EditGroupCommand(new Name(VALID_GROUP_NAME_BOO), descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editGroupCommand, model, commandHistory, Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Group groupToEdit = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder().withName(VALID_GROUP_NAME_BOO).build();
        final EditGroupCommand standardCommand = new EditGroupCommand(groupToEdit.getName(), descriptor);

        // same values -> returns true
        EditGroupDescriptor copyDescriptor = new EditGroupDescriptor(descriptor);
        EditGroupCommand commandWithSameValues = new EditGroupCommand(groupToEdit.getName(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different group to edit -> returns false
        Group groupToEdit2 = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        assertFalse(standardCommand.equals(new EditGroupCommand(groupToEdit2.getName(), copyDescriptor)));

        // different descriptor -> returns false
        EditGroupDescriptor descriptor2 = new EditGroupDescriptorBuilder().withDescription(VALID_GROUP_DESCRIPTION)
                .build();
        assertFalse(standardCommand.equals(new EditGroupCommand(groupToEdit.getName(), descriptor2)));
    }

}
