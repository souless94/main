package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class EditTimetableCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeEditTimetableSuccess() {
        Person personToEditTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        String day = VALID_DAY;
        String timing = VALID_TIMING;
        String details = VALID_DETAILS;
        EditTimetableCommand editTimetableCommand = new EditTimetableCommand(INDEX_FIRST, day,
            timing, details);
        String expectedMessage = String
            .format(EditTimetableCommand.MESSAGE_EDIT_TIMETABLE_SUCCESS, personToEditTimetable);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());
        expectedModel.update(model.getFilteredPersonList().get(0), personToEditTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(editTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

    @Test
    public void executeEditTimetableSuccessWithCapitalLetterDay() {
        Person personToEditTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        String day = "WednEsday";
        String timing = VALID_TIMING;
        String details = VALID_DETAILS;
        EditTimetableCommand editTimetableCommand = new EditTimetableCommand(INDEX_FIRST, day,
            timing, details);
        String expectedMessage = String
            .format(EditTimetableCommand.MESSAGE_EDIT_TIMETABLE_SUCCESS, personToEditTimetable);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());
        expectedModel.update(model.getFilteredPersonList().get(0), personToEditTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(editTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String day = VALID_DAY;
        String timing = VALID_TIMING;
        String details = VALID_DETAILS;
        EditTimetableCommand editTimetableCommand = new EditTimetableCommand(outOfBoundIndex, day,
            timing, details);

        assertCommandFailure(editTimetableCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
