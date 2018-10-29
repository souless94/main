package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

class DeleteTimetableCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    void deleteTimetableSuccess() {
        Person personToDeleteTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        personToDeleteTimetable.getTimetable().downloadTimetableAsCsv();
        assertTrue(new File(personToDeleteTimetable.getStoredLocation()).exists());
        DeleteTimetableCommand deleteTimetableCommand = new DeleteTimetableCommand(
            INDEX_FIRST);
        String expectedMessage = String
            .format(DeleteTimetableCommand.MESSAGE_DELETE_TIMETABLE_SUCCESS,
                personToDeleteTimetable);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());
        expectedModel.update(model.getFilteredPersonList().get(0), personToDeleteTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(deleteTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);

        assertFalse(new File(personToDeleteTimetable.getStoredLocation()).exists());
    }
}
