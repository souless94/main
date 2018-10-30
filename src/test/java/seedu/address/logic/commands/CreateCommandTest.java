package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Accounts;

public class CreateCommandTest {

    private String testUsername = "test-username";
    private String testPassword = "test-password";
    private Accounts account = new Accounts(testUsername, testPassword);

    @Test
    public void executeCreateSuccess() {
        CreateCommand create = new CreateCommand(account);
        assertTrue(create.getCreateIsSuccessful());
    }

    @Test
    public void executeCreateFailure() {
        CreateCommand create = new CreateCommand(account);
        CreateCommand createDuplicate = new CreateCommand(account);
        assertFalse(createDuplicate.getCreateIsSuccessful());
    }


}

