package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddTimetableCommand object
 */
public class AddTimetableCommandParser implements Parser<AddTimetableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTimetableCommand and
     * returns an AddTimetableCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTimetableCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
            return new AddTimetableCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimetableCommand.MESSAGE_USAGE),
                pe);
        }
    }


}
