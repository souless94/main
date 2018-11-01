package seedu.address.logic.security;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;


//@@author aspiringdevslog

/**
 * Encrypts data before saving
 */
public class Encrypt {

    public static String encryptString(String plainString) {
        String encryptedString = Hashing.sha256()
            .hashString(plainString, StandardCharsets.UTF_8)
            .toString();

        return encryptedString;
    }

    public static String decryptString(String encryptedString) {
        return encryptedString.replace("encrypt123456", "");
    }
}
