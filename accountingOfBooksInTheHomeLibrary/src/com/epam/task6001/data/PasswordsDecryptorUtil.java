package com.epam.task6001.data;

public class PasswordsDecryptorUtil {
    private static final char[] symbols="abcdefghijklmnopqrstuvwxyzABCDEGGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    static String decryptPassword(String encryptedPassword) {
        char[] encryptedPasswordChars;
        char[] decryptedPasswordChars;
        String decryptedPassword;

        encryptedPasswordChars = encryptedPassword.toCharArray();
        decryptedPasswordChars = new char[encryptedPasswordChars.length];

        for (int i = 0; i < encryptedPassword.length(); i++) {
            for (int j = 0; j < 62; j++) {
                if (symbols[j] == encryptedPasswordChars[i]) {
                    decryptedPasswordChars[i] = symbols[(j + 7) % 62];
                }
            }
        }

        decryptedPassword = String.valueOf(decryptedPasswordChars);

        return decryptedPassword;
    }

    static String encryptPassword(String decryptedPassword) {
        char[] decryptedPasswordChars;
        char[] encryptedPasswordChars;
        String encryptedPassword;

        decryptedPasswordChars = decryptedPassword.toCharArray();
        encryptedPasswordChars = new char[decryptedPasswordChars.length];

        for (int i = 0; i < decryptedPassword.length(); i++) {
            for (int j = 0; j < 62; j++) {
                if (symbols[j] == decryptedPasswordChars[i]) {
                    encryptedPasswordChars[i] = ((j >= 7) ? symbols[j - 7] : symbols[62 + j - 7]);
                }
            }
        }

        encryptedPassword = String.valueOf(encryptedPasswordChars);

        return encryptedPassword;
    }
}
