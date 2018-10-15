package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTimetableCommand object
 */
public class DeleteTimetableCommandParser implements Parser<DeleteTimetableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTimetableCommand and
     * returns an DeleteTimetableCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTimetableCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
            return new DeleteTimetableCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimetableCommand.MESSAGE_USAGE),
                pe);
        }
    }
}
