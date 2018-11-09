package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.io.File;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UniqueList;
import seedu.address.model.exceptions.NotFoundException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book and delete it as
 * member from all groups that it is in.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the person identified by the index number used in the displayed person list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    //@@author Happytreat
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person personToDelete = CommandUtil.retrievePersonFromIndex(model, targetIndex);

        File timetableToBeDeleted = new File(personToDelete.getStoredLocation());
        if (timetableToBeDeleted.exists()) {
            timetableToBeDeleted.delete();
        }

        for (Group group : personToDelete.getGroups()) {
            Group groupToUpdate = CommandUtil.retrieveGroupFromName(model, group.getName());
            groupToUpdate = deleteMemberFromGroup(groupToUpdate, personToDelete);
            model.update(group, groupToUpdate);
        }

        model.delete(personToDelete);

        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    /**
     * @return updated group that has {@code personToDelete} deleted from its member list
     */
    public Group deleteMemberFromGroup(Group groupToBeEdited, Person personToDelete)
        throws CommandException {
        try {
            UniqueList<Person> newGroupMembers = new UniqueList<>();
            newGroupMembers.setElements(groupToBeEdited.getGroupMembers());

            newGroupMembers.remove(personToDelete);

            return new Group(groupToBeEdited.getName(), groupToBeEdited.getDescription(),
                newGroupMembers);
        } catch (NotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
