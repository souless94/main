package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndexes;
import static seedu.address.testutil.TypicalGroups.FAMILY;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewGroupCommand.
 */
public class ViewGroupCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_viewGroupWithAllPersonsInAddressBook_showsSamePersonList() {
        Group groupWithAllPersons = new GroupBuilder().withName("AllPersonGroup").withSampleMembers().build();
        model.add(groupWithAllPersons);
        assertCommandSuccess(new ViewGroupCommand(groupWithAllPersons.getName()), model, commandHistory,
                ViewGroupCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_viewGroupWithNotAllPersonsInAddressBook() {
        Group family = new GroupBuilder(FAMILY).build();
        // First 7 people are in FAMILY group
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            indexes.add(i);
        }

        showPersonAtIndexes(expectedModel, indexes);
        assertCommandSuccess(new ViewGroupCommand(family.getName()), model, commandHistory,
                ViewGroupCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
