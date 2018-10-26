package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.timetable.TimetableData;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed. Parses {@code oneBasedIndex} into an {@code Index} and returns
     * it. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    //TODO: need to implement exceptions
    public static String parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedName = username.trim();
        return username;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    //TODO: need to implement exceptions
    public static String parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedName = password.trim();
        return password;
    }

    /**
     * Parses a {@code String location} into an {@code String location}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static String parseLocation(String location) throws ParseException {
        requireNonNull(location);
        boolean doesFileExists = new File(location).exists();
        if (!doesFileExists) {
            throw new ParseException(Messages.MESSAGE_TIMETABLE_NOT_FOUND);
        }
        return location;
    }

    /**
     * Parses a {@code String fileName} into an {@code String fileName}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code fileName} is invalid.
     */
    public static String parseFilename(String fileName) throws ParseException {
        requireNonNull(fileName);
        return fileName;
    }

    /**
     * Parses a {@code String format} into an {@code String format}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code format} is invalid.
     */
    public static String parseFormat(String format) throws ParseException {
        requireNonNull(format);
        if ("horizontal".equals(format) || "vertical".equals(format)) {
            return format;
        } else {
            throw new ParseException(Messages.INVALID_TIMETABLE_FORMAT);
        }
    }

    /**
     * Parses a {@code String day} into an {@code String day}. Leading and trailing whitespaces will
     * be trimmed. checks if day is any of the days in a week.
     *
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static String parseDay(String day) throws ParseException {
        requireNonNull(day);
        String[] validDays = new TimetableData("horizontal", null, "default", 1, null, null, null)
            .getDaysInLowerCase();
        if (ArrayUtils.contains(validDays, day.toLowerCase())) {
            return day;
        } else {
            throw new ParseException(Messages.INVALID_DAY);
        }
    }

    /**
     * Parses a {@code String timing} into an {@code String timing}. Leading and trailing
     * whitespaces will be trimmed. checks if timings are in 24h format and is from 0800 to 2300.
     *
     * @throws ParseException if the given {@code timing} is invalid.
     */
    public static String parseTiming(String timing) throws ParseException {
        requireNonNull(timing);
        String[] validTiming = new TimetableData("horizontal", null, "default", 1, null, null, null)
            .getTimings();
        if (ArrayUtils.contains(validTiming, timing)) {
            return timing;
        } else {
            throw new ParseException(Messages.INVALID_TIMING);
        }
    }

    /**
     * Parses a {@code String details} into an {@code String details}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code details} is invalid.
     */
    public static String parseDetails(String details) {
        requireNonNull(details);
        return details;
    }

}
