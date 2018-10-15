package seedu.address.logic.security;

//@@author aspiringdevslog

/**
 * Encrypts data before saving
 */
public class Encrypt {

    public static String encryptString(String plainString) {
        return plainString.concat("encrypt123456");
    }

    public static String decryptString(String encryptedString) {
        return encryptedString.replace("encrypt123456", "");
    }
}
