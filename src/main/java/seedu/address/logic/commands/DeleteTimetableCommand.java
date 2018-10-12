package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * deletes the timetable the person have and gives the person a default timetable
 */
public class DeleteTimetableCommand extends Command {

    public static final String COMMAND_WORD = "delete_timetable";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": delete timetable and adds a default timetable to the person identified"
            + "by the index number used in the displayed person list."
            + " \n"
            + "Parameters : INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_TIMETABLE_SUCCESS = "timetable deleted successfully";


    private final Index index;

    public DeleteTimetableCommand(Index index) {
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

        Person personToDeleteTimetable = lastShownList.get(index.getZeroBased());
        File toBeDeleted = new File(
            personToDeleteTimetable.getStoredLocation()
                + "/"
                + personToDeleteTimetable.getName().toString() + ".csv");
        if (toBeDeleted.exists()) {
            toBeDeleted.delete();
        }
        Person updatedPerson = createPersonWithNewTimetable(personToDeleteTimetable);
        model.updatePerson(personToDeleteTimetable, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_TIMETABLE_SUCCESS, updatedPerson));
    }

    /**
     * it creates a new person with a default timetable while not changing other details of person
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     */
    private Person createPersonWithNewTimetable(Person personToEdit) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
            "default", "default", "default");
    }
}
