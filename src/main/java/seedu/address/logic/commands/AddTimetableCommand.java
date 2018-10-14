package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * adds timetable to person
 */
public class AddTimetableCommand extends Command {

    public static final String COMMAND_WORD = "add_timetable";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": adds timetable to the person identified "
                    + "by the index number used in the displayed person list. "
                    + "there are 2 modes new or existing, new requires just format."
                    + "Existing requires filename and file location and format"
                    + "Existing timetable will be overwritten by the input values.\n"
                    + "Parameters for mode = new: INDEX (must be a positive integer) "
                    + "[" + PREFIX_MODE + "]"
                    + "[" + PREFIX_FILE_NAME + "]"
                    + "[" + PREFIX_FORMAT + "]"
                    + "Example: " + COMMAND_WORD + " 1 "
                    + PREFIX_MODE + "new " + PREFIX_FILE_NAME + "test " + PREFIX_FORMAT + "vertical"
                    + "\n"
                    + "Parameters for mode = existing: INDEX (must be a positive integer) "
                    + "[" + PREFIX_MODE + "]"
                    + "[" + PREFIX_FORMAT + "]"
                    + "[" + PREFIX_FILE_NAME + "]"
                    + "[" + PREFIX_FILE_LOCATION + "] \n"
                    + "Example: " + COMMAND_WORD + " 1 "
                    + PREFIX_MODE + "existing " + PREFIX_FORMAT + "horizontal "
                    + PREFIX_FILE_NAME + "test " + PREFIX_FILE_LOCATION + "C:\\Users\\admin\\Downloads";

    public static final String MESSAGE_ADD_TIMETABLE_SUCCESS = "timetable added successfully";


    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */

    public AddTimetableCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);
        this.index = index;
        this.editPersonDescriptor = editPersonDescriptor;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person personToEdit = CommandUtil.retrievePersonFromIndex(model, index);
        Person editedPerson = EditCommand.createEditedPerson(personToEdit, editPersonDescriptor);
        model.update(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_TIMETABLE_SUCCESS, editedPerson));
    }
}

