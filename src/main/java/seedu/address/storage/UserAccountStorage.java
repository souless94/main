package seedu.address.storage;

import java.util.HashMap;

import seedu.address.logic.security.Encrypt;

//@@author aspiringdevslog

/**
 * Storage of username and passwords
 */
public class UserAccountStorage {

    private static HashMap<String, String> userHashMap = new HashMap<>();
    private static final String adminUsername = "admin";
    private static final String adminPassword = "adminPassword";

    public UserAccountStorage() {
        addAdminAccount();
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
        return userHashMap.get(username).equals(encryptedPassword);
    }

    public static boolean checkDuplicateUser(String username) {
        return userHashMap.containsKey(username);
    }

    /**
     * initialize hashmap with admin account
     */
    public static void addAdminAccount() {
        String encryptedAdminPassword = Encrypt.encryptString(adminPassword);
        userHashMap.put(adminUsername, encryptedAdminPassword);
    }
}
