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
 * Contains integration tests (interaction with the Model) and unit tests for ViewGroupRankedAvailableTimeslotCommand.
 */
public class ViewGroupRankedAvailableTimeslotCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void executeViewSlotsRankedWithNobodyInGroup() {
        GroupBuilder testGroupBuilder = new GroupBuilder();
        Group testGroup = testGroupBuilder.withName("testGroup").build();
        model.add(testGroup);
        assertCommandSuccess(new ViewGroupRankedAvailableTimeslotCommand(testGroup.getName(), 1), model, commandHistory,
                ViewGroupRankedAvailableTimeslotCommand.MESSAGE_SUCCESS + "1 person(s) available:\n"
                        + testGroup.listRankedAvailableTimeslots(1), expectedModel);
    }

    @Test
    public void executeViewSlotsRankedWithFreeMembers() {
        Group testGroup = new GroupBuilder().withName("testGroup").withSampleMembers().build();
        model.add(testGroup);
        assertCommandSuccess(new ViewGroupRankedAvailableTimeslotCommand(testGroup.getName(), 1), model, commandHistory,
                ViewGroupRankedAvailableTimeslotCommand.MESSAGE_SUCCESS + "1 person(s) available:\n"
                        + testGroup.listRankedAvailableTimeslots(1), expectedModel);
    }
}
