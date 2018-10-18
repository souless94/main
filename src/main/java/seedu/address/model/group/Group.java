package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Collections;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toMap;



import javafx.collections.ObservableList;
import seedu.address.model.Entity;
import seedu.address.model.UniqueList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

//@@author Happytreat
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

    private String dayToString(int day) {
        switch (day) {
            case 1:
                return "Monday";

            case 2:
                return "Tuesday";

            case 3:
                return "Wednesday";

            case 4:
                return "Thursday";

            case 5:
                return "Friday";

            case 6:
                return "Saturday";

            case 7:
                return "Sunday";

            default:
                return "Invalid day";
        }
    }

    /**
     * Returns all available time slots among the group as a String in ascending order in terms of timing
     */
    public String listAvailableTimeslots() {
        Iterator<Person> person_itr = groupMembers.iterator();
        StringBuilder builder = new StringBuilder();
        TreeSet<Integer> available_slots = new TreeSet<>();
        Iterator<Integer> slots_itr = available_slots.iterator();
        boolean isFirstPerson = true;
        while (person_itr.hasNext()) {
            Person curr_person = person_itr.next();
            boolean[][] isFree = curr_person.getTimetable().getTimetable().getBooleanTimetable();
            if (isFirstPerson) {
                for (int i = 1; i <= 7; i++) {
                    for (int j = 1; j <= 16; j++) {
                        if (isFree[i][j]) {
                            available_slots.add(i * 100 + j);
                        }
                    }
                }
                isFirstPerson = false;
            }
            else {
                while (slots_itr.hasNext()) {
                    int curr_timeslot = slots_itr.next();
                    int row = curr_timeslot / 100;
                    int col = curr_timeslot % 100;
                    if (!isFree[row][col]) {
                        available_slots.remove(curr_timeslot);
                    }
                    if (available_slots.isEmpty()) {
                        return "There are no available slots!";
                    }
                }
            }
        }
        slots_itr = available_slots.iterator();
        while (slots_itr.hasNext()) {
            int curr_timeslot_2 = slots_itr.next();
            int day = curr_timeslot_2 / 100;
            int timing = (curr_timeslot_2 % 100 + 7) * 100;
            builder.append("Day: ");
            builder.append(dayToString(day));            
            builder.append(" ").append("Time: ").append(Integer.toString(timing)).append("\n");
        }
        return builder.toString();
    }
    /**
     * Returns the time slots among the group as a String in descending order with respect to number of people available and then ascending order in terms of timing
     */
    public String listRankedAvailableTimeslots () {
        Iterator<Person> person_itr = groupMembers.iterator();
        StringBuilder builder = new StringBuilder();
        TreeMap<Integer, Integer> available_slots = new TreeMap<>();
        while (person_itr.hasNext()) {
            Person curr_person = person_itr.next();
            boolean[][] isFree = curr_person.getTimetable().getTimetable().getBooleanTimetable();
            for (int i = 1; i <= 7; i++) {
                for (int j = 1; j <= 16; j++) {
                    if (isFree[i][j]) {
                        int slot = i * 100 + j;
                        if (available_slots.containsKey(slot)) {
                            int count = available_slots.get(slot) + 1;
                            available_slots.put(slot, count);
                        }
                        else {
                            available_slots.put(slot, 1);
                        }
                    }
                }
            }
        }
        Map<Integer, Integer> sorted_slots = available_slots.entrySet().stream()
                                                            .sorted(Collections.reverseOrder(comparing(Entry::getValue)))
                                                            .collect(toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        int prev = 0;
        for (Integer key : sorted_slots.keySet()) {
            int curr_timeslot = key;
            int day = curr_timeslot / 100;
            int timing = (curr_timeslot % 100 + 7) * 100;
            int available_persons = sorted_slots.get(key);
            if (available_persons != prev) {
                builder.append("Number of people available: " + available_persons + "\n");
                prev = available_persons;
            }
            builder.append("Day: ");
            builder.append(dayToString(day));
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
