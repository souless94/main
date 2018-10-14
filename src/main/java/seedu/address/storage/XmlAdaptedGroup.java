package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UniqueList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Group.
 */
public class XmlAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String description;

    @XmlElement
    private List<XmlAdaptedPerson> members = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedGroup. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {
    }

    /**
     * Constructs an {@code XmlAdaptedGroup} with the given group details.
     */
    public XmlAdaptedGroup(String name, String description, List<XmlAdaptedPerson> members) {
        this.name = name;
        this.description = description;
        if (members != null) {
            this.members = new ArrayList<>(members);
        }
    }

    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedGroup(Group source) {
        name = source.getName().fullName;
        description = source.getDescription();
        members = source.getGroupMembers().stream()
            .map(XmlAdaptedPerson::new)
            .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * person
     */
    public Group toModelType() throws IllegalValueException {
        final List<Person> groupMembers = new ArrayList<>();
        for (XmlAdaptedPerson member : members) {
            groupMembers.add(member.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (description == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }

        final String modelDescription = description;

        UniqueList<Person> temp = new UniqueList<>();
        temp.setElements(groupMembers);
        final UniqueList<Person> modelGroupMembers = temp;

        return new Group(modelName, modelDescription, modelGroupMembers);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGroup)) {
            return false;
        }

        XmlAdaptedGroup otherGroup = (XmlAdaptedGroup) other;
        return Objects.equals(name, otherGroup.name)
            && Objects.equals(description, otherGroup.description)
            && Objects.equals(members, otherGroup.members);
    }
}
