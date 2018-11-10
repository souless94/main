package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UniqueList;
import seedu.address.model.exceptions.DuplicateElementException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Adds an existing person to an existing group in the address book.
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String ALIAS = "r";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an existing person to a group "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_NAME + " GROUP NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Family ";

    public static final String MISSING_GROUP_NAME = "Please enter group name.";
    public static final String MESSAGE_SUCCESS = "Added member to group: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the group.";


    private final Index index;
    private final Name groupName;

    /**
     * @param groupName of the group in the filtered group list to edit
     * @param index of the person to be added to the group.
     */
    public RegisterCommand(Name groupName, Index index) {
        requireNonNull(groupName);
        requireNonNull(index);
        this.groupName = groupName;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Group groupToBeEdited = CommandUtil.retrieveGroupFromName(model, groupName);

        Person personToAdd = CommandUtil.retrievePersonFromIndex(model, index);

        try {
            addMemberToGroup(model, groupToBeEdited, personToAdd);
            model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, personToAdd));

        } catch (DuplicateElementException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    /**
     * Create {@code newGroup} by adding a new member {@code personToAdd} into {@code groupToBeEdited}.
     * Create {@code newPerson} by adding {@code groupToBeEdited} into the list of groups inside
     * {@code personToAdd}.
     * Updates the {@code model} with the updated group {@code newGroup} and updated person {@code personToAdd}.
     */
    public static void addMemberToGroup(Model model, Group groupToBeEdited, Person personToAdd)
            throws DuplicateElementException {
        assert groupToBeEdited != null;

        List<Group> groupList = new ArrayList<>();
        groupList.add(groupToBeEdited);
        groupList.addAll(personToAdd.getGroups());
        Person newPerson = personToAdd.defensiveSetGroups(groupList);

        UniqueList<Person> newGroupMembers = new UniqueList<>();
        newGroupMembers.setElements(groupToBeEdited.getGroupMembers());
        newGroupMembers.add(personToAdd);
        Group newGroup = new Group(groupToBeEdited.getName(), groupToBeEdited.getDescription(), newGroupMembers);

        model.update(groupToBeEdited, newGroup);
        model.update(personToAdd, newPerson);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RegisterCommand)) {
            return false;
        }

        // state check
        RegisterCommand e = (RegisterCommand) other;
        return groupName.equals(e.groupName)
                && index.equals(e.index);
    }
}
