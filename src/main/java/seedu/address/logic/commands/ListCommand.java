package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Lists all persons and groups in the address book to the user if no further parameter given.
 * Lists all groups that a person is enrolled in if index of person is inputted.
 * #TODO: Edit User Guide list function
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all persons and groups.";

    private static final String MESSAGE_SUCCESS_LIST_GROUPS = "Listed all groups the person is in.";

    private Index index = null;

    public ListCommand() {}

    public ListCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (index == null) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        try {
            Person personListed = model.getFilteredPersonList().get(index.getZeroBased());
            Predicate<Person> predicateShowPerson = person -> person == personListed;
            Predicate<Group> predicateShowGroups = group -> personListed.getGroups().contains(group);
            model.updateFilteredPersonList(predicateShowPerson);
            model.updateFilteredGroupList(predicateShowGroups);
            return new CommandResult(MESSAGE_SUCCESS_LIST_GROUPS);

        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }
}
