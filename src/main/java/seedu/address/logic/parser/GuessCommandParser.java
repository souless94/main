package seedu.address.logic.parser;

import seedu.address.logic.commands.GuessCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class GuessCommandParser implements Parser<GuessCommand> {
    public GuessCommand parse(String args) throws ParseException {
        return new GuessCommand(args);
    }
}
