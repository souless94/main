package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.EditGroupCommand.EditGroupDescriptor;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building EditGroupDescriptor objects.
 */
public class EditGroupDescriptorBuilder {

    private EditGroupDescriptor descriptor;

    public EditGroupDescriptorBuilder() {
        descriptor = new EditGroupDescriptor();
    }

    public EditGroupDescriptorBuilder(EditGroupDescriptor descriptor) {
        this.descriptor = new EditGroupDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGroupDescriptor} with fields containing {@code group}'s details
     */
    public EditGroupDescriptorBuilder(Group group) {
        descriptor = new EditGroupDescriptor();
        descriptor.setName(group.getName());
        descriptor.setDescription(group.getDescription());
        descriptor.setGroupMembers(group.getGroupMembers());
    }

    /**
     * Sets the {@code Name} of the {@code EditGroupDescriptor} that we are building.
     */
    public EditGroupDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the description of the {@code EditGroupDescriptor} that we are building.
     */
    public EditGroupDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * Sets the list of group Members of the {@code EditGroupDescriptor} that we are building.
     */
    public EditGroupDescriptorBuilder withGroupMembers(Person[] members) {
        descriptor.setGroupMembers(Arrays.asList(members));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditGroupDescriptor} that we are building.
     */
    public EditGroupDescriptorBuilder withGroupMembers(List<Person> members) {
        descriptor.setGroupMembers(members);
        return this;
    }


    public EditGroupDescriptor build() {
        return descriptor;
    }
}
