package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;


/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueList<Person> persons;
    private final UniqueList<Group> groups;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     *
     */
    {
        persons = new UniqueList<>();
        groups = new UniqueList<>();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //@@author Happytreat
    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address
     * book.
     */
    public void remove(Entity key) {
        if (key instanceof Group) {
            groups.remove((Group) key);
        } else if (key instanceof Person) {
            persons.remove((Person) key);
        }
    }

    /**
     * Adds {@code key} from this {@code AddressBook}. {@code key} must exist in the address
     * book.
     */
    public void add(Entity key) {
        if (key instanceof Group) {
            groups.add((Group) key);
        } else if (key instanceof Person) {
            persons.add((Person) key);
        }
    }

    /**
     * Returns true if an entity with the same identity as {@code key} exists in the address
     * book.
     */
    public boolean has(Entity key) {
        requireNonNull(key);
        if (key instanceof Group) {
            return groups.contains((Group) key);
        } else if (key instanceof Person) {
            return persons.contains((Person) key);
        }

        return false;
    }

    /**
     * Replaces the given entity {@code target} in the list with {@code edited}. {@code
     * target} must exist in the address book. The identity of {@code edited} must not
     * be the same as another existing entity in the address book.
     */
    public void update(Entity target, Entity edited) {
        requireNonNull(edited);

        if (target instanceof Group && edited instanceof Group) {
            groups.setElement((Group) target, (Group) edited);
        } else if (target instanceof Person && edited instanceof Person) {
            persons.setElement((Person) target, (Person) edited);
        }
    }

    /**
     * Replaces the contents of the group list with {@code group}. {@code groups} must not contain
     * duplicate group.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setElements(groups);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not
     * contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setElements(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setGroups(newData.getGroupList());
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons"
                + "and " + groups.asUnmodifiableObservableList().size() + "groups";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddressBook // instanceof handles nulls
            && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
