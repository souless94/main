package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        List<String> name1Keywords = Arrays.asList("Alex");
        List<String> name2Keywords = Arrays.asList("Betty");
        List<String> phone1Keywords = Arrays.asList("91234567");
        List<String> phone2Keywords = Arrays.asList("92143123");
        List<String> address1Keywords = Arrays.asList("BLK 123 TreePass Street #12-123");
        List<String> address2Keywords = Arrays.asList("BLK 321 Biscuit Street #19-03");
        List<String> email1Keywords = Arrays.asList("alex@example.com");
        List<String> email2Keywords = Arrays.asList("betty@example.com");
        List<String> tag1Keywords = Arrays.asList("friends", "Boy");
        List<String> tag2Keywords = Arrays.asList("friends", "Girl");

        FindCommand findFirstCommand = new FindCommand(
                name1Keywords, phone1Keywords, address1Keywords, email1Keywords, tag1Keywords);

        FindCommand findSecondCommand = new FindCommand(
                name2Keywords, phone2Keywords, address2Keywords, email2Keywords, tag2Keywords);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(
                name1Keywords, phone1Keywords, address1Keywords, email1Keywords, tag1Keywords);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    //@Test
    //public void execute_zeroKeywords_noPersonFound() {
    //    String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
    //    NameContainsKeywordsPredicate predicate = preparePredicate(" ");
    //    FindCommand command = new FindCommand(predicate);
    //    expectedModel.updateFilteredPersonList(predicate);
    //    assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    //    assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    //}

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        //NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        //FindCommand command = new FindCommand(predicate);

        FindCommand command = prepareFindCommand(
                "Alice", "", "", "", "owesMoney");

        NameContainsKeywordsPredicate predicate = preparePredicate(
                "Alice", "", "", "", "owesMoney");

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    //private NameContainsKeywordsPredicate preparePredicate(String userInput) {
    //    return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    //}

    /**
     * Parses {@code nameInput, phoneInput, addressInput, emailInput, tagInput}
     * into a {@code FindCommand}.
     */
    private FindCommand prepareFindCommand(String name, String phone,
                                           String address, String email, String tag) {

        String[] nameKeywords;
        String[] phoneKeywords;
        String[] addressKeywords;
        String[] emailKeywords;
        String[] tagKeywords;

        if (!name.equals("")) {
            nameKeywords = name.split("\\s+");
        } else {
            nameKeywords = new String[0];
        }

        if (!phone.equals("")) {
            phoneKeywords = phone.split("\\s+");
        } else {
            phoneKeywords = new String[0];
        }

        if (!address.equals("")) {
            addressKeywords = address.split("\\s+");
        } else {
            addressKeywords = new String[0];
        }

        if (!email.equals("")) {
            emailKeywords = email.split("\\s+");
        } else {
            emailKeywords = new String[0];
        }

        if (!tag.equals("")) {
            tagKeywords = tag.split("\\s+");
        } else {
            tagKeywords = new String[0];
        }

        return new FindCommand(Arrays.asList(nameKeywords),
                Arrays.asList(phoneKeywords), Arrays.asList(addressKeywords),
                Arrays.asList(emailKeywords), Arrays.asList(tagKeywords));
    }

    /**
     * Parses {@code nameInput, phoneInput, addressInput, emailInput, tagInput}
     * into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String name, String phone,
                                                           String address, String email, String tag) {

        String[] nameKeywords;
        String[] phoneKeywords;
        String[] addressKeywords;
        String[] emailKeywords;
        String[] tagKeywords;

        if (!name.equals("")) {
            nameKeywords = name.split("\\s+");
        } else {
            nameKeywords = new String[0];
        }

        if (!phone.equals("")) {
            phoneKeywords = phone.split("\\s+");
        } else {
            phoneKeywords = new String[0];
        }

        if (!address.equals("")) {
            addressKeywords = address.split("\\s+");
        } else {
            addressKeywords = new String[0];
        }

        if (!email.equals("")) {
            emailKeywords = email.split("\\s+");
        } else {
            emailKeywords = new String[0];
        }

        if (!tag.equals("")) {
            tagKeywords = tag.split("\\s+");
        } else {
            tagKeywords = new String[0];
        }

        return new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                Arrays.asList(phoneKeywords), Arrays.asList(addressKeywords),
                Arrays.asList(emailKeywords), Arrays.asList(tagKeywords),
                "all");
    }

}
