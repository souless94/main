package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Accounts;
import seedu.address.storage.UserAccountStorage;



//@@author aspiringdevslog

/**
 * Creates a account for NUS Hangs.
 */
public class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";
    public static final String MESSAGE_SUCCESS = "New user added successfully!";
    public static final String MESSAGE_FAILURE = "Username already exist.";

    private static boolean createIsSuccessful = false;


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an account in NUS Hangs. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "password ";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Creates an CreateCommand to add the specified {@code Account}
     */
    public CreateCommand(Accounts account) {

        String username = account.getUsername();
        String password = account.getPassword();

        if (!UserAccountStorage.checkDuplicateUser(username)) {
            UserAccountStorage.addNewAccount(username, password);
            createIsSuccessful = true;
        } else {
            createIsSuccessful = false;
        }

        logger.info("Account Creation Status: " + createIsSuccessful
            + " Username chosen: " + username
            + " Password Length: " + password.length());

    }

    public boolean getCreateIsSuccessful() {
        return createIsSuccessful;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (createIsSuccessful == true) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_FAILURE));
        }
    }


}
