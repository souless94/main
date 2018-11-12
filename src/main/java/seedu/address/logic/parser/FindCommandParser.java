package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer
                        .tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                                PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        checkPrefixes(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        String name = argMultimap.getValue(PREFIX_NAME).orElse("");
        String phone = argMultimap.getValue(PREFIX_PHONE).orElse("");
        String email = argMultimap.getValue(PREFIX_EMAIL).orElse("");
        String address = argMultimap.getValue(PREFIX_ADDRESS).orElse("");
        String tag = argMultimap.getValue(PREFIX_TAG).orElse("");

        String[] nameKeywords = getKeywords(name);
        String[] phoneKeywords = getKeywords(phone);
        String[] addressKeywords = getKeywords(address);
        String[] emailKeywords = getKeywords(email);
        String[] tagKeywords = getKeywords(tag);

        return new FindCommand(Arrays.asList(nameKeywords), Arrays.asList(phoneKeywords),
                Arrays.asList(addressKeywords), Arrays.asList(emailKeywords), Arrays.asList(tagKeywords));
    }

    /**
     * Parses the given {@code input} of arguments
     * to split into keywords of individual words.
     */
    private String[] getKeywords(String input) {
        String[] keywords;
        if (!"".equals(input)) {
            keywords = input.split("\\s+");
        } else {
            keywords = new String[0];
        }
        return keywords;
    }

    /**
     * Parses the given {@code Prefix, ArgumentMultimap} of arguments
     * to check no more than 1 prefix of the same type has been keyed.
     * @throws ParseException if the user input does not conform the expected format
     */
    private static void checkPrefixes(ArgumentMultimap argMultimap, Prefix... prefixes)throws ParseException {
        for (Prefix prefix: prefixes) {
            if ((argMultimap.getAllValues(prefix).size()) > 1) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
    }

}
