package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    //@Test
    //public void parse_validArgs_returnsFindCommand() {
    //    // no leading and trailing whitespaces
    //    FindCommand expectedFindCommand =
    //            prepareFindCommand(
    //                    "Alice", "", "", "", "owesMoney");
    //    assertParseSuccess(parser, "n/Alice t/owesMoney", expectedFindCommand);

    //    // multiple whitespaces between keywords
    //    assertParseSuccess(parser, " \n n/Alice \n \t t/owesMoney  \t", expectedFindCommand);
    //}

    /**
     * Parses {@code nameInput, phoneInput, addressInput, emailInput, tagInput}
     * into a {@code FindCommand}.
     */
    //private FindCommand prepareFindCommand(String nameInput, String phoneInput,
    //                                       String addressInput, String emailInput, String tagInput) {
    //    return new FindCommand(Arrays.asList(nameInput.split("\\s+")),
    //            Arrays.asList(phoneInput.split("\\s+")), Arrays.asList(addressInput.split("\\s+")),
    //            Arrays.asList(emailInput.split("\\s+")), Arrays.asList(tagInput.split("\\s+")));
    //}

}
