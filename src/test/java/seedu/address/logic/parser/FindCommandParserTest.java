package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    //@@author ZhiWei94
    @Test
    public void parseDoublePrefixThrowsParseException() {
        assertParseFailure(parser, " n/alex n/bernice t/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                prepareFindCommand(
                        "Alice", "", "", "", "owesMoney");
        assertParseSuccess(parser, " n/Alice t/owesMoney ", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t t/owesMoney  \t", expectedFindCommand);
    }

    /**
     * Parses {@code nameInput, phoneInput, addressInput, emailInput, tagInput}
     * into a {@code FindCommand}.
     */
    private FindCommand prepareFindCommand(String name, String phone,
                                           String address, String email, String tag) {

        String[] nameKeywords = getKeywords(name);
        String[] phoneKeywords = getKeywords(phone);
        String[] addressKeywords = getKeywords(address);
        String[] emailKeywords = getKeywords(email);
        String[] tagKeywords = getKeywords(tag);

        return new FindCommand(Arrays.asList(nameKeywords),
                Arrays.asList(phoneKeywords), Arrays.asList(addressKeywords),
                Arrays.asList(emailKeywords), Arrays.asList(tagKeywords));
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

}
