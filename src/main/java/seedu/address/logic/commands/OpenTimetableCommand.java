package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;

import java.io.File;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * opens the timetable of the person
 */
public class OpenTimetableCommand extends Command {

    public static final String COMMAND_WORD = "open_timetable";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": open timetable file of the person identified "
            + "by the index number used in the displayed person list."
            + " \n"
            + "Parameters : INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_OPEN_TIMETABLE_SUCCESS = "timetable opened successfully";


    private final Index index;

    /**
     * @param index of the person in the filtered person list to edit
     */

    public OpenTimetableCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToOpenTimetable = lastShownList.get(index.getZeroBased());
        String location = personToOpenTimetable.getTimetable().getStoredLocation();
        try {
            Desktop.getDesktop().open(new File(location));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult("file opened successfully");
    }
}
