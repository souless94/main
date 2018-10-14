package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group FAMILY = new GroupBuilder().withName("Family")
            .withDescription("Family Group")
            .withMembers(new Person[]{ALICE, BOB, BENSON, DANIEL})
            .build();

    public static final Group BESTFRIENDS = new GroupBuilder().withName("Best Friends")
            .withDescription("Best Friend Group")
            .withMembers(new Person[]{ALICE, BOB, BENSON, DANIEL})
            .build();

    public static final String KEYWORD_MATCHING_BEST = "Best"; // A keyword that matches best

    private TypicalGroups() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        ab.addPerson(ALICE);
        ab.addPerson(BOB);
        ab.addPerson(BENSON);
        ab.addPerson(DANIEL);
        List<Person> samplePersons = Arrays.asList(SampleDataUtil.getSamplePersons());
        for (Person person : samplePersons) {
            ab.addPerson(person);
        }

        for (Group group : getTypicalGroups()) {
            ab.addGroup(group);
        }
        return ab;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(FAMILY, BESTFRIENDS));
    }
}

