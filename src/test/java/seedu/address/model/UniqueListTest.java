package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalGroups.BESTFRIENDS;
import static seedu.address.testutil.TypicalGroups.FAMILY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.exceptions.DuplicateElementException;
import seedu.address.model.exceptions.NotFoundException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.PersonBuilder;

public class UniqueListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueList<Person> uniquePersonList = new UniqueList<>();
    private final UniqueList<Group> uniqueGroupList = new UniqueList<>();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePersonList.contains(null);
    }

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.contains(null);
    }

    @Test
    public void contains_personNotInPersonList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_groupNotInGroupList_returnsFalse() {
        assertFalse(uniqueGroupList.contains(FAMILY));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupList.add(FAMILY);
        assertTrue(uniqueGroupList.contains(FAMILY));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void contains_groupWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGroupList.add(FAMILY);
        Group editedFamily = new GroupBuilder(FAMILY).withDescription(VALID_GROUP_DESCRIPTION).build();
        assertTrue(uniqueGroupList.contains(editedFamily));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePersonList.add(null);
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicateElementException() {
        uniquePersonList.add(ALICE);
        thrown.expect(DuplicateElementException.class);
        uniquePersonList.add(ALICE);
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateElementException() {
        uniqueGroupList.add(FAMILY);
        thrown.expect(DuplicateElementException.class);
        uniqueGroupList.add(FAMILY);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePersonList.setElement(null, ALICE);
    }

    @Test
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setElement(null, FAMILY);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePersonList.setElement(ALICE, null);
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setElement(FAMILY, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsNotFoundException() {
        thrown.expect(NotFoundException.class);
        uniquePersonList.setElement(ALICE, ALICE);
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsNotFoundException() {
        thrown.expect(NotFoundException.class);
        uniqueGroupList.setElement(FAMILY, FAMILY);
    }


    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setElement(ALICE, ALICE);
        UniqueList<Person> expectedUniquePersonList = new UniqueList<>();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() {
        uniqueGroupList.add(FAMILY);
        uniqueGroupList.setElement(FAMILY, FAMILY);
        UniqueList<Group> expectedUniqueGroupList = new UniqueList<>();
        expectedUniqueGroupList.add(FAMILY);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setElement(ALICE, editedAlice);
        UniqueList<Person> expectedUniquePersonList = new UniqueList<>();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setGroup_editedGroupHasSameIdentity_success() {
        uniqueGroupList.add(FAMILY);
        Group editedFamily = new GroupBuilder(FAMILY).withDescription(VALID_GROUP_DESCRIPTION).build();
        uniqueGroupList.setElement(FAMILY, editedFamily);
        UniqueList<Group> expectedUniqueGroupList = new UniqueList<>();
        expectedUniqueGroupList.add(editedFamily);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setElement(ALICE, BOB);
        UniqueList<Person> expectedUniquePersonList = new UniqueList<>();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() {
        uniqueGroupList.add(FAMILY);
        uniqueGroupList.setElement(FAMILY, BESTFRIENDS);
        UniqueList<Group> expectedUniqueGroupList = new UniqueList<>();
        expectedUniqueGroupList.add(BESTFRIENDS);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicateElementException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        thrown.expect(DuplicateElementException.class);
        uniquePersonList.setElement(ALICE, BOB);
    }

    @Test
    public void setGroup_editedGroupHasNonUniqueIdentity_throwsDuplicateElementException() {
        uniqueGroupList.add(FAMILY);
        uniqueGroupList.add(BESTFRIENDS);
        thrown.expect(DuplicateElementException.class);
        uniqueGroupList.setElement(FAMILY, BESTFRIENDS);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePersonList.remove(null);
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsNotFoundException() {
        thrown.expect(NotFoundException.class);
        uniquePersonList.remove(ALICE);
    }

    @Test
    public void remove_groupDoesNotExist_throwsNotFoundException() {
        thrown.expect(NotFoundException.class);
        uniqueGroupList.remove(FAMILY);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniqueList<Person> expectedUniquePersonList = new UniqueList<>();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        uniqueGroupList.add(FAMILY);
        uniqueGroupList.remove(FAMILY);
        UniqueList<Person> expectedUniqueGroupList = new UniqueList<>();
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePersonList.setElements((UniqueList<Person>) null);
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setElements((UniqueList<Group>) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniqueList<Person> expectedUniquePersonList = new UniqueList<>();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setElements(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setGroups_uniqueGroupList_replacesOwnListWithProvidedUniqueGroupList() {
        uniqueGroupList.add(FAMILY);
        UniqueList<Group> expectedUniqueGroupList = new UniqueList<>();
        expectedUniqueGroupList.add(BESTFRIENDS);
        uniqueGroupList.setElements(expectedUniqueGroupList);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePersonList.setElements((List<Person>) null);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setElements((List<Group>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniquePersonList.setElements(personList);
        UniqueList<Person> expectedUniquePersonList = new UniqueList<>();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupList.add(FAMILY);
        List<Group> groupList = Collections.singletonList(BESTFRIENDS);
        uniqueGroupList.setElements(groupList);
        UniqueList<Group> expectedUniqueGroupList = new UniqueList<>();
        expectedUniqueGroupList.add(BESTFRIENDS);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicateElementException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateElementException.class);
        uniquePersonList.setElements(listWithDuplicatePersons);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateElementException() {
        List<Group> listWithDuplicateGroups = Arrays.asList(FAMILY, FAMILY);
        thrown.expect(DuplicateElementException.class);
        uniqueGroupList.setElements(listWithDuplicateGroups);
    }

    @Test
    public void asUnmodifiableObservableList_modifyPersonList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void asUnmodifiableObservableList_modifyGroupList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asUnmodifiableObservableList().remove(0);
    }
}
