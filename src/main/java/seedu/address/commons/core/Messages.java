package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_DAY = "Days are in full name, there is no such day in a week";
    public static final String MESSAGE_INVALID_TIMING = "timings are in 24h format and is from 0800 to 2300";
    public static final String MESSAGE_INVALID_DAY_AND_TIMING =
        MESSAGE_INVALID_DAY + "\n" + MESSAGE_INVALID_TIMING;
    public static final String MESSAGE_INVALID_FILE_EXTENSION = "timetable can only be in csv";
    public static final String MESSAGE_INVALID_FILE_PATH = "That is not a valid file path";


    public static final String MESSAGE_NO_MATCH_TO_EXISTING_GROUP = "There is no match to an existing group.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_GROUPS_LISTED_OVERVIEW = "%1$d groups listed!";
    public static final String MESSAGE_TIMETABLE_NOT_FOUND = "timetable to be added is not found";
    public static final String MESSAGE_NOT_UNIQUE_PREFIX_INPUT = "can only enter 1 input per prefix";
    public static final String MESSAGE_IS_FILE_DIRECTORY = "timetable cannot be a file directory(folder)";



}
