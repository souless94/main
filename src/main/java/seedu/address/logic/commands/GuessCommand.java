package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Guess;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class GuessCommand extends Command {

    public static final String MESSAGE_GUESS = "did you mean: %1$s";

    private final String guess;

    public GuessCommand(String guess) {
        requireNonNull(guess);
        this.guess = guess;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Guess aGuess = new Guess(guess);
        String finalGuess = aGuess.getGuess();
        if (aGuess.isCanGuess()) {
            return new CommandResult(
                    String.format(MESSAGE_GUESS, finalGuess));
        }
        return new CommandResult(finalGuess);
    }
}
