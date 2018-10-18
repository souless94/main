package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;

/**
 * Lists all available timeslots with no conflicts
 */
public class ViewGroupAvailableTimeslotCommand extends Command {

    public static final String COMMAND_WORD = "view_group_slots";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the available timeslots of a group"
            + "[" + PREFIX_NAME + " GROUP NAME]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_NAME + "Family ";

    public static final String MESSAGE_SUCCESS = "Listed all available timeslots:\n";

    private final Name groupName;

    /**
     * @param groupName of the group to find the available timeslots of its members
     */
    public ViewGroupAvailableTimeslotCommand(Name groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Group group = CommandUtil.retrieveGroupFromName(model, groupName);

        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(MESSAGE_SUCCESS + group.listAvailableTimeslots());
    }

}
