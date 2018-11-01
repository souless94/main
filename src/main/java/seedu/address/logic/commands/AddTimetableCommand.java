package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_TIMETABLE_NOT_FOUND;
import static seedu.address.logic.commands.EditTimetableCommand.createUpdatedPerson;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.timetable.Timetable;

/**
 * adds timetable to person
 */
public class AddTimetableCommand extends Command {


    public static final String COMMAND_WORD = "add_timetable";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": adds timetable to the person identified "
            + "by the index number used in the displayed person list."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_FILE_LOCATION + "FILE_LOCATION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FILE_LOCATION
            + "C:/Users/wen kai/Downloads/y4s1/cs2103/project2/data/timetable/495011161 timetable";

    public static final String MESSAGE_ADD_TIMETABLE_SUCCESS = "timetable added successfully: %1$s";
    public static final String MESSAGE_INVALID_TIMETABLE_SIZE =
        "timetable to be added is wrong: \n"
            + "timetable should have in total rows: 8 , columns : 17 \n";
    private static final String timings = "correctTimings : \n"
        + "0800,0900,1000,1100 \n"
        + "1200,1300,1400,1500,1600 \n"
        + "1700,1800,1900,2000,2100,2200,2300 \n";
    private static final String days = "correctDays: {Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday";

    public static final String MESSAGE_INVALID_TIMETABLE = "timetable to be added is wrong: \n"
        + "does not have correct timings in first row and correct days in first column in the csv file \n"
        + timings + "\n"
        + days + "\n";


    private final Index index;
    private final String newFilePath;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public AddTimetableCommand(Index index, String newFilePath) {
        requireNonNull(index);
        this.index = index;
        this.newFilePath = newFilePath;

    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person personToEdit = CommandUtil.retrievePersonFromIndex(model, index);
        String filePath;
        if (newFilePath == null) {
            filePath = personToEdit.getStoredLocation();
        } else {
            filePath = newFilePath.replace("\\", "/");
        }
        boolean doesFileExists = new File(filePath).exists();
        if (doesFileExists) {
            Timetable timetable = new Timetable(filePath,
                personToEdit.getTimetable().getTimetableDataString(), 2, null, null, null);
            if (!timetable.isValid()) {
                if (!timetable.isCorrectSize()) {
                    throw new CommandException(MESSAGE_INVALID_TIMETABLE_SIZE);
                }
                if (!timetable.hasCorrectFirstRowsAndColumns()) {
                    throw new CommandException(MESSAGE_INVALID_TIMETABLE);
                } else {
                    throw new CommandException(
                        MESSAGE_INVALID_TIMETABLE + MESSAGE_INVALID_TIMETABLE_SIZE);
                }
            }
            Person updatedPerson = createUpdatedPerson(personToEdit, timetable, filePath);
            model.update(personToEdit, updatedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            model.commitAddressBook();
            return new CommandResult(
                String.format(MESSAGE_ADD_TIMETABLE_SUCCESS, updatedPerson.getStoredLocation()));
        } else {
            throw new CommandException(MESSAGE_TIMETABLE_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (newFilePath == null) {
            return other == this // short circuit if same object
                || (other instanceof AddTimetableCommand // instanceof handles nulls
                && index.equals(((AddTimetableCommand) other).index))
                && isNull(((AddTimetableCommand) other).newFilePath);
        }
        return other == this // short circuit if same object
            || (other instanceof AddTimetableCommand // instanceof handles nulls
            && index.equals(((AddTimetableCommand) other).index))
            && newFilePath.equals(((AddTimetableCommand) other).newFilePath);
    }
}

