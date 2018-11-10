package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_USER_NOT_LOGGED_IN;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.AddTimetableCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CreateCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.commands.DeleteTimetableCommand;
import seedu.address.logic.commands.DownloadTimetableCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.commands.EditTimetableCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewGroupAllAvailableTimeslotCommand;
import seedu.address.logic.commands.ViewGroupCommand;
import seedu.address.logic.commands.ViewGroupRankedAvailableTimeslotCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static boolean userIsLoggedOn = false;


    public AddressBookParser(boolean setLoggedOn) { // for test
        this.userIsLoggedOn = setLoggedOn;
    }

    public AddressBookParser() { // default constructor
    }

    public static void updateLoggedOnStatus(boolean status) {
        userIsLoggedOn = status;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */

    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (userIsLoggedOn) {
            switch (commandWord) {
            case AddTimetableCommand.COMMAND_WORD:
                return new AddTimetableCommandParser().parse(arguments);

            case DeleteTimetableCommand.COMMAND_WORD:
                return new DeleteTimetableCommandParser().parse(arguments);

            case DownloadTimetableCommand.COMMAND_WORD:
                return new DownloadTimetableCommandParser().parse(arguments);

            case EditTimetableCommand.COMMAND_WORD:
                return new EditTimetableCommandParser().parse(arguments);

            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case AddGroupCommand.COMMAND_WORD:
                return new AddGroupCommandParser().parse(arguments);

            case DeleteMemberCommand.COMMAND_WORD:
                return new DeleteMemberCommandParser().parse(arguments);

            case DeleteGroupCommand.COMMAND_WORD:
                return new DeleteGroupCommandParser().parse(arguments);

            case EditGroupCommand.COMMAND_WORD:
                return new EditGroupCommandParser().parse(arguments);

            case FindGroupCommand.COMMAND_WORD:
                return new FindGroupCommandParser().parse(arguments);

            case ViewGroupCommand.COMMAND_WORD:
                return new ViewGroupCommandParser().parse(arguments);

            case RegisterCommand.COMMAND_WORD:
                return new RegisterCommandParser().parse(arguments);

            case ViewGroupRankedAvailableTimeslotCommand.COMMAND_WORD:
                return new ViewGroupRankedAvailableTimeslotCommandParser().parse(arguments);

            case ViewGroupAllAvailableTimeslotCommand.COMMAND_WORD:
                return new ViewGroupAllAvailableTimeslotCommandParser().parse(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case ListCommand.COMMAND_WORD:
                return new ListCommandParser().parse(arguments);

            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();

            case CreateCommand.COMMAND_WORD:
                return new CreateCommandParser().parse(arguments);

            case LoginCommand.COMMAND_WORD:
                return new LoginCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        } else {
            switch (commandWord) {
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();

            case LoginCommand.COMMAND_WORD:
                return new LoginCommandParser().parse(arguments);

            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser().parse(arguments);

            case FindGroupCommand.COMMAND_WORD:
                return new FindGroupCommandParser().parse(arguments);

            case ViewGroupCommand.COMMAND_WORD:
                return new ViewGroupCommandParser().parse(arguments);

            case ViewGroupRankedAvailableTimeslotCommand.COMMAND_WORD:
                return new ViewGroupRankedAvailableTimeslotCommandParser().parse(arguments);

            case ViewGroupAllAvailableTimeslotCommand.COMMAND_WORD:
                return new ViewGroupAllAvailableTimeslotCommandParser().parse(arguments);

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();

            case ListCommand.COMMAND_WORD:
                return new ListCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_USER_NOT_LOGGED_IN);
            }

        }

    }
}
