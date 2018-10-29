package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Accounts;
import seedu.address.storage.UserAccountStorage;

//@@author aspiringdevslog

/**
 * Creates a user for address book.
 */
public class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";
    public static final String MESSAGE_SUCCESS = "New user added successfully!";
    public static final String MESSAGE_FAILURE = "Username already exist.";

    private static boolean createIsSuccessful = false;
    private static Accounts newAccount;

    //TODO: update MESSAGE_USAGE
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an account in NUS Hangs. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "password ";


    /**
     * Creates an CreateCommand to add the specified {@code Person}
     */
    public CreateCommand(Accounts account) {

        newAccount = account;

        if (!UserAccountStorage.checkDuplicateUser(account.getUsername())) {
            UserAccountStorage.addNewAccount(account.getUsername(), account.getPassword());
            createIsSuccessful = true;
        } else {
            createIsSuccessful = false;
        }
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
