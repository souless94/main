package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_BESTFRIEND;
import static seedu.address.testutil.TypicalGroups.BESTFRIENDS;
import static seedu.address.testutil.TypicalGroups.FAMILY;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.GroupBuilder;

public class GroupTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Group group = new GroupBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        group.getGroupMembers().remove(0);
    }

    @Test
    public void isSameGroup() {
        // same object -> returns true
        assertTrue(FAMILY.isSame(FAMILY));

        // null -> returns false
        assertFalse(FAMILY.isSame(null));

        // same name but different group Members -> returns true
        Group editedFamily = new GroupBuilder(FAMILY).withSampleMembers().build();
        assertTrue(FAMILY.isSame(editedFamily));

        // same name but different description -> returns true
        editedFamily = new GroupBuilder(FAMILY).withDescription("").build();
        assertTrue(FAMILY.isSame(editedFamily));

        // different name, same description, same group members -> returns false
        editedFamily = new GroupBuilder(FAMILY).withName(VALID_GROUP_NAME_BESTFRIEND).build();
        assertFalse(FAMILY.isSame(editedFamily));

        // different name, different description, different group members -> returns false
        editedFamily = new GroupBuilder(FAMILY).withName(VALID_GROUP_NAME_BESTFRIEND)
                .withDescription(VALID_GROUP_DESCRIPTION)
                .withSampleMembers().build();
        assertFalse(FAMILY.isSame(editedFamily));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(FAMILY.equals(FAMILY));

        // null -> returns false
        assertFalse(FAMILY.equals(null));

        // different type -> returns false
        assertFalse(FAMILY.equals(5));
        assertFalse(FAMILY.equals("FAMILY"));

        // different groups -> returns false
        assertFalse(FAMILY.equals(BESTFRIENDS));

        // same name but different group Members -> returns true
        Group editedFamily = new GroupBuilder(FAMILY).withSampleMembers().build();
        assertTrue(FAMILY.equals(editedFamily));

        // same name but different description -> returns true
        editedFamily = new GroupBuilder(FAMILY).withDescription("").build();
        assertTrue(FAMILY.equals(editedFamily));

        // different name, same description, same group members -> returns false
        editedFamily = new GroupBuilder(FAMILY).withName(VALID_GROUP_NAME_BESTFRIEND).build();
        assertFalse(FAMILY.equals(editedFamily));

        // different name, different description, different group members -> returns false
        editedFamily = new GroupBuilder(FAMILY).withName(VALID_GROUP_NAME_BESTFRIEND)
                .withDescription(VALID_GROUP_DESCRIPTION)
                .withSampleMembers().build();
        assertFalse(FAMILY.equals(editedFamily));
    }
}
