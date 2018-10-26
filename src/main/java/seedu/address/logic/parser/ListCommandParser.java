package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            try {
                Index index = ParserUtil.parseIndex(trimmedArgs);
                return new ListCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, pe));
            }
        }

        return new ListCommand();
    }
}

