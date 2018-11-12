package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

//@@author ZhiWei94
/**
 * Finds and lists all persons in NUS Hangs whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all person in NUS Hangs "
            + "(Prefix Search/ Case-Insensitive) and displays them as a list.\n"
            + "Parameters:\n"
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alex "
            + PREFIX_PHONE + "99272758 "
            + PREFIX_EMAIL + "charlotte@example.com "
            + PREFIX_ADDRESS + "Tampines "
            + PREFIX_TAG + "friends family\n"
            + "Example: " + COMMAND_WORD + " n/alex bernice e/lidavid@example.com";


    public final List<String> nameKeywords;
    public final List<String> phoneKeywords;
    public final List<String> addressKeywords;
    public final List<String> emailKeywords;
    public final List<String> tagKeywords;

    public FindCommand(List<String> nameKeywords, List<String> phoneKeywords, List<String> addressKeywords,
                       List<String> emailKeywords, List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.phoneKeywords = phoneKeywords;
        this.addressKeywords = addressKeywords;
        this.emailKeywords = emailKeywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                nameKeywords, addressKeywords, phoneKeywords, emailKeywords, tagKeywords, "all");
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && nameKeywords.equals(((FindCommand) other).nameKeywords) // state check
                && phoneKeywords.equals(((FindCommand) other).phoneKeywords)
                && addressKeywords.equals(((FindCommand) other).addressKeywords)
                && emailKeywords.equals(((FindCommand) other).emailKeywords)
                && tagKeywords.equals(((FindCommand) other).tagKeywords));
    }

}
