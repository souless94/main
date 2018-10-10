package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

class AddTimetableCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    void execute_timetableAcceptedByModel_addNewHorizontalSuccess() {
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST_PERSON.getZeroBased());
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String
            .format(AddTimetableCommand.MESSAGE_ADD_TIMETABLE_SUCCESS, personToAddTimetable);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToAddTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

}
