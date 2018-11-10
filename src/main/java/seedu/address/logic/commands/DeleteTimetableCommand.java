package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UniqueList;
import seedu.address.model.group.Group;
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

    public static final String ALIAS = "dt";

    public static final String MESSAGE_USAGE =
        COMMAND_WORD
            + ": delete timetable from stored location and adds a default timetable to the person identified"
            + "by the index number used in the displayed person list. \n"
            + "resets the timetable of the person if there is no timetable in the stored location"
            + " \n"
            + "Parameters : INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_TIMETABLE_SUCCESS = "delete and reset timetable successfully";

    public static final String MESSAGE_RESET_TIMETABLE_SUCCESS = "reset timetable successfully";

    public static final String MESSAGE_DELETE_TIMETABLE_FAILURE = "timetable was not deleted, but reset successfully";

    private final Index index;

    public DeleteTimetableCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Person personToDeleteTimetable = CommandUtil.retrievePersonFromIndex(model, index);
        File toBeDeleted = new File(personToDeleteTimetable.getStoredLocation());

        Person updatedPerson = createPersonWithNewTimetable(personToDeleteTimetable);
        for (Group group : personToDeleteTimetable.getGroups()) {
            CommandUtil.replacePersonInGroup(model, group, personToDeleteTimetable, updatedPerson);
        }
        model.update(personToDeleteTimetable, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        if (toBeDeleted.exists()) {
            toBeDeleted.delete();
            if (!toBeDeleted.exists()) {
                return new CommandResult(
                    String.format(MESSAGE_DELETE_TIMETABLE_SUCCESS, updatedPerson));
            } else {
                throw new CommandException(MESSAGE_DELETE_TIMETABLE_FAILURE);
            }
        } else {
            return new CommandResult(
                String.format(MESSAGE_RESET_TIMETABLE_SUCCESS, updatedPerson));
        }

    }

    /**
     * it creates a new person with a default timetable while not changing other details of person
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     */
    private Person createPersonWithNewTimetable(Person personToDeleteTimetable) {
        assert personToDeleteTimetable != null;

        Name updatedName = personToDeleteTimetable.getName();
        Phone updatedPhone = personToDeleteTimetable.getPhone();
        Email updatedEmail = personToDeleteTimetable.getEmail();
        Address updatedAddress = personToDeleteTimetable.getAddress();
        Set<Tag> updatedTags = personToDeleteTimetable.getTags();
        String storedLocation = personToDeleteTimetable.getStoredLocation();

        UniqueList<Group> uniqueGroupList = new UniqueList<>();
        uniqueGroupList.setElements(personToDeleteTimetable.getGroups());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
            uniqueGroupList, storedLocation, null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteTimetableCommand // instanceof handles nulls
            && index.equals(((DeleteTimetableCommand) other).index));
    }
}
