package seedu.address.testutil;

import java.io.File;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_FORMAT = "horizontal";
    public static final String DEFAULT_STORED_LOCATION =
        new File("").getAbsolutePath().replace("\\", "/")
            + "/src/main/resources/timetable/stored";
    public static final String DEFAULT_DOWNLOAD_LOCATION =
        new File("").getAbsolutePath().replace("\\", "/")
            + "/src/main/resources/timetable/download";
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private String format;
    private String storedLocation;
    private String downloadLocation;
    private Timetable timetable;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        format = DEFAULT_FORMAT;
        storedLocation = DEFAULT_STORED_LOCATION;
        downloadLocation = DEFAULT_DOWNLOAD_LOCATION;
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        format = personToCopy.getFormat();
        storedLocation = personToCopy.getStoredLocation();
        downloadLocation = personToCopy.getDownloadLocation();
        tags = new HashSet<>(personToCopy.getTags());
        timetable = new Timetable(name.toString(), format, storedLocation, downloadLocation);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code format} of the {@code Person} that we are building.
     */
    public PersonBuilder withformat(String format) {
        this.format = format;
        return this;
    }

    /**
     * Sets the {@code storedLocation} of the {@code Person} that we are building.
     */
    public PersonBuilder withStoredLocation(String storedLocation) {
        this.storedLocation = storedLocation;
        return this;
    }

    /**
     * Sets the {@code downloadLocation} of the {@code Person} that we are building.
     */
    public PersonBuilder withDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are
     * building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     *
     * @return a person
     */
    public Person build() {
        return new Person(name, phone, email, address, tags, format, storedLocation,
            downloadLocation);
    }

}
