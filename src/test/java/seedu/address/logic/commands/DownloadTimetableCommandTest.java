package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
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
            .get(INDEX_FIRST_PERSON.getZeroBased());
        DownloadTimetableCommand downloadTimetableCommand = new DownloadTimetableCommand(
            INDEX_FIRST_PERSON);
        String expectedMessage = String
            .format(DownloadTimetableCommand.MESSAGE_DOWNLOAD_TIMETABLE_SUCCESS,
                personToDownloadTimetable);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToDownloadTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(downloadTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);

        assertTrue(new File(personToDownloadTimetable.getStoredLocation()).exists());
    }
}
