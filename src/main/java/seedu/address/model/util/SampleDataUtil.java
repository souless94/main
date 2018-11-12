package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), null, null),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), null, null),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), null, null),
            new Person(new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), null, null),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), null, null),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), null, null),
            new Person(new Name("Kim Jong kook"), new Phone("92699917"),
                new Email("rKimJongkook@example.com"),
                new Address("Pyongyang Street 85, #11-31"),
                getTagSet("leader"), null, null),
            new Person(new Name("Damith"), new Phone("97474574"),
                new Email("Themyth@example.com"),
                new Address("Blk 74 Pasir ris 74, #74-74"),
                getTagSet("prof"), null, null),
            new Person(new Name("summer"), new Phone("92644919"),
                new Email("Thesun@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 65, #10-31"),
                getTagSet("friend"), null, null),
            new Person(new Name("zann"), new Phone("98674989"),
                new Email("Thezann@example.com"),
                new Address("Blk 77 Pasir ris  Street 85, #07-38"),
                getTagSet("friend"), null, null),
            new Person(new Name("crystal"), new Phone("92622319"),
                new Email("Thecrystal@example.com"),
                new Address("Blk 250 Ang Mo Kio Street 55, #15-31"),
                getTagSet("friend"), null, null),
            new Person(new Name("Ben"), new Phone("93637319"),
                new Email("TheBen@example.com"),
                new Address("Blk 52 Ang Mo Kio Street 57, #12-31"),
                getTagSet("friend"), null, null),
            new Person(new Name("Jonathan"), new Phone("93637319"),
                new Email("TheJonathan@example.com"),
                new Address("Blk 8 Pasir ris  Street 88, #12-34"),
                getTagSet("friend"), null, null),
            new Person(new Name("John Cena"), new Phone("93622319"),
                new Email("Wrestlingleague@example.com"),
                new Address("Blk 10 Tampines Street 52, #05-31"),
                getTagSet("friend"), null, null),
            new Person(new Name("Fann wong"), new Phone("93522449"),
                new Email("fannx0x0@example.com"),
                new Address("Blk 12 Pasir ris Street 72, #05-31"),
                getTagSet("friend"), null, null),
            new Person(new Name("Jesus"), new Phone("91682819"),
                new Email("thejesus@example.com"),
                new Address("Blk 20 Tampines Street 92, #09-19"),
                getTagSet("friend"), null, null),
            new Person(new Name("guan ying"), new Phone("91733817"),
                new Email("guanyinma@example.com"),
                new Address("Blk 18 Tampines Street 88, #08-17"),
                getTagSet("friend"), null, null),
            new Person(new Name("Han Fei"), new Phone("91656826"),
                new Email("Hanfeiahfei@example.com"),
                new Address("Blk 58 Hougang Street 28, #02-17"),
                getTagSet("friend"), null, null),
            new Person(new Name("Wang Fei"), new Phone("91853817"),
                new Email("wangfeiahfei@example.com"),
                new Address("Blk 98 Hougang Street 78, #02-17"),
                getTagSet("friend"), null, null),
            new Person(new Name("chen Fei"), new Phone("91726815"),
                new Email("wangchenahfei@example.com"),
                new Address("Blk 98 Paya Lebar Street 78, #03-37"),
                getTagSet("friend"), null, null)

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.add(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
