package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_DETAILS_EMPTY = "details to edit cannot be empty";
    public static final String MESSAGE_GROUPS_LISTED_OVERVIEW = "%1$d groups listed!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_DAY = "Days are in full name(case insensitive), from Monday to Sunday";
    public static final String MESSAGE_INVALID_TIMING = "timings are in 24h format and is from 0800 to 2300";
    public static final String MESSAGE_INVALID_DAY_AND_TIMING =
        MESSAGE_INVALID_DAY + "\n" + MESSAGE_INVALID_TIMING;
    public static final String MESSAGE_INVALID_FILE_EXTENSION = "timetable can only be in csv";
    public static final String MESSAGE_IS_FILE_DIRECTORY = "timetable cannot be a file directory(folder)";
    public static final String MESSAGE_NO_MATCH_TO_EXISTING_GROUP = "There is no match to an existing group.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_USER_NOT_LOGGED_IN = "Please login or create an account to continue.";
    public static final String MESSAGE_USER_ALREADY_LOGGED_IN = "User is already logged in.";
    public static final String MESSAGE_ADMIN_RIGHTS_REQUIRED_TO_CREATE_ACCOUNT =
        "Admin rights required to create an account. " +
            "Please login to admin account or contact an admin to continue. " +
            "Refer to User Guide section 5.1 for details";
    

}
