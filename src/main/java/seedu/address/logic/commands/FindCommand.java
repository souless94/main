package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons with any parameters contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alex friends gardens";

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
