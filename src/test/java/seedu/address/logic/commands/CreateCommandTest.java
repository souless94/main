package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Accounts;

public class CreateCommandTest {

    private String TEST_USERNAME = "test-username";
    private String TEST_PASSWORD = "test-password";
    private Accounts account = new Accounts(TEST_USERNAME, TEST_PASSWORD);

    @Test
    public void execute_create_success() {
        CreateCommand create = new CreateCommand(account);
        assertTrue(create.getCreateIsSuccessful());
    }

    @Test
    public void execute_create_failure() {
        CreateCommand create = new CreateCommand(account);
        CreateCommand createDuplicate = new CreateCommand(account);
        assertFalse(createDuplicate.getCreateIsSuccessful());
    }


}

