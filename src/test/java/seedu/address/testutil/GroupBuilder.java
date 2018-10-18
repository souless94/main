package seedu.address.testutil;

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

    private static final String DEFAULT_NAME = "Developer Team";
    private static final String DEFAULT_DESCRIPTION = "Developers for NUS Hang";

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
     * Sets the {@code groupMembers} of the {@code Group} that we are building to list of sample Persons.
     */
    public GroupBuilder withSampleMembers() {
        List<Person> tmp = Arrays.asList(SampleDataUtil.getSamplePersons());
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

