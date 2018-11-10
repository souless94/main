package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.exceptions.NotFoundException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = "delete_group";

    public static final String ALIAS = "dg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an existing group. "
            + "Parameters: "
            + PREFIX_NAME + "GROUP NAME ";

    public static final String MESSAGE_SUCCESS = "Deleted group successfully: %1$s";

    private final Group groupToBeDeleted;

    /**
     * Creates an CreateGroupCommand to create the specified {@code Group}
     */
    public DeleteGroupCommand(Group group) {
        requireNonNull(group);
        groupToBeDeleted = group;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            Group groupDeleted = CommandUtil.retrieveGroupFromName(model, groupToBeDeleted.getName());
            model.delete(groupDeleted);
            deleteGroupFromMembers(model, groupDeleted);
            model.commitAddressBook();
            model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, groupDeleted.getName()));
        } catch (NotFoundException e) {
            throw new CommandException(Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
        }
    }

    /**
     * Update model with a new {@code Person} with {@code groupDeleted} deleted from list of groups
     * for every member in {@code groupDeleted}
     */
    private static void deleteGroupFromMembers(Model model, Group groupDeleted) {
        assert groupDeleted != null;
        List<Person> membersToEdit = new ArrayList<>(groupDeleted.getGroupMembers());

        for (Person member : membersToEdit) {
            CommandUtil.updatePersonDeleteGroupFromGroupList(model, groupDeleted, member);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && groupToBeDeleted.equals(((DeleteGroupCommand) other).groupToBeDeleted));
    }
}
