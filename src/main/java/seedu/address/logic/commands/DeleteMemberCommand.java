package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UniqueList;
import seedu.address.model.exceptions.NotFoundException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Delete an existing member of an existing group in the address book.
 */
    public class DeleteMemberCommand extends Command {

        public static final String COMMAND_WORD = "delete_member";

        public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete an existing member of an existing group "
                + "Parameters: INDEX of person in view_group (must be a positive integer)\n"
                + "[" + PREFIX_NAME + " GROUP NAME]\n"
                + "Example: " + COMMAND_WORD + " 1 "
                + PREFIX_NAME + "Family ";

        public static final String MISSING_GROUP_NAME = "Please enter group name.";
        private static final String MESSAGE_EDIT_GROUP_SUCCESS = "Deleted member from group: %1$s";
        private static final String NO_MEMBER_WITH_GIVEN_INDEX = "Please input the correct index of person to delete.";


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

        // Make sure that group exists
        List<Group> lastShownList = model.getFilteredGroupList();
        Group groupToBeEdited = new Group(groupName, ""); //do not know description and groupMembers

        if (!lastShownList.contains(groupToBeEdited)) {
            throw new CommandException(Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
        }

        groupToBeEdited = lastShownList.get(lastShownList.indexOf(groupToBeEdited)); //retrieves original group

        try {
            Group editedGroup = deleteMemberFromGroup(groupToBeEdited, index);

            model.updateGroup(groupToBeEdited, editedGroup);
            model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, editedGroup));

        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(NO_MEMBER_WITH_GIVEN_INDEX);
        }

    }

    /**
     * Creates and returns a {@code group} with a member deleted {@code indexToDelete}
     * in {@code groupToBeEdited}
     */
    private static Group deleteMemberFromGroup(Group groupToBeEdited, Index index) throws IndexOutOfBoundsException {
        assert groupToBeEdited != null;

        UniqueList<Person> newGroupMembers = new UniqueList<>();
        newGroupMembers.setElements(groupToBeEdited.getGroupMembers());
        Person personToDelete = groupToBeEdited.getGroupMembers().get(index.getZeroBased());
        newGroupMembers.remove(personToDelete);
        return new Group(groupToBeEdited.getName(), groupToBeEdited.getDescription(), newGroupMembers);
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
