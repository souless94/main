package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.timetable.Timetable;

/**
 * download a timetable to a specific location
 */
public class DownloadTimetableCommand extends Command {

    public static final String COMMAND_WORD = "download_timetable";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": download timetable from the person identified "
            + "by the index number used in the displayed person list. "
            + "there must not be a file with same filename"
            + "as the person timetable filename "
            + " in the download folder location \n"
            + "Parameters : INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DOWNLOAD_TIMETABLE_SUCCESS = "timetable downloaded successfully to : ";
    public static final String MESSAGE_TIMETABLE_IS_PRESENT =
        "there is a csv file with same name as your timetable filename";
    private final Index index;

    /**
     * location of the download is gotten from the person
     *
     * @param index of the person to download timetable from
     */
    public DownloadTimetableCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDownloadTimetable = lastShownList.get(index.getZeroBased());
        boolean doesFileExists = new File(personToDownloadTimetable.getStoredLocation()).exists();
        if (doesFileExists) {
            throw new CommandException(MESSAGE_TIMETABLE_IS_PRESENT);
        }
        Timetable timetable = personToDownloadTimetable.getTimetable();
        timetable.downloadTimetableAsCsv();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        model.commitAddressBook();
        return new CommandResult(
            String.format(MESSAGE_DOWNLOAD_TIMETABLE_SUCCESS)
                + personToDownloadTimetable.getStoredLocation());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DownloadTimetableCommand // instanceof handles nulls
            && index.equals(((DownloadTimetableCommand) other).index));
    }
}
