package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditTimetableCommand.createUpdatedPerson;
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
            + " \n"
            + "Parameters : INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ADD_TIMETABLE_SUCCESS = "timetable added successfully: %1$s";
    public static final String MESSAGE_TIMETABLE_NOT_FOUND = "timetable to be added is not found";

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
        if ("default".equals(newFilePath)) {
            filePath = personToEdit.getStoredLocation();
        } else {
            filePath = newFilePath.replace("\\", "/");
        }
        boolean doesFileExists = new File(filePath).exists();
        if (doesFileExists) {
            Timetable timetable = new Timetable(filePath, personToEdit.getFormat(),
                personToEdit.getTimetable().getTimetableDataString(), 2, null, null, null);
            Person updatedPerson = createUpdatedPerson(personToEdit, timetable, filePath);
            model.update(personToEdit, updatedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.commitAddressBook();
            return new CommandResult(
                String.format(MESSAGE_ADD_TIMETABLE_SUCCESS, updatedPerson.getStoredLocation()));
        } else {
            throw new CommandException(MESSAGE_TIMETABLE_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddTimetableCommand // instanceof handles nulls
            && index.equals(((AddTimetableCommand) other).index))
            && newFilePath.equals(((AddTimetableCommand) other).newFilePath);
    }
}

