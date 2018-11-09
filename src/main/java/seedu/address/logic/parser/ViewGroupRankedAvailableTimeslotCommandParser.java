package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewGroupRankedAvailableTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new ViewGroupRankedAvailableTimeSlotCommand object
 */
public class ViewGroupRankedAvailableTimeslotCommandParser implements Parser<ViewGroupRankedAvailableTimeslotCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewGroupRankedAvailableTimeSlotCommand
     * and returns an ViewGroupRankedAvailableTimeSlotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewGroupRankedAvailableTimeslotCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewGroupRankedAvailableTimeslotCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        int numReq = ParserUtil.parseNumReq(argMultimap.getValue(PREFIX_NUMBER).get());

        return new ViewGroupRankedAvailableTimeslotCommand(name, numReq);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
