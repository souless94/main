package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.Accounts;
import seedu.address.storage.UserAccountStorage;



//@@author aspiringdevslog

/**
 * Creates a user for address book.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String MESSAGE_SUCCESS = "Login successful!";

    private static boolean loginIsSuccessful = false;

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": Login to NUS Hangs to access various features. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "password ";

    private static final String MESSAGE_FAILURE = "Login failed! "
        + "Please check your username and/or password.";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Login
     */
    public LoginCommand(Accounts account) {

        String username = account.getUsername();
        String password = account.getPassword();

        if (!UserAccountStorage.checkDuplicateUser(username)) {
            loginIsSuccessful = false;
        } else if (UserAccountStorage.checkPasswordMatch(username, password)){
            loginIsSuccessful = true;
            AddressBookParser.updateLoggedOnStatus(true);
        } else {
            loginIsSuccessful = false;
        }

        logger.info("Account Login Status: " + loginIsSuccessful
            + " Username: " + username);
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (loginIsSuccessful == true) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_FAILURE));
        }
    }

    public boolean getLoginIsSuccessful() {
        return loginIsSuccessful;
    }

}
