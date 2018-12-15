package seedu.address.model;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Guess extends Entity {

    public static String QUESTION_MARK = "?";
    public static String[] COMMAND_WORDS = {"help", "create", "login", "add", "delete", "edit", "find", "list",
            "add_timetable", "edit_timetable", "delete_timetable", "download_timetable",
            "add_group", "edit_group", "find_group", "delete_group", "view_group", "register", "delete_member",
            "view_slots_all", "view_slots_ranked"};
    private static String finalGuess = "sorry, we cannot guess the word";
    private final String guess;
    private HashMap<Character, Integer> guessHashMap;
    private HashMap<Character, Integer> commandHashMap;
    private TreeMap<Float, String> treeMap;
    private boolean canGuess;

    public Guess(String guess) {
        requireAllNonNull(guess);
        guessHashMap = new HashMap<>();
        commandHashMap = new HashMap<>();
        treeMap = new TreeMap<>();
        canGuess = true;
        this.guess = predictGuess(guess);

    }

    private void setCommandHashMap(String commandWord) {
        getOccurrences(commandWord, "command");
    }

    private String predictGuess(String guess) {
        getOccurrences(guess, "guess");

        Set<Character> keySet = guessHashMap.keySet();
        for (String command : COMMAND_WORDS) {
            setCommandHashMap(command);
            float countOfOccurrences = 0;
            if (guess.length() < command.length()) {
                countOfOccurrences = getCount(keySet) / command.length();
                treeMap.put(countOfOccurrences, command);
                commandHashMap.clear();
            }
        }
        if (!treeMap.isEmpty()) {
            return treeMap.lastEntry().getValue();
        } else {
            this.canGuess = false;
            return finalGuess;
        }
    }

    private float getCount(Set<Character> keySet) {
        float count = 0;
        for (char character : keySet) {
            if (commandHashMap.containsKey(character)) {
                count++;
            }
        }
        return count;
    }

    private void getOccurrences(String word, String mode) {
        char[] charArray = word.toCharArray();
        if ("command".equals(mode)) {
            for (char characters : charArray) {
                if (!commandHashMap.containsKey(characters)) {
                    commandHashMap.put(characters, 1);
                } else {
                    commandHashMap.put(characters, commandHashMap.get(characters) + 1);
                }
            }
        } else {
            for (char characters : charArray) {
                if (!guessHashMap.containsKey(characters)) {
                    guessHashMap.put(characters, 1);
                } else {
                    guessHashMap.put(characters, guessHashMap.get(characters) + 1);
                }
            }
        }
    }

    public String getGuess() {
        if (this.guess.equals(finalGuess)) {
            return this.guess;
        }
        return this.guess + QUESTION_MARK;
    }

    public boolean isCanGuess() {
        return this.canGuess;
    }

    @Override
    public boolean isSame(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Guess)) {
            return false;
        }
        Guess otherGuess = (Guess) other;
        return otherGuess.getGuess().equals(getGuess());
    }
}
