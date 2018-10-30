package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.UniqueList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_NAME = "Developer Team";
    public static final String DEFAULT_DESCRIPTION = "Developers for NUS Hang";

    private Name name;
    private String description;
    private UniqueList<Person> groupMembers;

    public GroupBuilder() {
        name = new Name(DEFAULT_NAME);
        description = DEFAULT_DESCRIPTION;
        groupMembers = new UniqueList<>();
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        name = groupToCopy.getName();
        description = groupToCopy.getDescription();
        groupMembers = new UniqueList<>();
        groupMembers.setElements(groupToCopy.getGroupMembers());
    }

    /**
     * Sets the {@code Name} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Group} that we are building.
     */
    public GroupBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code groupMembers} of the {@code Group} that we are building to list of typical Persons
     * and sample Persons.
     */
    public GroupBuilder withSampleMembers() {
        List<Person> tmp = new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        tmp.addAll(Arrays.asList(SampleDataUtil.getSamplePersons()));
        this.groupMembers = new UniqueList<>();
        this.groupMembers.setElements(tmp);
        return this;
    }

    /**
     * Parses the list of {@code persons} into a {@code UniqueList<Person>} and set it to the {@code Group} that we are
     * building.
     */
    public GroupBuilder withMembers(Person[] persons) {
        List<Person> personsList = Arrays.asList(persons);
        this.groupMembers = new UniqueList<>();
        this.groupMembers.setElements(personsList);
        return this;
    }

    public Group build() {
        return new Group(name, description, groupMembers);
    }

}

