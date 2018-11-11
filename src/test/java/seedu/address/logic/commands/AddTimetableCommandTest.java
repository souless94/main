package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddTimetableCommand.MESSAGE_INVALID_TIMETABLE;
import static seedu.address.logic.commands.AddTimetableCommand.MESSAGE_INVALID_TIMETABLE_SIZE;
import static seedu.address.logic.commands.AddTimetableCommand.MESSAGE_TIMETABLE_NOT_FOUND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.PersonBuilder.DEFAULT_STORED_INVALID_TIMETABLE_LOCATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.FileWriter;

import org.junit.Test;

import com.opencsv.CSVWriter;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;


public class AddTimetableCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeTimetableAcceptedByModelAddSuccess() {
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        personToAddTimetable.getTimetable().downloadTimetableAsCsv();
        File timetable = new File(personToAddTimetable.getStoredLocation());
        assertTrue(timetable.exists());
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST, null);
        String expectedMessage = String
            .format(AddTimetableCommand.MESSAGE_ADD_TIMETABLE_SUCCESS,
                personToAddTimetable.getStoredLocation());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());

        expectedModel.update(model.getFilteredPersonList().get(0), personToAddTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

    @Test
    public void executeTimetableAcceptedByModelAddByFileLocationSuccess() {
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        personToAddTimetable.getTimetable().downloadTimetableAsCsv();
        File timetable = new File(personToAddTimetable.getStoredLocation());
        assertTrue(timetable.exists());
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST,
            personToAddTimetable.getStoredLocation());
        String expectedMessage = String
            .format(AddTimetableCommand.MESSAGE_ADD_TIMETABLE_SUCCESS,
                personToAddTimetable.getStoredLocation());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());

        expectedModel.update(model.getFilteredPersonList().get(0), personToAddTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }


    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(outOfBoundIndex,
            null);

        assertCommandFailure(addTimetableCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeTimetableNotFoundAddFailure() {
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        File timetable = new File(personToAddTimetable.getStoredLocation());
        if (timetable.exists()) {
            timetable.delete();
        }
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST, null);
        assertCommandFailure(addTimetableCommand, model, commandHistory,
            MESSAGE_TIMETABLE_NOT_FOUND + personToAddTimetable.getStoredLocation());
    }

    @Test
    public void executeHorizontalTimetableAddWrongRowSizeAddFailure() {
        String wrongTimetableLocation = DEFAULT_STORED_INVALID_TIMETABLE_LOCATION
            + "/" + "1663619914 timetable - wrongrowsize.csv";
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        File timetable = new File(wrongTimetableLocation);
        if (timetable.exists()) {
            timetable.delete();
        }
        try {
            FileWriter fileWriter = new FileWriter(timetable);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            for (int i = 0; i < 8; i++) {
                csvWriter.writeNext(personToAddTimetable.getTimetable().getTimetableMatrix()[i]);
            }
            csvWriter.writeNext(personToAddTimetable.getTimetable().getTimetableMatrix()[0]);
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST,
            wrongTimetableLocation);
        assertCommandFailure(addTimetableCommand, model, commandHistory,
            MESSAGE_INVALID_TIMETABLE_SIZE);
    }

    @Test
    public void executeTimetableAddWrongColumnSizeAddFailure() {
        String wrongTimetableLocation = DEFAULT_STORED_INVALID_TIMETABLE_LOCATION
            + "/" + "1663619914 timetable - wrongcolumnsize.csv";
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        File timetable = new File(wrongTimetableLocation);
        if (timetable.exists()) {
            timetable.delete();
        }
        try {
            FileWriter fileWriter = new FileWriter(timetable);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            String[] wrongEntry = new String[18];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 17; j++) {
                    wrongEntry[j] = personToAddTimetable.getTimetable()
                        .getTimetableMatrix()[i][j];
                }
                wrongEntry[17] = "test";
                csvWriter.writeNext(wrongEntry);
            }
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST,
            wrongTimetableLocation);
        assertCommandFailure(addTimetableCommand, model, commandHistory,
            MESSAGE_INVALID_TIMETABLE_SIZE);
    }

    @Test
    public void executeTimetableAddWrongFirstColumnAddFailure() {
        String wrongTimetableLocation = DEFAULT_STORED_INVALID_TIMETABLE_LOCATION
            + "/" + "1663619914 timetable - wrongdays.csv";
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        File timetable = new File(wrongTimetableLocation);
        if (timetable.exists()) {
            timetable.delete();
        }
        try {
            FileWriter fileWriter = new FileWriter(timetable);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            String[] wrongEntry = new String[17];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 17; j++) {
                    wrongEntry[j] = personToAddTimetable.getTimetable()
                        .getTimetableMatrix()[i][j];
                }
                if (i == 5) {
                    wrongEntry[0] = "firday";
                }
                csvWriter.writeNext(wrongEntry);
            }
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST,
            wrongTimetableLocation);
        assertCommandFailure(addTimetableCommand, model, commandHistory,
            MESSAGE_INVALID_TIMETABLE);
    }

    @Test
    public void executeTimetableAddWrongFirstRowAddFailure() {
        String wrongTimetableLocation = DEFAULT_STORED_INVALID_TIMETABLE_LOCATION
            + "/" + "1663619914 timetable - wrongtimings.csv";
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        File timetable = new File(wrongTimetableLocation);
        if (timetable.exists()) {
            timetable.delete();
        }
        try {
            FileWriter fileWriter = new FileWriter(timetable);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            String[] wrongFirstRow = personToAddTimetable.getTimetable().getTimetableMatrix()[0];
            wrongFirstRow[2] = "magic";
            csvWriter.writeNext(wrongFirstRow);
            for (int i = 1; i < 8; i++) {
                csvWriter.writeNext(personToAddTimetable.getTimetable().getTimetableMatrix()[i]);
            }
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST,
            wrongTimetableLocation);
        assertCommandFailure(addTimetableCommand, model, commandHistory,
            MESSAGE_INVALID_TIMETABLE);
    }
}
