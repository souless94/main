package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UniqueList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Delete an existing member of an existing group in the address book.
 */
public class DeleteMemberCommand extends Command {

    public static final String COMMAND_WORD = "delete_member";

    public static final String ALIAS = "dm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete an existing member of an existing group. "
            + "Parameters: INDEX of person after performing view_group\n"
            + PREFIX_NAME + " GROUP NAME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Family ";

    public static final String MISSING_GROUP_NAME = "Please enter group name.";
    public static final String MESSAGE_SUCCESS = "Deleted member from group: %1$s";
    public static final String NO_MEMBER_WITH_GIVEN_INDEX = "Please input the correct index of person to delete. "
            + "(Index from view_group displayed list.)";


    private final Index index;
    private final Name groupName;

    /**
     * @param groupName of the group in the filtered group list to edit
     * @param index of the person to be added to the group.
     */
    public DeleteMemberCommand(Name groupName, Index index) {
        requireNonNull(groupName);
        requireNonNull(index);
        this.groupName = groupName;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Group groupToBeEdited = CommandUtil.retrieveGroupFromName(model, groupName);

        try {
            Group editedGroup = deleteMemberFromGroup(model, groupToBeEdited, index);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, editedGroup));

        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(NO_MEMBER_WITH_GIVEN_INDEX);
        }

    }

    /**
     * Creates and returns a {@code newGroup} with a member at {@code index} deleted
     * in {@code groupToBeEdited}.
     * Creates a new person {@code newPerson} by removing the reference to {@code group} from person at {@code index}
     * Updates model with {@code newGroup} and {@code newPersons}.
     * Updates the model filteredGroupList with Predicate showing only the group and its remaining members.
     */
    public static Group deleteMemberFromGroup(Model model, Group groupToBeEdited, Index index)
            throws IndexOutOfBoundsException {
        try {
            assert groupToBeEdited != null;
            assert index != null;

            UniqueList<Person> newGroupMembers = new UniqueList<>();
            newGroupMembers.setElements(groupToBeEdited.getGroupMembers());
            Person personToDelete = groupToBeEdited.getGroupMembers().get(index.getZeroBased());
            newGroupMembers.remove(personToDelete);
            Group newGroup = new Group(groupToBeEdited.getName(), groupToBeEdited.getDescription(), newGroupMembers);
            model.update(groupToBeEdited, newGroup);

            //Update personToDelete to have group removed from his grouplist
            CommandUtil.updatePersonDeleteGroupFromGroupList(model, groupToBeEdited, personToDelete);
            Predicate<Person> predicateShowAllMembers = person -> newGroup.getGroupMembers().contains(person);
            model.updateFilteredPersonList(predicateShowAllMembers);
            return newGroup;
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMemberCommand)) {
            return false;
        }

        // state check
        DeleteMemberCommand e = (DeleteMemberCommand) other;
        return groupName.equals(e.groupName)
                && index.equals(e.index);
    }
}
