package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pw/");

    public static final Prefix PREFIX_FILE_LOCATION = new Prefix("fl/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");

    public static final Prefix PREFIX_DAY = new Prefix("day/");
    public static final Prefix PREFIX_TIMING = new Prefix("timing/");
    public static final Prefix PREFIX_DETAILS = new Prefix("m/");

}
