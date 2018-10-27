package seedu.address.logic.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


//@@author aspiringdevslog

/**
 * Encrypts data before saving
 */
public class Encrypt {

    public static String encryptString(String plainString) {
        
        return Base64.getEncoder().encodeToString(plainString.getBytes(StandardCharsets.UTF_8));
    }

    public static String decryptString(String encryptedString) {
        return encryptedString.replace("encrypt123456", "");
    }
}
