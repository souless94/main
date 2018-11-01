package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
 * edits a particular cell in timetable of a person
 */
public class EditTimetableCommand extends Command {

    public static final String COMMAND_WORD = "edit_timetable";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": edit timetable for the person identified "
            + "by the index number used in the displayed person list."
            + "if no details is entered, make the details blank "
            + "for the timeslot in the timetable"
            + " \n"
            + "Parameters : INDEX (must be a positive integer) "
            + "[" + PREFIX_DAY + "DAY] "
            + "[" + PREFIX_TIMING + "TIMING] "
            + "[" + PREFIX_DETAILS + "DETAILS] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "Wednesday "
            + PREFIX_TIMING + "1700 "
            + "[" + PREFIX_DETAILS + "]" + "do cs2103";

    public static final String MESSAGE_EDIT_TIMETABLE_SUCCESS = "timetable edited successfully";


    private final Index index;
    private final String day;
    private final String timing;
    private final String details;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public EditTimetableCommand(Index index, String day, String timing, String details) {
        requireNonNull(index);
        this.index = index;
        this.day = day;
        this.timing = timing;
        this.details = details;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Person personToEdit = CommandUtil.retrievePersonFromIndex(model, index);
        String filePath = personToEdit.getStoredLocation();
        Timetable timetable = new Timetable(filePath,
            personToEdit.getTimetable().getTimetableDataString(), 3, day, timing, details);
        Person updatedPerson = createUpdatedPerson(personToEdit, timetable, filePath);
        model.update(personToEdit, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_TIMETABLE_SUCCESS, updatedPerson));
    }

    /**
     * it updates the timetableData of the person. Creates and returns a {@code Person} with the
     * details of {@code personToEdit}
     */
    public static Person createUpdatedPerson(Person personToEdit, Timetable timetable,
        String filePath) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        String storedLocation = filePath;
        String timetableString = timetable.getTimetableDataString();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
            storedLocation, timetableString);
    }

    @Override
    public boolean equals(Object other) {
        if (day == null && timing == null) {
            return other == this // short circuit if same object
                || (other instanceof EditTimetableCommand // instanceof handles nulls
                && index.equals(((EditTimetableCommand) other).index))
                && isNull(((EditTimetableCommand) other).day)
                && isNull(((EditTimetableCommand) other).timing)
                && details.equals(((EditTimetableCommand) other).details);
        }
        return other == this // short circuit if same object
            || (other instanceof EditTimetableCommand // instanceof handles nulls
            && index.equals(((EditTimetableCommand) other).index))
            && day.equals(((EditTimetableCommand) other).day)
            && timing.equals(((EditTimetableCommand) other).timing)
            && details.equals(((EditTimetableCommand) other).details);
    }
}
