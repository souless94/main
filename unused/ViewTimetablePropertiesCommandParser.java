package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewTimetablePropertiesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewTimetablePropertiesCommand object
 */
public class ViewTimetablePropertiesCommandParser implements
    Parser<ViewTimetablePropertiesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ViewTimetablePropertiesCommand and returns an ViewTimetablePropertiesCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTimetablePropertiesCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewTimetablePropertiesCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewTimetablePropertiesCommand.MESSAGE_USAGE), pe);
        }
    }
}
