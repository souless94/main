package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DownloadTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DownloadTimetableCommand object
 */
public class DownloadTimetableCommandParser implements Parser<DownloadTimetableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DownloadTimetableCommand
     * and returns an DownloadTimetableCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DownloadTimetableCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
            return new DownloadTimetableCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadTimetableCommand.MESSAGE_USAGE),
                pe);
        }

    }
}
