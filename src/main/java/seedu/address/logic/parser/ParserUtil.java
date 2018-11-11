package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DETAILS_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DAY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DAY_AND_TIMING;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_EXTENSION;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIMING;
import static seedu.address.commons.core.Messages.MESSAGE_IS_FILE_DIRECTORY;
import static seedu.address.model.person.timetable.TimetableData.DAYS_IN_LOWER_CASE;
import static seedu.address.model.person.timetable.TimetableData.TIMINGS;

import java.io.File;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_NUMBER = "Number required is not an unsigned integer";

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
     * Parses {@code numReq} into an {@code int} and returns it. Leading and trailing whitespaces
     * will be trimmed. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified number is invalid (not unsigned integer).
     */
    public static Integer parseNumReq(String numReq) throws ParseException {
        requireNonNull(numReq);
        String trimmedNumReq = numReq.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedNumReq)) {
            throw new ParseException(MESSAGE_INVALID_NUMBER);
        }
        return Integer.parseInt(numReq);
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
        String trimmedLocation = location.trim();
        String fileExtension = FilenameUtils.getExtension(trimmedLocation);
        if ("csv".equals(fileExtension)) {
            File timetable = new File(trimmedLocation);
            if (timetable.isDirectory()) {
                throw new ParseException(MESSAGE_IS_FILE_DIRECTORY);
            }
        } else {
            throw new ParseException(MESSAGE_INVALID_FILE_EXTENSION);
        }
        File timetable = new File(trimmedLocation);
        return timetable.getAbsolutePath().replace("\\", "/");
    }

    /**
     * Parses a {@code String day} into an {@code String day}. Leading and trailing whitespaces will
     * be trimmed. checks if day is any of the days in a week.
     *
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static String parseDay(String day) throws ParseException {
        requireNonNull(day);
        String trimmedDay = day.trim();
        String[] validDays = DAYS_IN_LOWER_CASE;
        if (ArrayUtils.contains(validDays, trimmedDay.toLowerCase())) {
            return trimmedDay;
        } else {
            throw new ParseException(MESSAGE_INVALID_DAY);
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
        String trimmedTiming = timing.trim();
        String[] validTiming = TIMINGS;
        if (ArrayUtils.contains(validTiming, trimmedTiming)) {
            return trimmedTiming;
        } else {
            throw new ParseException(MESSAGE_INVALID_TIMING);
        }
    }

    /**
     * Parses a {@code String details} into an {@code String details}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code details} is invalid.
     */
    public static String parseDetails(String details) throws ParseException {
        requireNonNull(details);
        if (details.length() > 0) {
            return details;
        } else {
            throw new ParseException(MESSAGE_DETAILS_EMPTY);
        }
    }

    /**
     * Parses {@code String timing,String day} Leading and trailing whitespaces will be trimmed.
     * checks if timings are in 24h format and is from 0800 to 2300 and checks if day is any of the
     * days in a week.
     *
     * @throws ParseException if the given {@code timing} is invalid.
     */
    public static void checkBothDayAndTiming(String day, String timing) throws ParseException {

        String trimmedDay = day.trim();
        String[] validDays = DAYS_IN_LOWER_CASE;
        String trimmedTiming = timing.trim();
        String[] validTiming = TIMINGS;
        if (!ArrayUtils.contains(validDays, trimmedDay.toLowerCase())
            && !ArrayUtils.contains(validTiming, trimmedTiming)) {
            throw new ParseException(MESSAGE_INVALID_DAY_AND_TIMING);
        }
    }
}
