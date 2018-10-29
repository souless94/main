package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DownloadTimetableCommand.MESSAGE_TIMETABLE_IS_PRESENT;
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

class DownloadTimetableCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    void downloadTimetableSuccess() {
        Person personToDownloadTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        File timetable = new File(personToDownloadTimetable.getStoredLocation());
        if (timetable.exists()) {
            timetable.delete();
        }
        DownloadTimetableCommand downloadTimetableCommand = new DownloadTimetableCommand(
            INDEX_FIRST);
        String expectedMessage = String
            .format(DownloadTimetableCommand.MESSAGE_DOWNLOAD_TIMETABLE_SUCCESS);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());
        expectedModel.update(model.getFilteredPersonList().get(0), personToDownloadTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(downloadTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);
        assertTrue(new File(personToDownloadTimetable.getStoredLocation()).exists());
    }

    @Test
    void downloadTimetableDuplicateFailure() {
        Person personToDownloadTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        File timetable = new File(personToDownloadTimetable.getStoredLocation());
        if (!timetable.exists()) {
            personToDownloadTimetable.getTimetable().downloadTimetableAsCsv();
        }
        DownloadTimetableCommand downloadTimetableCommand = new DownloadTimetableCommand(
            INDEX_FIRST);
        assertCommandFailure(downloadTimetableCommand, model, commandHistory,
            MESSAGE_TIMETABLE_IS_PRESENT);
        assertTrue(new File(personToDownloadTimetable.getStoredLocation()).exists());
    }
}
