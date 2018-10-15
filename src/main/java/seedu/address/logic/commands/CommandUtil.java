package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains helper methods for commands.
 */
public class CommandUtil {

    /**
     * Retrieves the group with index in displayed Group list
     */
    public static Group retrieveGroupFromName(Model model, Name groupName) throws CommandException {
        // Make sure that group exists
        List<Group> lastShownList = model.getFilteredGroupList();
        Group groupToBeEdited = new Group(groupName, ""); //do not know description and groupMembers

        if (!lastShownList.contains(groupToBeEdited)) {
            throw new CommandException(Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
        }

        return lastShownList.get(lastShownList.indexOf(groupToBeEdited)); //retrieves original group
    }

    /**
     * Retrieves the person with index in displayed Group list
     */
    public static Person retrievePersonFromIndex (Model model, Index targetIndex) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }
}

