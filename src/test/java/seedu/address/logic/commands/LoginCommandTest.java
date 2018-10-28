package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Accounts;
import seedu.address.testutil.Assert;

public class LoginCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private String TEST_USERNAME = "test-username";
    private String TEST_PASSWORD = "test-password";
    private Accounts account = new Accounts(TEST_USERNAME, TEST_PASSWORD);
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

        assertThrows(NullPointerException.class,
                ( ) -> {
                    LoginCommand login = new LoginCommand(wrongAccount);
                });
    }


}
