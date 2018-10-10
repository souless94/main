package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class ViewTimetablePropertiesCommand extends Command {

    public static final String COMMAND_WORD = "view_timetable";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": view timetable download and stored folders from the person identified"
            + "by the index number used in the displayed person list. "
            + "file must be created in the folders. \n"
            + "Parameters : INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index index;

    /**
     * location of the download and stored folder is gotten from the person
     *
     * @param index of the person in the filtered person list to edit
     */
    public ViewTimetablePropertiesCommand(Index index) {
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

        Person personToViewTimetable = lastShownList.get(index.getZeroBased());
        boolean doesStoredFolderExists = new File(personToViewTimetable.getStoredLocation())
            .exists();
        boolean doesDownloadFolderExists = new File(personToViewTimetable.getDownloadLocation())
            .exists();
        String message;
        if (!doesDownloadFolderExists){
            message="there is no such download folder in your computer";
        }
        else if(!doesStoredFolderExists){
            message ="there is no such stored folder in your computer";
        }
        else if (!doesStoredFolderExists&&!doesDownloadFolderExists){
            message = "there is no such download and stored folder in your computer";
        }
        else {
            message =
                "location of download folder is at : " + personToViewTimetable.getDownloadLocation()
                    + ".\n"
                    + "location of stored folder is at : " + personToViewTimetable
                    .getStoredLocation()
                    + ".\n";
        }
        return new CommandResult(message);
    }
}
