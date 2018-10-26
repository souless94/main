package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Entity;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;

public class AddGroupCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddGroupCommand(null);
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGroupAdded modelStub = new ModelStubAcceptingGroupAdded();
        Group validGroup = new GroupBuilder().build();

        CommandResult commandResult = new AddGroupCommand(validGroup)
            .execute(modelStub, commandHistory);

        assertEquals(String.format(AddGroupCommand.MESSAGE_SUCCESS, validGroup),
            commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validGroup), modelStub.groupsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() throws Exception {
        Group validGroup = new GroupBuilder().build();
        AddGroupCommand addGroupCommand = new AddGroupCommand(validGroup);
        ModelStub modelStub = new ModelStubWithGroup(validGroup);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddGroupCommand.MESSAGE_DUPLICATE_GROUP);
        addGroupCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Group family = new GroupBuilder().withName("Family").build();
        Group friends = new GroupBuilder().withName("Friends").build();
        AddGroupCommand addFamilyCommand = new AddGroupCommand(family);
        AddGroupCommand addFriendsCommand = new AddGroupCommand(friends);

        // same object -> returns true
        assertTrue(addFamilyCommand.equals(addFamilyCommand));

        // same values -> returns true
        AddGroupCommand addFamilyCommandCopy = new AddGroupCommand(family);
        assertTrue(addFamilyCommand.equals(addFamilyCommandCopy));

        // different types -> returns false
        assertFalse(addFamilyCommand.equals(1));

        // null -> returns false
        assertFalse(addFamilyCommand.equals(null));

        // different person -> returns false
        assertFalse(addFamilyCommand.equals(addFriendsCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void add(Entity key) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void delete(Entity key) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean has(Entity key) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void update(Entity target, Entity edited) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single group.
     */
    private class ModelStubWithGroup extends ModelStub {

        private final Group group;

        ModelStubWithGroup(Group group) {
            requireNonNull(group);
            this.group = group;
        }

        @Override
        public boolean has(Entity target) {
            requireNonNull(target);
            if (target instanceof Group) {
                return this.group.isSame(target);
            }
            return false;
        }
    }

    /**
     * A Model stub that always accept the group being added.
     */
    private class ModelStubAcceptingGroupAdded extends ModelStub {

        final ArrayList<Group> groupsAdded = new ArrayList<>();

        @Override
        public boolean has(Entity target) {
            requireNonNull(target);
            if (target instanceof Group) {
                return groupsAdded.stream().anyMatch(target::isSame);
            }
            return false;
        }

        @Override
        public void add(Entity target) {
            requireNonNull(target);
            if (target instanceof Group) {
                groupsAdded.add((Group) target);
            }
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddGroupCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
