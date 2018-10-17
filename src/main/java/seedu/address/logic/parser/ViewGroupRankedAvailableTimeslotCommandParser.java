package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewGroupAvailableTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new ViewGroupAvailableTimeSlotCommand object
 */
public class ViewGroupRankedAvailableTimeslotCommandParser implements Parser<ViewGroupAvailableTimeslotCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewGroupAvailableTimeSlotCommand
     * and returns an ViewGroupAvailableTimeSlotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewGroupAvailableTimeslotCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewGroupAvailableTimeslotCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        return new ViewGroupAvailableTimeslotCommand(name);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
