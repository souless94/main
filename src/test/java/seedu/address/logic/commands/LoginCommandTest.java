package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.model.person.Accounts;

public class LoginCommandTest {

    private String testUsername = "test-username";
    private String testPassword = "test-password";
    private Accounts account = new Accounts(testUsername, testPassword);
    private CreateCommand create = new CreateCommand(account);

    @Test
    public void execute_login_successful() {
        LoginCommand login = new LoginCommand(account);
        assertTrue(login.getLoginIsSuccessful());
    }

    @Test
    public void execute_loginWithWrongPassword_failure() {
        String rightUsername = "test-username";
        String wrongPassword = "wrong-password";
        Accounts wrongAccount = new Accounts(rightUsername, wrongPassword);

        LoginCommand login = new LoginCommand(wrongAccount);
        assertFalse(login.getLoginIsSuccessful());
    }

    @Test // expects null pointer exception since we are using a username that is not in hashmap
    public void execute_loginWithWrongUsername_failure() {
        String wrongUsername = "wrong-username";
        String rightPassword = "test-password";
        Accounts wrongAccount = new Accounts(wrongUsername, rightPassword);

        assertThrows(NullPointerException.class, (
        ) -> {
            LoginCommand login = new LoginCommand(wrongAccount);
        });
    }


}
