package seedu.address.model;


import org.apache.commons.text.similarity.LevenshteinDistance;

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
    private TreeMap<Integer, String> treeMap;

    public Guess(String guess) {
        requireAllNonNull(guess);
        treeMap = new TreeMap<>();
        this.guess = predictGuess(guess);

    }


    private String predictGuess(String guess) {
        int distance = 0;
        for (String command : COMMAND_WORDS) {
            distance = LevenshteinDistance.getDefaultInstance().apply(command, guess);
            treeMap.put(distance, command);
        }
        String finalGuess = treeMap.firstEntry().getValue();
        return finalGuess;
    }


    public String getGuess() {
        if (this.guess.equals(finalGuess)) {
            return this.guess;
        }
        return this.guess + QUESTION_MARK;
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
