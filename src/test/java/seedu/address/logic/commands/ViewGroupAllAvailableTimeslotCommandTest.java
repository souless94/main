package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewGroupAllAvailableTimeslotCommand.
 */
public class ViewGroupAllAvailableTimeslotCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void executeViewSlotsAllWithNobodyInGroup() {
        GroupBuilder testGroupBuilder = new GroupBuilder();
        Group testGroup = testGroupBuilder.withName("testGroup").build();
        model.add(testGroup);
        assertCommandSuccess(new ViewGroupAllAvailableTimeslotCommand(testGroup.getName()), model, commandHistory,
                ViewGroupAllAvailableTimeslotCommand.MESSAGE_SUCCESS
                        + testGroup.listAllAvailableTimeslots(), expectedModel);
    }

    @Test
    public void executeViewSlotsAllWithFreeMembers() {
    	Group testGroupWithMembers = new GroupBuilder().withName("testGroup").withSampleMembers().build();
        model.add(testGroupWithMembers);
        assertCommandSuccess(new ViewGroupAllAvailableTimeslotCommand(testGroupWithMembers.getName()), model, commandHistory,
                ViewGroupAllAvailableTimeslotCommand.MESSAGE_SUCCESS
                        + testGroupWithMembers.listAllAvailableTimeslots(), expectedModel);
    }
}
