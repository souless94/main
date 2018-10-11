package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OpenTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class OpenTimetableCommandParser implements Parser <OpenTimetableCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTimetableCommand and
     * returns an DeleteTimetableCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenTimetableCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
            return new OpenTimetableCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenTimetableCommand.MESSAGE_USAGE),
                pe);
        }
    }

}
