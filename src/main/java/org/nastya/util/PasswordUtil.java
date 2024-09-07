package org.nastya.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String encode(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean compare(String password_plaintext, String stored_hash){
        return BCrypt.checkpw(password_plaintext, stored_hash);
    }
}
