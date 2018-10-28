package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import seedu.address.logic.commands.CreateCommand;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Accounts;
import seedu.address.storage.UserAccountStorage;

public class CreateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private String TEST_USERNAME = "test-username";
    private String TEST_PASSWORD = "test-password";
    private Accounts account = new Accounts(TEST_USERNAME, TEST_PASSWORD);

    @Test
    public void execute_create_success(){
        CreateCommand create = new CreateCommand(account);
        assertTrue(create.getCreateIsSuccessful());
    }
    
    @Test
    public void execute_create_failure(){
        CreateCommand create = new CreateCommand(account);
        CreateCommand createDuplicate = new CreateCommand(account);
        assertFalse(createDuplicate.getCreateIsSuccessful());
    }



}

