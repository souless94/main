package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_TIMETABLE_NOT_FOUND;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;

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

    public static final String MESSAGE_ADD_TIMETABLE_SUCCESS = "timetable added successfully";


    private final Index index;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public AddTimetableCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person personToEdit = CommandUtil.retrievePersonFromIndex(model, index);

        String filePath = personToEdit.getStoredLocation()
                + "/" + String.valueOf(personToEdit.hashCode())
                + " timetable";
        File toRead = new File(filePath + ".csv");
        if (toRead.exists()) {
            Timetable timetable = new Timetable(filePath, personToEdit.getFormat(), index.getZeroBased());
            Person updatedPerson = createUpdatedPerson(personToEdit, timetable);
            model.update(personToEdit, updatedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_ADD_TIMETABLE_SUCCESS, updatedPerson));
        } else {
            throw new CommandException(MESSAGE_TIMETABLE_NOT_FOUND);
        }
    }

    /**
     * it updates the timetableData of the person. Creates and returns a {@code Person} with the
     * details of {@code personToEdit}
     */
    private static Person createUpdatedPerson(Person personToEdit, Timetable timetable) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        String format = personToEdit.getFormat();
        String storedLocation = personToEdit.getStoredLocation();
        String timetableString = timetable.getTimetableDataString();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                format, storedLocation, timetableString);
    }
}

