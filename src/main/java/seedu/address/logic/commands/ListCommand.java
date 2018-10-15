package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons and groups in the address book to the user.
 * #TODO: Edit User Guide list function
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons and groups.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
