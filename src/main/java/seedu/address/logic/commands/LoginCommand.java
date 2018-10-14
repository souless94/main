package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Accounts;
import seedu.address.storage.UserAccountStorage;

/**
 * Creates a user for address book.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    private static boolean loginIsSuccessful = false;

    //TODO: update MESSAGE_USAGE
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "password ";

    private static final String MESSAGE_SUCCESS = "Login successful!";
    private static final String MESSAGE_FAILURE = "Login failed!";
    //TODO: throw exception message

    /**
     * Login
     */
    public LoginCommand(Accounts account) {
        if (UserAccountStorage.checkPasswordMatch(account.getUsername(), account.getPassword())) {
            loginIsSuccessful = true;
        } else {
            loginIsSuccessful = false;
        }
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
}
