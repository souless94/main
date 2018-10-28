package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_UNIQUE_PREFIX_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;

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
        ArgumentMultimap argMultimap =
            ArgumentTokenizer
                .tokenize(args, PREFIX_FILE_LOCATION);
        Index index;
        String newFilePath = "default";
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimetableCommand.MESSAGE_USAGE),
                pe);
        }

        if (argMultimap.getValue(PREFIX_FILE_LOCATION).isPresent()) {
            if (argMultimap.isOnlyOnePrefix(PREFIX_FILE_LOCATION)) {
                newFilePath = ParserUtil
                    .parseLocation(argMultimap.getValue(PREFIX_FILE_LOCATION).get());
            } else {
                throw new ParseException(MESSAGE_NOT_UNIQUE_PREFIX_INPUT);
            }
        }
        return new AddTimetableCommand(index, newFilePath);
    }
}
