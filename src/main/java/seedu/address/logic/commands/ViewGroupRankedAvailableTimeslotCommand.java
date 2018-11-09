package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;

/**
 * Lists all time slots in descending order in terms of availablility with 
 * a minimum number of people available required
 */
public class ViewGroupRankedAvailableTimeslotCommand extends Command {

    public static final String COMMAND_WORD = "view_slots_ranked";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the time slots of a group in descending order "
            + "in terms of availability with a minimum number of people available required "
            + "[" + PREFIX_NAME + "GROUP NAME] [" + PREFIX_NUMBER + "NUM REQ]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Family " + PREFIX_NUMBER + "1";

    public static final String MESSAGE_SUCCESS = "Listed all time slots in descending order of availability"
            + " with at least ";

    private final Name groupName;
    private final int numberRequired;


    /**
     * @param groupName of the group to find the available timeslots of its members
     */
    public ViewGroupRankedAvailableTimeslotCommand(Name groupName, int numberRequired) {
        requireNonNull(groupName);
        requireNonNull(numberRequired);
        this.groupName = groupName;
        this.numberRequired = numberRequired;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Group group = CommandUtil.retrieveGroupFromName(model, groupName);

        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(MESSAGE_SUCCESS + this.numberRequired + " person(s) available:\n" + 
            group.listRankedAvailableTimeslots(numberRequired));
    }

}
