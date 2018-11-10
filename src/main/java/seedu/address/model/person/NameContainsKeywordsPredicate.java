package seedu.address.model.person;

import java.util.ArrayList;
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
    private final List<String> nameKeywords;
    private final List<String> addressKeywords;
    private final List<String> phoneKeywords;
    private final List<String> emailKeywords;
    private final List<String> tagKeywords;

    private final String mode;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.mode = "name";
        this.nameKeywords = new ArrayList<>();
        this.addressKeywords = new ArrayList<>();
        this.phoneKeywords = new ArrayList<>();
        this.emailKeywords = new ArrayList<>();
        this.tagKeywords = new ArrayList<>();
    }

    public NameContainsKeywordsPredicate(List<String> nameList, List<String> addressList, List<String> phoneList,
                                         List<String> emailList, List<String> tagList, String mode) {
        this.keywords = new ArrayList<>();
        this.nameKeywords = nameList;
        this.addressKeywords = addressList;
        this.phoneKeywords = phoneList;
        this.emailKeywords = emailList;
        this.tagKeywords = tagList;
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
                return nameKeywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                        || addressKeywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword))
                        || phoneKeywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword))
                        || emailKeywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword))
                        || tagKeywords.stream()
                        .anyMatch(keyword -> person.getTags().contains(new Tag(keyword)));
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
