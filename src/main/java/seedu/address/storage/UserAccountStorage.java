package seedu.address.storage;

import java.util.HashMap;

import seedu.address.logic.security.Encrypt;

/**
 * Storage of username and passwords
 */
public class UserAccountStorage {

    private static HashMap<String, String> userHashMap = new HashMap<>();

    public UserAccountStorage() {
    }

    /**
     * @param username
     * @param password
     */

    public static void addNewAccount(String username, String password) {
        String encryptedPassword = Encrypt.encryptString(password);
        userHashMap.put(username, encryptedPassword);
    }

    /**
     * @param username
     * @param password
     * @return
     */
    public static boolean checkPasswordMatch(String username, String password) {
        String encryptedPassword = Encrypt.encryptString(password);
        // TODO: remove this line, temporary to see output. System.out.print("Encrypted password: " + encryptedPassword + " User entered: " + password + " Stored in hashmap: " + userHashMap.get(username) + " Decrypted password: " + Encrypt.decryptString(userHashMap.get(username)));
        return userHashMap.get(username).equals(encryptedPassword);
    }

    public static boolean checkDuplicateUser(String username) {
        return userHashMap.containsKey(username);
    }

}

/**
 * Class for username and password
 */
class Account {
    private String username;
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
