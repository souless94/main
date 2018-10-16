package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.Objects;
import java.util.HashSet;

import javafx.collections.ObservableList;
import seedu.address.model.Entity;
import seedu.address.model.UniqueList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.person.timetable.TimetableData;

/**
 * Represents a Group in the address book.
 * Guarantees: Field values are validated, immutable.
 */
public class Group extends Entity {

    public static final String MESSAGE_GROUP_NO_DESCRIPTION =
            "No group description has been inputted.";

    /**Groups must have unique names.*/
    private final Name name;

    // Data fields
    private final String description;
    private UniqueList<Person> groupMembers;

    /**
     * Every field must be present and not null.
     */
    public Group(Name name, String description) {
        requireAllNonNull(name, description);
        this.name = name;
        this.description = description;
        groupMembers = new UniqueList<>();
    }

    public Group(Name name, String description, UniqueList<Person> groupMembers) {
        requireAllNonNull(name, description, groupMembers);
        this.name = name;
        this.description = description;
        this.groupMembers = groupMembers;
    }

    public Name getName() {
        return name;
    }

    public String getDescription() {
        if (description.equals("")) {
            return MESSAGE_GROUP_NO_DESCRIPTION;
        }
        return description;
    }

    public ObservableList<Person> getGroupMembers() {
        return groupMembers.asUnmodifiableObservableList();
    }

    /**
     * Returns all member of a group as a String.
     */
    public String printMembers() {
        Iterator<Person> itr = groupMembers.iterator();
        StringBuilder builder = new StringBuilder();
        int count = 1;
        while (itr.hasNext()) {
            builder.append(count).append(". ").append(itr.next().getName().fullName).append("\n");
            count += 1;
        }
        return builder.toString();
    }

    /**
     * Returns all available time slots among the group as a String.
     */
    public String findAvailableSlots() {
        Iterator<Person> itr = groupMembers.iterator();
        StringBuilder builder = new StringBuilder();
        HashSet<Integer> available = new HashSet<>();
        Iterator<Integer> itr2 = available.iterator();
        boolean isFirstPerson = true;
        while (itr.hasNext()) {
            Person person = itr.next();
            boolean[][] isFree = person.getTimetable().getTimetable().getBooleanTimetable();
            if (isFirstPerson) {
                for (int i = 1; i <= 7; i++) {
                    for (int j = 1; j <= 16; j++) {
                        if (isFree[i][j]) {
                            available.add(i * 100 + j);
                        }
                    }
                }
                isFirstPerson = false;
            }
            else {
                while (itr2.hasNext()) {
                    int timeslot = itr2.next();
                    int row = timeslot / 100;
                    int col = timeslot % 100;
                    if (!isFree[row][col]) {
                        available.remove(timeslot);
                    }
                    if (available.isEmpty()) {
                        return "There are no available slots!";
                    }
                }
            }
        }
        itr2 = available.iterator();
        while (itr2.hasNext()) {
            int timeslot = itr2.next();
            int day = timeslot / 100;
            int timing = (timeslot % 100 + 7) * 100;
            builder.append("Day: ");
            switch (day) {
            case 1:
                builder.append("Monday");
                break;

            case 2:
                builder.append("Tuesday");
                break;

            case 3:
                builder.append("Wednesday");
                break;

            case 4:
                builder.append("Thursday");
                break;

            case 5:
                builder.append("Friday");
                break;

            case 6:
                builder.append("Saturday");
                break;

            case 7:
                builder.append("Sunday");
                break;

            default:
                builder.append("Invalid day");
                break; 
            }
            builder.append(" ").append("Time: ").append(Integer.toString(timing)).append("\n");
        }
        return builder.toString();
    }

    /**
     * Returns true if both groups of the same name.
     * For group, isSame is the same function as equals
     * since groups are uniquely identified by their names.
     */
    @Override
    public boolean isSame(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getName().equals(getName());
    }

    /**
     * Same as isSame because Groups are uniquely identified by their names.
     * TO NOTE: Used when deleting groups.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nDescription: ")
                .append(getDescription())
                .append("\nNumber of Members: ")
                .append(groupMembers.asUnmodifiableObservableList().size());
        return builder.toString();
    }
}
