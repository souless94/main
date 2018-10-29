package seedu.address.model.person.timetable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Test;

public class TimetableTest {

    private Timetable timetable = ALICE.getTimetable();

    @Test
    public void isSameTimetable() {

        // same object -> returns true
        assertTrue(timetable.isSame(timetable));

        // null -> returns false
        assertFalse(timetable.isSame(null));

    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(timetable.equals(timetable));

        // null -> returns false
        assertNotEquals(timetable, null);

        // different type -> returns false
        assertFalse(timetable.equals(5));


    }

    @Test
    public void checkProperties() {

        // check if timetable has correct size
        assertTrue(timetable.isCorrectSize());

        // check if timetable has correct first Rows and Columns
        assertTrue(timetable.hasCorrectFirstRowsAndColumns());
    }
}
