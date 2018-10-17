package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.Entity;
import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate<T extends Entity> implements Predicate<T> {
    private final List<String> keywords;
    private final String mode;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.mode = "name";
    }

    public NameContainsKeywordsPredicate(List<String> keywords, String mode) {
        this.keywords = keywords;
        this.mode = mode;
    }

    @Override
    public boolean test(T element) {
        if (element instanceof Person) {
            Person person = (Person) element;

            switch(mode) {
            case "name":
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
            case "address":
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
            case "phone":
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
            case "email":
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
            case "tag":
                return keywords.stream()
                        .anyMatch(keyword -> person.getTags().contains(new Tag(keyword)));
            case "all":
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)||
                                person.getTags().contains(new Tag(keyword))||
                                StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)||
                                StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                        );
            default:
                System.out.println("Invalid mode to 'NameContainsKeywordsPredicate'");
            }
        }

        if (element instanceof Group) {
            Group group = (Group) element;
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getName().fullName, keyword));
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
